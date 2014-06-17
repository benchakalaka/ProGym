package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;

import com.progym.R;

@EActivity ( R.layout.activity_bmi_choose_activity ) public class ActivityChooseActivityBMI extends Activity {
     /**
      * 0 - INACTIVE
      * 1 - LOW
      * 2 - MODERATE
      */
     @ViewById RadioButton                   rbLow;
     @ViewById RadioButton                   rbInactive;
     @ViewById RadioButton                   rbModerate;

     @ViewById Button                        btnNext;
     @ViewById Button                        btnBack;

     @AnimationRes ( R.anim.fade ) Animation fade;

     @Click void btnBack() {
          onBackPressed();
     }

     @Click void btnNext() {
          btnNext.startAnimation(fade);
          int userActivitry = 0;
          if ( rbInactive.isChecked() ) {
               userActivitry = 0;
          }

          if ( rbLow.isChecked() ) {
               userActivitry = 1;
          }

          if ( rbModerate.isChecked() ) {
               userActivitry = 2;
          }

          ActivityBMIResult.USER_ACTIVITY_LEVEL = userActivitry;

          startActivity(new Intent(ActivityChooseActivityBMI.this, ActivityBMIResult_.class));

     }
}
