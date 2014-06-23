package com.progym.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.progym.R;

@EActivity ( R.layout.activity_bmi_result ) public class ActivityBMIResult extends Activity {

     /**
      * 0 - INACTIVE
      * 1 - LOW
      * 2 - MODERATE
      */
     public static int                       USER_ACTIVITY_LEVEL = 0;
     public static double                    USER_BMI;
     public static double                    USER_GENDER;
     public static double                    USER_WEIGHT;
     public static double                    USER_HEIGHT;
     public static double                    USER_AGE;
     public static double                    USER_HEALTHY_WEIGHT_FROM;
     public static double                    USER_HEALTHY_WEIGHT_TO;

     @ViewById Button                        btnFinish;
     @ViewById Button                        btnAboutBmiIndex;

     @ViewById TextView                      twBmiValue;
     @ViewById TextView                      twBmiResultName;
     @ViewById TextView                      twHealthyWeightRange;
     @ViewById TextView                      twHealthyCaloriesRange;

     @AnimationRes ( R.anim.fade ) Animation fade;
     private int                             USER_HEALTHY_CALORIES;

     @AfterViews void afterViews() {
          twBmiValue.setText(getGenderNameByIndex(USER_GENDER) + " " + String.format("%.2f", USER_BMI));
          twBmiResultName.setText(getBMINameByIndex(USER_BMI));
          twHealthyWeightRange.setText(String.format("%.2f", USER_HEALTHY_WEIGHT_FROM) + " kg - " + String.format("%.2f", USER_HEALTHY_WEIGHT_TO) + " kg");
          calculateCalories();
     }

     private String getGenderNameByIndex(double gender) {
          return gender == 0 ? "MALE" : "FEMALE";
     }

     private CharSequence getBMINameByIndex(double bmi) {
          if ( bmi < 18.5 ) {
               twBmiResultName.setTextColor(Color.GREEN);
               return "Underweight";
          }

          if ( bmi >= 18.5 && bmi <= 24.99 ) {
               twBmiResultName.setTextColor(Color.GREEN);
               return "Normal";
          }

          if ( bmi >= 25 && bmi <= 29.99 ) {
               twBmiResultName.setTextColor(Color.YELLOW);
               return "Overweight";
          }

          if ( bmi >= 30 && bmi <= 35.99 ) {
               twBmiResultName.setTextColor(Color.RED);
               return "Obese";
          }

          if ( bmi >= 36 && bmi <= 39.99 ) {
               twBmiResultName.setTextColor(Color.RED);
               return "Severely Obese";
          }

          if ( bmi >= 40 ) {
               twBmiResultName.setTextColor(Color.RED);
               return "Morbidly Obese";
          }
          return "Unknown";
     }

     private void calculateCalories() {
          double caloriesHighestLevel = 0;
          // MALE
          if ( USER_GENDER == 0 ) {
               switch (USER_ACTIVITY_LEVEL) {
                    case 0:
                         caloriesHighestLevel = (660 + (13.7 * USER_WEIGHT) + (5 * USER_HEIGHT) - (6.8 * USER_AGE)) * 1.2;
                         break;

                    case 1:
                         caloriesHighestLevel = (660 + (13.7 * USER_WEIGHT) + (5 * USER_HEIGHT) - (6.8 * USER_AGE)) * 1.375;
                         break;

                    case 2:
                         caloriesHighestLevel = (660 + (13.7 * USER_WEIGHT) + (5 * USER_HEIGHT) - (6.8 * USER_AGE)) * 1.725;
                         break;
               }

          } else {
               // FEMALE (user_GENDER == 1)
               switch (USER_ACTIVITY_LEVEL) {
                    case 0:
                         caloriesHighestLevel = (665 + (9.6 * USER_WEIGHT) + (1.8 * USER_HEIGHT) - (4.7 * USER_AGE)) * 1.2;
                         break;

                    case 1:
                         caloriesHighestLevel = (665 + (9.6 * USER_WEIGHT) + (1.8 * USER_HEIGHT) - (4.7 * USER_AGE)) * 1.375;
                         break;

                    case 2:
                         caloriesHighestLevel = (665 + (9.6 * USER_WEIGHT) + (1.8 * USER_HEIGHT) - (4.7 * USER_AGE)) * 1.725;
                         break;
               }
          }

          twHealthyCaloriesRange.setText(String.format("Low barrier is %.2f , high barrier is %.2f", caloriesHighestLevel * 0.8, caloriesHighestLevel));

          /*
           * // MALE and INACTIVE user activity level
           * if ( USER_ACTIVITY_LEVEL == 0 ) {
           * if ( USER_GENDER == 0 ) {
           * if ( USER_AGE < 29 ) {
           * USER_HEALTHY_CALORIES = 2450;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 2300;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 2100;
           * }
           * } else {
           * // FEMALE
           * if ( USER_AGE < 29 ) {
           * USER_HEALTHY_CALORIES = 2000;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 1900;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 1800;
           * }
           * }
           * }
           * // MALE and LOW user activity level
           * if ( USER_ACTIVITY_LEVEL == 1 ) {
           * if ( USER_GENDER == 0 ) {
           * if ( USER_AGE < 29 ) {
           * USER_HEALTHY_CALORIES = 2800;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 2650;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 2500;
           * }
           * } else {
           * // FEMALE
           * if ( USER_AGE < 29 && USER_ACTIVITY_LEVEL == 1 ) {
           * USER_HEALTHY_CALORIES = 2200;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 2150;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 2100;
           * }
           * }
           * }
           * // MALE and MODERATE user activity level
           * if ( USER_ACTIVITY_LEVEL == 2 ) {
           * if ( USER_GENDER == 0 ) {
           * if ( USER_AGE < 29 ) {
           * USER_HEALTHY_CALORIES = 3350;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 3150;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 3000;
           * }
           * } else {
           * // FEMALE
           * if ( USER_AGE < 29 && USER_ACTIVITY_LEVEL == 2 ) {
           * USER_HEALTHY_CALORIES = 2600;
           * }
           * if ( USER_AGE >= 29 && USER_AGE <= 39 ) {
           * USER_HEALTHY_CALORIES = 2500;
           * }
           * if ( USER_AGE > 40 ) {
           * USER_HEALTHY_CALORIES = 2500;
           * }
           * }
           * }
           */

     }

     @Click void btnFinish() {
          btnFinish.startAnimation(fade);
          startActivity(new Intent(ActivityBMIResult.this, ActivityStart_.class));
     }

     @Click void btnAboutBmiIndex() {
          btnAboutBmiIndex.startAnimation(fade);
          Dialog d = new Dialog(ActivityBMIResult.this);
          d.setTitle("BMI index's explanation");
          d.setContentView(R.layout.activity_bmi_explanation);
          d.show();
          d.setCanceledOnTouchOutside(true);
          d.setCancelable(true);
     }
}
