package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.progym.R;

@EActivity ( R.layout.activity_choose_food_or_water_managment ) public class ActivitySelectFoodOrWaterManagment extends Activity {

     @ViewById LinearLayout                  llFoodManagment;
     @ViewById LinearLayout                  llWaterManagment;

     @AnimationRes ( R.anim.fade ) Animation fade;

     @Click void llFoodManagment() {
          llFoodManagment.startAnimation(fade);
          startActivity(new Intent(ActivitySelectFoodOrWaterManagment.this, ActivityFoodManagment_.class));
     }

     @Click void llWaterManagment() {
          llWaterManagment.startAnimation(fade);
          startActivity(new Intent(ActivitySelectFoodOrWaterManagment.this, ActivityWaterManagement_.class));
     }
}
