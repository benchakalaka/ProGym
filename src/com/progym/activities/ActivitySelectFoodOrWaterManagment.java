package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.progym.R;

@EActivity ( R.layout.activity_choose_food_or_water_managment ) public class ActivitySelectFoodOrWaterManagment extends Activity {

     @ViewById LinearLayout                  llFoodManagment;
     @ViewById LinearLayout                  llWaterManagment;

     @AnimationRes ( R.anim.fade ) Animation fade;
     private ProgressDialog                  pb;

     @Click void llFoodManagment() {
          llFoodManagment.startAnimation(fade);
          showProgressBar(ActivitySelectFoodOrWaterManagment.this);

          Thread t = new Thread(new Runnable() {

               @Override public void run() {
                    startActivity(new Intent(ActivitySelectFoodOrWaterManagment.this, ActivityFoodManagment_.class));
                    hideProgressBar(ActivitySelectFoodOrWaterManagment.this);
               }
          });
          t.start();

     }

     @Click void llWaterManagment() {
          llWaterManagment.startAnimation(fade);
          showProgressBar(ActivitySelectFoodOrWaterManagment.this);

          Thread t = new Thread(new Runnable() {

               @Override public void run() {

                    ActivitySelectFoodOrWaterManagment.this.startActivity(new Intent(ActivitySelectFoodOrWaterManagment.this, ActivityWaterManagement_.class));
                    hideProgressBar(ActivitySelectFoodOrWaterManagment.this);
               }
          });
          t.start();

     }

     protected void showProgressBar(Activity activity) {
          if ( null != pb ) {
               pb.show();
          } else {
               initProgressBar(activity);
          }
     }

     private void initProgressBar(Activity activity) {
          pb = new ProgressDialog(activity);
          pb.setIndeterminate(true);
          pb.setTitle(R.string.please_wait);
          pb.setMessage(getResources().getString(R.string.populating_data));
          pb.show();
     }

     protected void hideProgressBar(Activity activity) {
          if ( null != pb ) {
               pb.dismiss();
          } else {
               initProgressBar(activity);
          }
     }
}
