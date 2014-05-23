package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.progym.R;

@EActivity ( R.layout.choose_type_of_progress ) public class ActivityChooseProgressType extends ProgymSuperActivity {

     @ViewById RelativeLayout rlRoundImageWaterProgress;
     // @ViewById RelativeLayout rlRoundImageFoodProgress;
     @ViewById RelativeLayout rlRoundImageTraingingProgress;

     @Override public void displaySelectedDate() {
          // not using
     }

     @Click void rlRoundImageWaterProgress() {
          rlRoundImageWaterProgress.startAnimation(rightOut);
          startActivity(new Intent(ActivityChooseProgressType.this, ActivityWaterProgress_.class));
     }

     /*
      * @Click void rlRoundImageFoodProgress() {
      * rlRoundImageFoodProgress.startAnimation(rightOut);
      * }
      */

     @Click void rlRoundImageTraingingProgress() {
          rlRoundImageTraingingProgress.startAnimation(rightOut);
     }

}
