package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.progym.R;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_calculate_bmi ) public class ActivityCalculateBMI extends Activity {
     private static int    USER_HEALTHY_CALORIES;
     @ViewById EditText    etBMIAge;
     @ViewById EditText    etBMIWeight;
     @ViewById EditText    etBMIHeight;

     @ViewById Button      btnCalculateMyBMI;
     @ViewById Button      btnCalculateBMI;

     @ViewById RadioButton rbMale;
     @ViewById RadioButton rbFemale;

     boolean checkFilds() {
          boolean retValue = true;
          try {

               if ( StringUtils.isBlank(etBMIAge.getText().toString()) || Double.valueOf(etBMIAge.getText().toString()) == 0 ) {
                    retValue = false;
               }

               if ( StringUtils.isBlank(etBMIWeight.getText().toString()) || Double.valueOf(etBMIWeight.getText().toString()) == 0 ) {
                    retValue = false;
               }

               if ( StringUtils.isBlank(etBMIHeight.getText().toString()) || Double.valueOf(etBMIHeight.getText().toString()) == 0 ) {
                    retValue = false;
               }

          } catch (Exception ex) {
               ex.printStackTrace();
               retValue = false;
          }
          return retValue;
     }

     @Click void btnCalculateMyBMI() {
      
     }

     @Click void btnCalculateBMI() {
          if ( !checkFilds() ) {
               Utils.showCustomToast(ActivityCalculateBMI.this, "Fields arent filled properly", R.drawable.warning);
          } else {
               double heightInSquare = Math.pow(Double.valueOf(etBMIHeight.getText().toString()) / 100, 2);
               double userBmi = Double.valueOf(etBMIWeight.getText().toString()) / heightInSquare;
               double userHealthyWeightFrom = 18.5 * heightInSquare;
               double userHealthyWeightTo = 24.99 * heightInSquare;

               ActivityBMIResult.USER_BMI = userBmi;
               ActivityBMIResult.USER_HEALTHY_WEIGHT_FROM = userHealthyWeightFrom;
               ActivityBMIResult.USER_HEALTHY_WEIGHT_TO = userHealthyWeightTo;
               ActivityBMIResult.USER_GENDER = getUserGender();

               startActivity(new Intent(ActivityCalculateBMI.this, ActivityChooseActivityBMI_.class));
          }
     }

     /**
      * 0 - MALE
      * 1 - FEMALE
      */
     private int getUserGender() {
          return rbMale.isChecked() ? 0 : 1;
     }
}
