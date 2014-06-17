package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.LinearLayout;

import com.progym.R;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_choose_type_of_advice ) public class ActivityChooseTypeOfAdvice extends ProgymSuperActivity {

     @ViewById LinearLayout llWhatDoYouNeed;
     @ViewById LinearLayout llUsefulTable;

     @ViewById LinearLayout llCalculateBMI;
     @ViewById LinearLayout llCalculateMyBMI;

     @Override public void displaySelectedDate() {
          // not using
     }

     @Override void afterViews() {
          fade.setDuration(300);
     }

     /**
      * Show water progress activity
      */
     @Click void llWhatDoYouNeed() {
          llWhatDoYouNeed.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceLevel_1_.class));
     }

     /**
      * Show food progress activity
      */
     @Click void llUsefulTable() {
          llUsefulTable.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceAgeGroup_.class));
     }

     /**
      * Show calculate BMI
      */

     @Click void llCalculateBMI() {
          llCalculateBMI.startAnimation(fade);
          startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityCalculateBMI_.class));
     }
     
     /**
      * Show calculate BMI
      */

     @Click void llCalculateMyBMI() {
     	llCalculateMyBMI.startAnimation(fade);
     	    User user = DataBaseUtils.getCurrentUser();
              if ( null != user ) {
                   double heightInSquare = Math.pow(user.height / 100, 2);
                   double userBmi = user.weight / Math.pow(user.height / 100, 2);
                   double userHealthyWeightFrom = 18.5 * heightInSquare;
                   double userHealthyWeightTo = 24.99 * heightInSquare;

                   ActivityBMIResult.USER_BMI = userBmi;
                   ActivityBMIResult.USER_HEALTHY_WEIGHT_FROM = userHealthyWeightFrom;
                   ActivityBMIResult.USER_HEALTHY_WEIGHT_TO = userHealthyWeightTo;
                   ActivityBMIResult.USER_GENDER = user.gender;
                   ActivityBMIResult.USER_ACTIVITY_LEVEL = 1;
                   startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityChooseActivityBMI_.class));
              } else {
          	    Utils.showCustomToast(ActivityChooseTypeOfAdvice.this, R.string.create_profile_please, R.drawable.unhappy);
              }
     }

}
