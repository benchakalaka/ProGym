package com.progym;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.RelativeLayout;

@EActivity ( R.layout.choose_type_of_progress ) public class ChooseTepeOfProgressActivity extends ProgymSuperActivity {

     @ViewById RelativeLayout rlRoundImageWaterProgress;
     @ViewById RelativeLayout rlRoundImageFoodProgress;
     @ViewById RelativeLayout rlRoundImageTraingingProgress;

     @Override public void displaySelectedDate() {
          // not using
     }

     @Click void rlRoundImageWaterProgress() {
          rlRoundImageWaterProgress.startAnimation(rightOut);
          startActivity(new Intent(ChooseTepeOfProgressActivity.this, WaterProgressActivity_.class));
     }

     @Click void rlRoundImageFoodProgress() {
          rlRoundImageFoodProgress.startAnimation(rightOut);
     }

     @Click void rlRoundImageTraingingProgress() {
          rlRoundImageTraingingProgress.startAnimation(rightOut);
     }

}
