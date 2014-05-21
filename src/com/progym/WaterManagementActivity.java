package com.progym;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.progym.constants.GlobalConstants;
import com.progym.custom.ConsumedWaterItemView;
import com.progym.custom.ConsumedWaterItemView_;
import com.progym.custom.WaterLevelBodyView;
import com.progym.model.CustomWaterVolume;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;
import com.todddavies.components.progressbar.ProgressWheel;

@EActivity ( R.layout.water_management_activity ) public class WaterManagementActivity extends ProgymSuperActivity {

     @ViewById ImageView            ivGlass250ML;
     @ViewById ImageView            ivBottle500ML;
     @ViewById ImageView            ivBottle1L;
     @ViewById ImageView            ivBottle2L;
     @ViewById ImageView            ivCustomWaterVolume;

     @ViewById TextView             twPercentComplete;

     @ViewById LinearLayout         llAlreadyConsumedWaterList;
     @ViewById LinearLayout         llRightPanelBody;
     @ViewById LinearLayout         llEditCustomWater;

     @ViewById HorizontalScrollView horizontalScrollView;

     @ViewById ProgressWheel        pwConsumedCircleProgress;
     @ViewById ProgressBar          pbConsumedLeft;
     @ViewById WaterLevelBodyView   ivBodyWaterLevel;

     private MediaPlayer            mediaPlayer;

     @Click void llEditCustomWater() {
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
               @Override public void onClick(DialogInterface dialog, int whichButton) {

                    String value = input.getText().toString();
                    cwv.customVolume = Integer.valueOf(value);
                    cwv.user = User.find(User.class, "name = ?", "Eleonora Kosheleva").get(0);
                    cwv.save();
               }
          });

          alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
               }
          });
          alert.show();
     }

     @Click void llLeftPanelDateWithCalendar() {
          calendar = new CaldroidFragment();
          calendar.setCaldroidListener(onDateChangeListener);

          List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumed();

          HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
          for ( WaterConsumed singleDate : list ) {
               try {
                    datesAndColour.put(DateUtils.parseDate(singleDate.date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), R.color.caldroid_sky_blue);
               } catch (ParseException e) {
                    e.printStackTrace();
               }
          }
          // highlight dates in calendar with blue color
          calendar.setBackgroundResourceForDates(datesAndColour);
          calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);
     }

     @Override public void displaySelectedDate() {
          // Apply pattern for displaying into left panel without time
          Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
          twCurrentDate.setText(Utils.dateFormat.format(SELECTED_DATE));
          Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
          loadVolumesByDate(twCurrentDate.getText().toString());

          int alreadyDrinked = DataBaseUtils.getConsumedPerDay(twCurrentDate.getText().toString());

          User u = DataBaseUtils.getCurrentUser();
          // in ml
          double shouldDring = (u.weight / 30) * 1000;
          double onePercent = shouldDring / 100;
          displayStatistic(alreadyDrinked, shouldDring - alreadyDrinked, alreadyDrinked / onePercent);

          ivBodyWaterLevel.PERCENT_COMPLETE = (int) (alreadyDrinked / onePercent);
          ivBodyWaterLevel.invalidate();
     }

     private void displayStatistic(int consumed, double leftToConsume, double percent) {
          int consumedIn_360_DegreeFormat = (int) (3.6 * percent);
          twPercentComplete.setText(String.valueOf(String.format("%.2f", percent)) + "% done");

          pwConsumedCircleProgress.setProgress(consumedIn_360_DegreeFormat > 360 ? 360 : consumedIn_360_DegreeFormat);
          // pwConsumedCircleProgress.setText();
          pwConsumedCircleProgress.setText(String.valueOf(String.format("%.2f L", consumed / 1000f)));

          pbConsumedLeft.setProgress((int) (percent));

          /*
           * int barColor = color.caldroid_darker_gray;
           * if ( percent > 50 && percent < 75 ) {
           * barColor = android.R.color.holo_green_light;
           * } else {
           * if ( percent >= 75 ) {
           * barColor = color.caldroid_holo_blue_dark;
           * }
           * }
           * pwConsumedCircleProgress.setRimColor(barColor);
           * pwConsumedCircleProgress.setBackgroundColor(barColor);
           * pwConsumedCircleProgress.setTextColor(barColor);
           */
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
          List <WaterConsumed> list = DataBaseUtils.getWaterConsumedByDate(date);
          llAlreadyConsumedWaterList.removeAllViews();
          for ( WaterConsumed w : list ) {
               ConsumedWaterItemView itemView = ConsumedWaterItemView_.build(getApplicationContext());
               itemView.ivVolumeImage.setBackgroundResource(Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)));
               // itemView.twWaterVolume.setText(String.valueOf(w.volumeConsumed));
               llAlreadyConsumedWaterList.addView(itemView);
          }
     }

     @Override @AfterViews void afterViews() {
          displaySelectedDate();
          loadVolumesByDate(twCurrentDate.getText().toString());
          // init player
          mediaPlayer = MediaPlayer.create(WaterManagementActivity.this, R.raw.pouring_liquid);

          llRightPanelBody.setOnDragListener(new OnDragListener() {

               @Override public boolean onDrag(View v, final DragEvent event) {
                    switch (event.getAction()) {
                         case DragEvent.ACTION_DROP:
                              if ( null != mediaPlayer ) {
                                   mediaPlayer.start();
                              }

                              final String tag = event.getClipData().getDescription().getLabel().toString();

                              ConsumedWaterItemView itemView = ConsumedWaterItemView_.build(getApplicationContext());
                              itemView.ivVolumeImage.setBackgroundResource(Utils.getImageIdByTag(tag));
                              // itemView.twWaterVolume.setText(String.valueOf(Utils.getVolumeByTag(tag)));
                              llAlreadyConsumedWaterList.addView(itemView);

                              horizontalScrollView.postDelayed(new Runnable() {
                                   @Override public void run() {
                                        horizontalScrollView.smoothScrollTo(llAlreadyConsumedWaterList.getRight(), llAlreadyConsumedWaterList.getTop());
                                   }
                              }, 100L);

                              itemView.startAnimation(leftIn);

                              User u = DataBaseUtils.getCurrentUser();
                              WaterConsumed waterToLog = new WaterConsumed(getApplicationContext());
                              waterToLog.user = u;
                              waterToLog.volumeConsumed = Utils.getVolumeByTag(tag);
                              waterToLog.date = Utils.dateFormat.format(SELECTED_DATE);
                              waterToLog.save();

                              int alreadyDrinked = DataBaseUtils.getConsumedPerDay(twCurrentDate.getText().toString());

                              // in ml
                              double shouldDring = (u.weight / 30) * 1000;
                              double onePercent = shouldDring / 100;

                              displayStatistic(alreadyDrinked, shouldDring - alreadyDrinked, alreadyDrinked / onePercent);
                              ivBodyWaterLevel.PERCENT_COMPLETE = (int) (alreadyDrinked / onePercent);
                              ivBodyWaterLevel.invalidate();
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
