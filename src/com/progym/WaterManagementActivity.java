package com.progym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.progym.model.CustomWaterVolume;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.GlobalConstants;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.water_management_activity )
public class WaterManagementActivity extends FragmentActivity {

	private final static SimpleDateFormat				dateFormat			= new SimpleDateFormat();
	private static Date								SELECTED_DATE			= new Date();

	private CaldroidFragment							calendar;

	@ViewById ImageView								ivGlass250ML;
	@ViewById ImageView								ivBottle500ML;
	@ViewById ImageView								ivBottle1L;
	@ViewById ImageView								ivBottle2L;
	@ViewById ImageView								ivCustomWaterVolume;
	@ViewById LinearLayout							llAlreadyConsumedWaterList;
	@ViewById LinearLayout							llRightPanelBody;
	@ViewById TextView								twPercentWatterCompletted;
	@ViewById TextView								twCurrentDate;
	@ViewById ImageView								ivNextDay;
	@ViewById ImageView								ivPrevDay;

	private MediaPlayer								mediaPlayer;

	// Setup listener
	public final com.roomorama.caldroid.CaldroidListener	onDateChangeListener	= new com.roomorama.caldroid.CaldroidListener() {
																		@Override public void onSelectDate(final Date date, View view) {
																			SELECTED_DATE = date;
																			displaySelectedDate();
																			calendar.dismiss();
																		}

																		@Override public void onChangeMonth(int month, int year) {
																		}

																		@Override public void onLongClickDate(Date date, View view) {
																		}

																		@Override public void onCaldroidViewCreated() {
																		}
																	};

