package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;

import com.progym.R;

@EActivity ( R.layout.activity_bmi_choose_activity ) public class ActivityChooseActivityBMI extends Activity {
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
     }
}
