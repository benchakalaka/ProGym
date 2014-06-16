package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.progym.R;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_calculate_bmi ) public class ActivityCalculateBMI extends Activity {
     @ViewById EditText etBMIAge;
     @ViewById EditText etBMIWeight;
     @ViewById EditText etBMIHeight;

     @ViewById Button   btnCalculateMyBMI;
     @ViewById Button   btnCalculateBMI;

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
          User user = DataBaseUtils.getCurrentUser();
          if ( null != user ) {
               double userBmi = user.weight / Math.pow(user.height / 100, 2);
               Utils.showCustomToast(ActivityCalculateBMI.this, "User BMI: " + userBmi, R.drawable.energy);
          }
     }

     @Click void btnCalculateBMI() {
          if ( !checkFilds() ) {
               Utils.showCustomToast(ActivityCalculateBMI.this, "Fields arent filled properly", R.drawable.warning);
          } else {
               double userBmi = Double.valueOf(etBMIWeight.getText().toString()) / Math.pow(Double.valueOf(etBMIHeight.getText().toString()) / 100, 2);
               startActivity(new Intent(ActivityCalculateBMI.this, ActivityChooseActivityBMI_.class));
          }
     }
}
