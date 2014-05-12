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
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
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

import com.orm.query.Condition;
import com.orm.query.Select;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.GlobalConstants;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.water_management_activity ) public class WaterManagementActivity extends FragmentActivity {

     private final static SimpleDateFormat                dateFormat           = new SimpleDateFormat();
     private static Date                                  SELECTED_DATE        = new Date();

     private CaldroidFragment                             calendar;

     @ViewById ImageView                                  ivGlass250ML;
     @ViewById ImageView                                  ivBottle500ML;
     @ViewById ImageView                                  ivBottle1L;
     @ViewById ImageView                                  ivBottle2L;
     @ViewById ImageView                                  ivCustomWaterVolume;
     @ViewById LinearLayout                               llAlreadyConsumedWaterList;
     @ViewById LinearLayout                               llRightPanelBody;
     @ViewById TextView                                   twPercentWatterCompletted;
     @ViewById TextView                                   twCurrentDate;
     @ViewById ImageView                                  ivNextDay;
     @ViewById ImageView                                  ivPrevDay;
     @ViewById ImageView                                  ivCalendar;

     private MediaPlayer                                  mediaPlayer;

     // Setup listener
     public final com.roomorama.caldroid.CaldroidListener onDateChangeListener = new com.roomorama.caldroid.CaldroidListener() {
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

     @Click void ivCalendar() {
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
          // dragView(v);
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
