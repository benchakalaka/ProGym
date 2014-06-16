package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.LinearLayout;

import com.progym.R;

@EActivity ( R.layout.activity_choose_type_of_advice ) public class ActivityChooseTypeOfAdvice extends ProgymSuperActivity {

     @ViewById LinearLayout llWhatDoYouNeed;
     @ViewById LinearLayout llUsefulTable;

     @ViewById LinearLayout llCalculateBMI;

     @Override public void displaySelectedDate() {
          // not using
     }

     @Override void afterViews() {
          fade.setDuration(300);
     }

     /**
      * Show water progress activity
      */
     @Click void llWhatDoYouNeed() {
          llWhatDoYouNeed.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceLevel_1_.class));
     }

     /**
      * Show food progress activity
      */
     @Click void llUsefulTable() {
          llUsefulTable.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceAgeGroup_.class));
     }

     /**
      * Show calculate BMI
      */

     @Click void llCalculateBMI() {
          llCalculateBMI.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityCalculateBMI_.class));
     }

}
