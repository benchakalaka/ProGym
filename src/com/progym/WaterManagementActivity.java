package com.progym;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.GlobalConstants;
import com.progym.utils.Utils;

@EActivity ( R.layout.water_management_activity )
public class WaterManagementActivity extends Activity {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@ViewById ImageView		ivGlass250ML;
	@ViewById ImageView		ivBottle500ML;
	@ViewById ImageView		ivBottle1L;
	@ViewById ImageView		ivBottle2L;
	@ViewById ImageView		ivCustomWaterVolume;
	@ViewById LinearLayout	llAlreadyConsumedWaterList;
	@ViewById LinearLayout	llRightPanelBody;
	@ViewById TextView		twPercentWatterCompletted;

	private MediaPlayer		mediaPlayer;

	@Override protected void onDestroy() {
		super.onDestroy();
		if ( mediaPlayer != null ) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@AfterViews void afterViews() {
		// init player
		mediaPlayer = MediaPlayer.create(WaterManagementActivity.this, R.raw.pouring_liquid);

		ivGlass250ML.setTag(GlobalConstants.WATER_VOLUMES.GLASS_250ML);
		ivBottle500ML.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML);
		ivBottle1L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_1L);
		ivBottle2L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_2L);
		// ivCustomWaterVolume.setTag(GlobalConstants.WATER_VOLUMES.);
		
		List <WaterConsumed> list = WaterConsumed.listAll(WaterConsumed.class);
		
		for (WaterConsumed w : list){
			LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
			ImageView iv = new ImageView(getApplicationContext());
			iv.setBackgroundResource( Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)) );
			iv.setLayoutParams(params);
			
			llAlreadyConsumedWaterList.addView(iv);
			
			Log.d(GlobalConstants.TAG,w.user.name + " drink ("+w.volumeConsumed+" ml) "/*+w.date.toGMTString()*/);
		}
		

		llRightPanelBody.setOnDragListener(new OnDragListener() {

			@Override public boolean onDrag(View v, final DragEvent event) {
				switch (event.getAction()) {
					case DragEvent.ACTION_DROP:
						if ( null != mediaPlayer ){
							mediaPlayer.start();
						}
						final String tag = event.getClipData().getDescription().getLabel().toString();

						LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
						ImageView iv = new ImageView(getApplicationContext());
						iv.setBackgroundResource( Utils.getImageIdByTag(tag) );
						iv.setLayoutParams(params);
						
						llAlreadyConsumedWaterList.addView(iv);
						
						User u = User.find(User.class, "name = ?", "Eleonora Kosheleva").get(0);
						WaterConsumed waterToLog = new WaterConsumed(getApplicationContext());
						waterToLog.user = u;
						waterToLog.volumeConsumed = Utils.getVolumeByTag(tag);
						waterToLog.date = dateFormat.format(new Date());
						waterToLog.save();
						
						List <WaterConsumed> list = WaterConsumed.listAll(WaterConsumed.class);
						
						double alreadyDrinked = 0;
						
						for (WaterConsumed w : list){
							Log.d(GlobalConstants.TAG,w.user.name + " drink ("+w.volumeConsumed+" ml) at "+w.date);
							alreadyDrinked +=w.volumeConsumed;
						}
						
						// in ml
						double shouldDring = (u.weight / 30)*1000;
						twPercentWatterCompletted.setText(String.valueOf((shouldDring - alreadyDrinked<0) ? "0 ml " : (int)(shouldDring - alreadyDrinked)) + "ml left");
						
						break;
				}
				return true;
			}
		});

	}
	
	private void dragView (View v){
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
	}

	@LongClick boolean ivGlass250ML(View v) {
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
	}

	@LongClick boolean ivCustomWaterVolume(View v) {
		//dragView(v);
		return true;
	}

	@Click void ivGlass250ML() {
		Toast.makeText(getApplicationContext(), "250 ml", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle500ML() {
		Toast.makeText(getApplicationContext(), "500 ml", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle1L() {
		Toast.makeText(getApplicationContext(), "1 L", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle2L() {
		Toast.makeText(getApplicationContext(), "2 L", Toast.LENGTH_SHORT).show();
	}

	@Click void ivCustomWaterVolume() {
		Toast.makeText(getApplicationContext(), "10000 L", Toast.LENGTH_SHORT).show();
	}

}
