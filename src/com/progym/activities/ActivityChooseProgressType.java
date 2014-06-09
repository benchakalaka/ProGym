package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;

import com.progym.R;
import com.progym.custom.roundimageview.RoundedImageView;

@EActivity ( R.layout.choose_type_of_progress ) public class ActivityChooseProgressType extends ProgymSuperActivity {

     @ViewById RoundedImageView roundImageWaterProgress; 
     @ViewById RoundedImageView roundImageFoodProgress;
     @ViewById RoundedImageView roundImageFoodCalloriesProgress;

     @Override public void displaySelectedDate() {
          // not using
     }

     /**
      * Show water progress activity
      */
     @Click void roundImageWaterProgress() {
          roundImageWaterProgress.startAnimation(rightOut);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityWaterProgress_.class));
     }

     /**
      * Show food progress activity
      */
     @Click void roundImageFoodProgress() {
          roundImageFoodProgress.startAnimation(rightOut);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityFoodProgress_.class));
     }
     
     /**
      * Show food progress callories activity
      */
     @Click void roundImageFoodCalloriesProgress() {
     	roundImageFoodCalloriesProgress.startAnimation(rightOut);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityCalloriesProgress_.class));
     }
     
     
     
}
