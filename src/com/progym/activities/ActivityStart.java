package com.progym.activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.progym.R;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_start ) public class ActivityStart extends Activity {

     /**
      * LinearLayout section (start buttons)
      */
     @ViewById LinearLayout                          llNutrition;
     @ViewById LinearLayout                          llProgress;
     @ViewById LinearLayout                          llProfile;
     @ViewById LinearLayout                          llAdvice;
     @ViewById LinearLayout                          llWater;

     /**
      * Animation at the start screen
      */
     @AnimationRes ( R.anim.fadein ) Animation       fadeIn;
     @AnimationRes ( R.anim.shake ) Animation        shake;
     @AnimationRes ( R.anim.push_left_in ) Animation leftIn0;
     @AnimationRes ( R.anim.push_left_in ) Animation leftIn1;
     @AnimationRes ( R.anim.push_left_in ) Animation leftIn2;
     @AnimationRes ( R.anim.push_left_in ) Animation leftIn3;
     @AnimationRes ( R.anim.push_left_in ) Animation leftIn4;

     @AfterViews void afterViews() {
          // set animations playing one after other with delay 300ms
          leftIn0.setStartOffset(0);
          leftIn1.setStartOffset(300);
          leftIn2.setStartOffset(600);
          leftIn3.setStartOffset(800);
          leftIn4.setStartOffset(1200);

          llNutrition.startAnimation(leftIn0);
          llWater.startAnimation(leftIn1);
          llProgress.startAnimation(leftIn2);
          llProfile.startAnimation(leftIn3);
          llAdvice.startAnimation(leftIn4);

          if ( DataBaseUtils.getCurrentUser() == null ) {

               List <User> users = DataBaseUtils.getUsers();
               if ( users.isEmpty() ) {
                    DataBaseUtils.setCurrentUser(null);
               } else {
                    DataBaseUtils.setCurrentUser(users.get(0));
               }
          }

          // Set up ingridient database
          DataBaseUtils.setUpIngridientsDatabase(getApplicationContext());
     }

     /**
      * LinearLayout nutrition and water managment
      */
     @Click void llNutrition() {

          llNutrition.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               llProfile.startAnimation(shake);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityFoodManagment_.class));
     }

     /**
      * LinearLayout water module
      */
     @Click void llWater() {
          llNutrition.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               llProfile.startAnimation(shake);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityWaterManagement_.class));
     }

     /**
      * LinearLayout advice module
      */
     @Click void llAdvice() {
          llAdvice.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               llProfile.startAnimation(shake);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityChooseTypeOfAdvice_.class));
     }

     /**
      * LinearLayout your profile
      */
     @Click void llProfile() {
          llProfile.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityUserProfile_.class));
     }

     /**
      * LinearLayout progress
      */
     @Click void llProgress() {
          llProgress.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               llProfile.startAnimation(shake);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityChooseProgressType_.class));
     }

     @Override public void onBackPressed() {
          moveTaskToBack(true);
     }
}
