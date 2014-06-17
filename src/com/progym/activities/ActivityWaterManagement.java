package com.progym.activities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.InputType;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;
import com.progym.constants.GlobalConstants;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.ConsumedWaterItemView;
import com.progym.custom.ConsumedWaterItemView_;
import com.progym.custom.SingleConsumedVolumeTodayItemView;
import com.progym.custom.SingleConsumedVolumeTodayItemView_;
import com.progym.custom.WaterLevelBodyView;
import com.progym.model.CustomWaterVolume;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;
import com.todddavies.components.progressbar.ProgressWheel;

@EActivity ( R.layout.activity_water_management ) public class ActivityWaterManagement extends ProgymSuperActivity {

     @ViewById ImageView            ivGlass250ML;
     @ViewById ImageView            ivBottle500ML;
     @ViewById ImageView            ivBottle1L;
     @ViewById ImageView            ivBottle2L;
     @ViewById ImageView            ivCustomWaterVolume;
     @ViewById ImageView            ivListOfConsumedWater;

     @ViewById TextView             twPercentComplete;

     @ViewById LinearLayout         llAlreadyConsumedWaterList;
     @ViewById LinearLayout         llRightPanelBody;
     @ViewById LinearLayout         llEditCustomWater;
     LinearLayout                   llListOfWaterToday;

     @ViewById LinearLayout         ll250ml;
     @ViewById LinearLayout         ll500ml;
     @ViewById LinearLayout         ll1L;
     @ViewById LinearLayout         ll2L;
     @ViewById RelativeLayout       rlCustomMl;

     @ViewById HorizontalScrollView horizontalScrollView;

     @ViewById ProgressWheel        pwConsumedCircleProgress;
     @ViewById ProgressBar          pbConsumedLeft;
     @ViewById WaterLevelBodyView   ivBodyWaterLevel;

     private MediaPlayer            mediaPlayer;
     private final double           USER_SHOULD_CONSUME = DataBaseUtils.getWaterUserShouldConsumePerDay();
     private Dialog                 dialogListOfWaterConsumedToday;

     @Override @AfterViews void afterViews() {

          calendar = new CaldroidFragmentCustom();
          calendar.setCaldroidListener(onDateChangeListener);
          dialogListOfWaterConsumedToday = new Dialog(this);
          dialogListOfWaterConsumedToday.setContentView(R.layout.dialog_list_consumed_today);
          dialogListOfWaterConsumedToday.setCanceledOnTouchOutside(true);
          dialogListOfWaterConsumedToday.setCancelable(true);
          llListOfWaterToday = (LinearLayout) dialogListOfWaterConsumedToday.findViewById(R.id.llRootListOfWaterVolumes);

          displaySelectedDate();

          loadVolumesByDate(twCurrentDate.getText().toString());
          // init player
          mediaPlayer = MediaPlayer.create(ActivityWaterManagement.this, R.raw.pouring_liquid);

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

                              llAlreadyConsumedWaterList.addView(itemView);
                              horizontalScrollView.postDelayed(new Runnable() {
                                   @Override public void run() {
                                        horizontalScrollView.smoothScrollTo(llAlreadyConsumedWaterList.getRight(), llAlreadyConsumedWaterList.getTop());
                                   }
                              }, 100L);
                              itemView.startAnimation(leftIn);

                              User u = DataBaseUtils.getCurrentUser();
                              final WaterConsumed waterToLog = new WaterConsumed(getApplicationContext());
                              waterToLog.user = u;
                              waterToLog.volumeConsumed = Utils.getVolumeByTag(tag);
                              SELECTED_DATE.setHours(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                              SELECTED_DATE.setMinutes(Calendar.getInstance().get(Calendar.MINUTE));
                              SELECTED_DATE.setSeconds(Calendar.getInstance().get(Calendar.SECOND));
                              waterToLog.date = Utils.formatDate(SELECTED_DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
                              waterToLog.save();

                              itemView.setOnClickListener(new OnClickListener() {

                                   @Override public void onClick(View v) {
                                        Utils.showCustomToast(ActivityWaterManagement.this, waterToLog.date, Utils.getImageIdByTag(tag));
                                   }
                              });

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

     @Override protected void onPause() {
          super.onPause();
          startActivity(new Intent(ActivityWaterManagement.this, ActivityStart_.class));
     }

     @Click void ivListOfConsumedWater() {
          fade.setDuration(600);
          ivListOfConsumedWater.startAnimation(fade);

          // Display volumes consumed only in this day
          List <WaterConsumed> list = DataBaseUtils.getWaterConsumedByDate(twCurrentDate.getText().toString());
          llListOfWaterToday.removeAllViews();

          if ( list.isEmpty() ) {
               Utils.showCustomToast(ActivityWaterManagement.this, R.string.drink_something, R.drawable.unhappy);
               return;
          }

          for ( final WaterConsumed w : list ) {
               // Fill List of consumed today water volume
               SingleConsumedVolumeTodayItemView listItem = SingleConsumedVolumeTodayItemView_.build(getApplicationContext());
               listItem.ivVolumeImage.setBackgroundResource(Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)));
               listItem.twWaterVolume.setText(String.valueOf(w.volumeConsumed) + " ML");
               try {
                    listItem.twTime.setText(w.date.substring(w.date.indexOf(" ") + 1, w.date.length()));
               } catch (Exception ex) {
                    ex.printStackTrace();
                    listItem.twTime.setText(String.valueOf(w.date));
               }

               llListOfWaterToday.addView(listItem);
          }
          Utils.showCustomToast(ActivityWaterManagement.this, R.string.today_water_statistic, R.drawable.water_progress);
          dialogListOfWaterConsumedToday.show();
     }

     @Click void llEditCustomWater() {
          final Dialog editDialog = new Dialog(ActivityWaterManagement.this);
          editDialog.setContentView(R.layout.dialog_custom_value);
          editDialog.setTitle(R.string.input_volume_in_ml);
          Button cancel = (Button) editDialog.findViewById(R.id.btnCancel);
          Button edit = (Button) editDialog.findViewById(R.id.btnEdit);
          final EditText input = (EditText) editDialog.findViewById(R.id.etCustomValue);

          final CustomWaterVolume cwv;
          List <CustomWaterVolume> customVolumes = CustomWaterVolume.listAll(CustomWaterVolume.class);
          if ( customVolumes.isEmpty() ) {
               cwv = new CustomWaterVolume(getApplicationContext());
          } else {
               cwv = customVolumes.get(0);
               input.setText(String.valueOf(cwv.customVolume));
               input.setSelection(String.valueOf(cwv.customVolume).length());
          }
          input.setInputType(InputType.TYPE_CLASS_NUMBER);
          cancel.setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    editDialog.dismiss();
               }
          });

          edit.setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    int value = 0;
                    try {
                         value = Integer.valueOf(input.getText().toString());
                    } catch (Exception ex) {
                         ex.printStackTrace();
                         Utils.showCustomToast(ActivityWaterManagement.this, R.string.please_input_proper_value, R.drawable.unhappy);
                         return;
                    }
                    if ( value <= 0 ) {
                         Utils.showCustomToast(ActivityWaterManagement.this, R.string.should_be_greater_than_0, R.drawable.unhappy);
                         return;
                    }

                    cwv.customVolume = value;
                    cwv.user = DataBaseUtils.getCurrentUser();
                    cwv.save();
                    editDialog.dismiss();
               }
          });

          editDialog.show();
     }

     @Click void llLeftPanelDateWithCalendar() {
          llLeftPanelDateWithCalendar.startAnimation(fade);
          showProgressBar(ActivityWaterManagement.this);

          Thread t = new Thread(new Runnable() {

               @Override public void run() {
                    List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumed();
                    calendar = new CaldroidFragmentCustom();
                    HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
                    for ( WaterConsumed singleDate : list ) {
                         try {
                              datesAndColour.put(DateUtils.parseDate(singleDate.date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), R.color.caldroid_sky_blue);
                         } catch (ParseException e) {
                              e.printStackTrace();
                         }
                    }

                    try {
                         // highlight dates in calendar with blue color
                         calendar.setBackgroundResourceForDates(datesAndColour);
                         calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);
                    } catch (Exception ex) {
                         ex.printStackTrace();
                    }
                    hideProgressBar(ActivityWaterManagement.this);
               }
          });
          t.start();

     }

     @Override public void displaySelectedDate() {
          // Apply pattern for displaying into left panel without time
          twCurrentDate.setText(Utils.formatDate(SELECTED_DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD));
          dialogListOfWaterConsumedToday.setTitle(twCurrentDate.getText().toString());
          loadVolumesByDate(twCurrentDate.getText().toString());

          int alreadyDrinked = DataBaseUtils.getConsumedPerDay(twCurrentDate.getText().toString());

          // in ml
          double onePercent = USER_SHOULD_CONSUME / 100;
          displayStatistic(alreadyDrinked, USER_SHOULD_CONSUME - alreadyDrinked, alreadyDrinked / onePercent);

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

          if ( percent >= 100 ) {
               Utils.showCustomToast(ActivityWaterManagement.this, "Well done " + DataBaseUtils.getCurrentUser().name + ", keep it up!", R.drawable.happy);
          }

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
          llListOfWaterToday.removeAllViews();
          for ( final WaterConsumed w : list ) {
               ConsumedWaterItemView itemView = ConsumedWaterItemView_.build(getApplicationContext());
               itemView.ivVolumeImage.setBackgroundResource(Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)));
               llAlreadyConsumedWaterList.addView(itemView);
               itemView.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {
                         Utils.showCustomToast(ActivityWaterManagement.this, w.date, Utils.getImageIdByVolume(w.volumeConsumed));
                    }
               });

               // Fill List of consumed today water volume
               SingleConsumedVolumeTodayItemView listItem = SingleConsumedVolumeTodayItemView_.build(getApplicationContext());
               listItem.ivVolumeImage.setBackgroundResource(Utils.getImageIdByVolume(Integer.valueOf(w.volumeConsumed)));
               listItem.twWaterVolume.setText(String.valueOf(w.volumeConsumed) + " ML");
               try {
                    listItem.twTime.setText(w.date.substring(w.date.indexOf(" ") + 1, w.date.length()));
               } catch (Exception ex) {
                    ex.printStackTrace();
                    listItem.twTime.setText(String.valueOf(w.date));
               }

               llListOfWaterToday.addView(listItem);
          }
     }

     private void animateBody() {
          fade.setDuration(500);
          ivBodyWaterLevel.startAnimation(fade);
     }

     private void dragView(View v) {
          String tag = v.getTag().toString();
          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);
     }

     @Click void ll250ml() {
          animateBody();
          double onePercent = USER_SHOULD_CONSUME / 100;
          Utils.showCustomToast(ActivityWaterManagement.this, "250 ml is " + String.format("%.2f", 250 / onePercent) + "% of your body norma!", R.drawable.info);
     }

     @Click void ll500ml() {
          animateBody();
          double onePercent = USER_SHOULD_CONSUME / 100;
          Utils.showCustomToast(ActivityWaterManagement.this, "500 ml is " + String.format("%.2f", 500 / onePercent) + "% of your body norma!", R.drawable.info);
     }

     @Click void ll1L() {
          animateBody();
          double onePercent = USER_SHOULD_CONSUME / 100;
          Utils.showCustomToast(ActivityWaterManagement.this, "1L is " + String.format("%.2f", 1000 / onePercent) + "% of your body norma!", R.drawable.info);
     }

     @Click void ll2L() {
          animateBody();
          double onePercent = USER_SHOULD_CONSUME / 100;
          Utils.showCustomToast(ActivityWaterManagement.this, "2L is " + String.format("%.2f", 2000 / onePercent) + "% of your body norma!", R.drawable.info);
     }

     @Click void rlCustomMl() {
          animateBody();
          List <CustomWaterVolume> customVolumes = CustomWaterVolume.listAll(CustomWaterVolume.class);
          if ( !customVolumes.isEmpty() ) {
               double onePercent = USER_SHOULD_CONSUME / 100;
               Utils.showCustomToast(ActivityWaterManagement.this, customVolumes.get(0).customVolume + "ml is " + String.format("%.2f", customVolumes.get(0).customVolume / onePercent) + "% of your body norma!", R.drawable.info);
          } else {
               Utils.showCustomToast(ActivityWaterManagement.this, R.string.there_is_no_custom_value, R.drawable.unhappy);
               llEditCustomWater.startAnimation(fade);
          }
     }

     @Touch void ivGlass250ML(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateBody();
               dragView(v);
          }
     }

     @Touch void ivBottle500ML(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateBody();
               dragView(v);
          }
     }

     @Touch void ivBottle1L(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateBody();
               dragView(v);
          }
     }

     @Touch void ivBottle2L(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateBody();
               dragView(v);
          }
     }

     @Touch void ivCustomWaterVolume(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               List <CustomWaterVolume> customVolumes = CustomWaterVolume.listAll(CustomWaterVolume.class);
               if ( !customVolumes.isEmpty() ) {
                    ivCustomWaterVolume.setTag(String.valueOf(customVolumes.get(0).customVolume));
                    dragView(v);
                    animateBody();
               } else {
                    Utils.showCustomToast(ActivityWaterManagement.this, R.string.there_is_no_custom_value, R.drawable.warning);
                    llEditCustomWater.startAnimation(fade);
               }
          }
     }
}
