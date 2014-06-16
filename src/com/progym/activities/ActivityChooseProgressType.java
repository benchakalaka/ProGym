package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.LinearLayout;

import com.progym.R;

@EActivity ( R.layout.activity_choose_type_of_progress ) public class ActivityChooseProgressType extends ProgymSuperActivity {

     @ViewById LinearLayout llWaterProgress;
     @ViewById LinearLayout llFoodProgress;
     @ViewById LinearLayout llFoodCalloriesProgress;

     @Override public void displaySelectedDate() {
          // not using
     }

     @Override void afterViews() {
          fade.setDuration(300);
     }

     /**
      * Show water progress activity
      */
     @Click void llWaterProgress() {
          llWaterProgress.startAnimation(fade);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityWaterProgress_.class));
     }

     /**
      * Show food progress activity
      */
     @Click void llFoodProgress() {
          llFoodProgress.startAnimation(fade);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityFoodProgress_.class));
     }

     /**
      * Show food progress callories activity
      */
     @Click void llFoodCalloriesProgress() {
          llFoodCalloriesProgress.startAnimation(fade);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityCalloriesProgress_.class));
     }

}
