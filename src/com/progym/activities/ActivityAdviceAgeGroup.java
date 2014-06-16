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

@EActivity ( R.layout.activity_advice_age_group ) public class ActivityAdviceAgeGroup extends Activity {
     @ViewById LinearLayout                  llPAC;

     @AnimationRes ( R.anim.fade ) Animation fade;

     @Click void llPAC() {
          llPAC.startAnimation(fade);
          startActivity(new Intent(ActivityAdviceAgeGroup.this, ActivityAdvicePacExplanation_.class));
     }

}