	@Click void twCurrentDate() {
		calendar = new CaldroidFragment();
		calendar.setCaldroidListener(onDateChangeListener);

		List <WaterConsumed> list = WaterConsumed.listAll(WaterConsumed.class);

		HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
		for ( WaterConsumed singleDate : list ) {
			try {
				datesAndColour.put(DateUtils.parseDate(singleDate.date, GlobalConstants.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), com.caldroid.R.color.caldroid_sky_blue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// highlight dates in calendar with blue color
		calendar.setBackgroundResourceForDates(datesAndColour);
		calendar.show(getSupportFragmentManager(), "TAG");
	}

	@Click void ivNextDay() {
		// Add one day (add one day to currrnet date)
		SELECTED_DATE = DateUtils.addDays(SELECTED_DATE, 1);
		displaySelectedDate();
	}

	@Click void ivPrevDay() {
		// Sub one day from selected date
		SELECTED_DATE = DateUtils.addDays(SELECTED_DATE, -1);
		displaySelectedDate();
		loadVolumesByDate(twCurrentDate.getText().toString());
	}

	private void displaySelectedDate() {
		// Apply pattern for displaying into left panel without time
		dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD);
		twCurrentDate.setText(dateFormat.format(SELECTED_DATE));
		dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
		loadVolumesByDate(twCurrentDate.getText().toString());
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		if ( mediaPlayer != null ) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	private void loadVolumesByDate(String date) {
		// Display volumes consumed only in this day
		List <WaterConsumed> list = Select.from(WaterConsumed.class).where(Condition.prop("date").like(date + "%")).list();
		llAlreadyConsumedWaterList.removeAllViews();
		for ( WaterConsumed w : list ) {
			LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
			ImageView iv = new ImageView(getApplicationContext());
			iv.setBackgroundResource(Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)));
			iv.setLayoutParams(params);

			llAlreadyConsumedWaterList.addView(iv);

			Log.d(GlobalConstants.TAG, w.user.name + " drink (" + w.volumeConsumed + " ml) "/* +w.date.toGMTString() */);
		}
	}

	@AfterViews void afterViews() {
		displaySelectedDate();
		loadVolumesByDate(twCurrentDate.getText().toString());
		// init player
		mediaPlayer = MediaPlayer.create(WaterManagementActivity.this, R.raw.pouring_liquid);

		// TODO: Set tag into XML files
		ivGlass250ML.setTag(GlobalConstants.WATER_VOLUMES.GLASS_250ML);
		ivBottle500ML.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML);
		ivBottle1L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_1L);
		ivBottle2L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_2L);
		// ivCustomWaterVolume.setTag(GlobalConstants.WATER_VOLUMES.);

		llRightPanelBody.setOnDragListener(new OnDragListener() {

			@Override public boolean onDrag(View v, final DragEvent event) {
				switch (event.getAction()) {
					case DragEvent.ACTION_DROP:
						if ( null != mediaPlayer ) {
							mediaPlayer.start();
						}
						final String tag = event.getClipData().getDescription().getLabel().toString();

						LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
						ImageView iv = new ImageView(getApplicationContext());
						iv.setBackgroundResource(Utils.getImageIdByTag(tag));
						iv.setLayoutParams(params);

						llAlreadyConsumedWaterList.addView(iv);

						User u = User.find(User.class, "name = ?", "Eleonora Kosheleva").get(0);
						WaterConsumed waterToLog = new WaterConsumed(getApplicationContext());
						waterToLog.user = u;
						waterToLog.volumeConsumed = Utils.getVolumeByTag(tag);
						waterToLog.date = dateFormat.format(SELECTED_DATE);
						waterToLog.save();

						List <WaterConsumed> list = WaterConsumed.listAll(WaterConsumed.class);

						double alreadyDrinked = 0;
						for ( WaterConsumed w : list ) {
							Log.d(GlobalConstants.TAG, w.user.name + " drink (" + w.volumeConsumed + " ml) at " + w.date);
							alreadyDrinked += w.volumeConsumed;
						}

						// in ml
						double shouldDring = (u.weight / 30) * 1000;
						twPercentWatterCompletted.setText(String.valueOf((shouldDring - alreadyDrinked < 0) ? "0 ml " : (int) (shouldDring - alreadyDrinked)) + "ml left");

						break;
				}
				return true;
			}
		});

	}

	private void dragView(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder(v);
		v.startDrag(dragData, shadow, null, 0);
	}

	/*@LongClick boolean ivGlass250ML(View v) {
		dragView(v);
		return true;
	}

	@LongClick boolean ivBottle500ML(View v) {
		dragView(v);
		return true;
	}

	@LongClick boolean ivBottle1L(View v) {
		dragView(v);
		return true;
	}

	@LongClick boolean ivBottle2L(View v) {
		dragView(v);
		return true;
	}*/

	@LongClick boolean ivCustomWaterVolume(View v) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Custom water volume");
		alert.setMessage("Volume in ML");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		final CustomWaterVolume cwv;
		List <CustomWaterVolume> customVolumes = CustomWaterVolume.listAll(CustomWaterVolume.class);
		if ( customVolumes.isEmpty() ) {
			cwv = new CustomWaterVolume(getApplicationContext());
		} else {
			cwv = customVolumes.get(0);
			input.setText(String.valueOf(cwv.customVolume));
		}

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				String value = input.getText().toString();
				cwv.customVolume = Integer.valueOf(value);
				cwv.user = User.find(User.class, "name = ?", "Eleonora Kosheleva").get(0);
				cwv.save();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});
		alert.show();
		return true;
	}

	@Touch void ivGlass250ML(MotionEvent event, View v) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			Toast.makeText(getApplicationContext(), "250 ml", Toast.LENGTH_SHORT).show();
			dragView(v);
		}
	}

	@Touch void ivBottle500ML(MotionEvent event, View v) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {

			dragView(v);
			Toast.makeText(getApplicationContext(), "500 ml", Toast.LENGTH_SHORT).show();
		}
	}

	@Touch void ivBottle1L(MotionEvent event, View v) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {

			dragView(v);
			Toast.makeText(getApplicationContext(), "1 L", Toast.LENGTH_SHORT).show();
		}
	}

	@Touch void ivBottle2L(MotionEvent event, View v) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {

			dragView(v);
			Toast.makeText(getApplicationContext(), "2 L", Toast.LENGTH_SHORT).show();
		}
	}

	@Touch void ivCustomWaterVolume(MotionEvent event, View v) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {

			List <CustomWaterVolume> customVolumes = CustomWaterVolume.listAll(CustomWaterVolume.class);
			if ( !customVolumes.isEmpty() ) {
				ivCustomWaterVolume.setTag(String.valueOf(customVolumes.get(0).customVolume));
				dragView(v);
			} else {
				Toast.makeText(getApplicationContext(), "There is no custom water value found", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
