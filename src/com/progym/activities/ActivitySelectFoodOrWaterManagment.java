package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.animation.Animation;

import com.progym.R;
import com.progym.custom.roundimageview.RoundedImageView;

@EActivity ( R.layout.activity_select_food_or_water_managment ) public class ActivitySelectFoodOrWaterManagment extends Activity {

     @ViewById RoundedImageView              roundImageFoodManagment;
     @ViewById RoundedImageView              roundImageWaterManagment;

     @AnimationRes ( R.anim.fade ) Animation fade;
     private ProgressDialog                  pb;

     @Click void roundImageFoodManagment() {
          roundImageFoodManagment.startAnimation(fade);
          showProgressBar(ActivitySelectFoodOrWaterManagment.this);

          Thread t = new Thread(new Runnable() {

               @Override public void run() {
                    startActivity(new Intent(ActivitySelectFoodOrWaterManagment.this, ActivityFoodManagment_.class));
                    hideProgressBar(ActivitySelectFoodOrWaterManagment.this);
               }
          });
          t.start();

     }

     @Click void roundImageWaterManagment() {
          roundImageWaterManagment.startAnimation(fade);
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
          pb.setTitle("Please wait...");
          pb.setMessage("Populating data");
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
