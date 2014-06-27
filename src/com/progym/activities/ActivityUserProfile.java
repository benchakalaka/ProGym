package com.progym.activities;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;
import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.progym.R;
import com.progym.custom.NDSpinner;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_user_profile ) public class ActivityUserProfile extends Activity {

     @ViewById TextView       twWeight;
     @ViewById TextView       twHeight;

     @ViewById EditText       etUserName;
     @ViewById EditText       etUserWeight;
     @ViewById EditText       etUserAge;
     @ViewById EditText       etUserHeight;
     // @ViewById NDSpinner spinnerBodyType;
     @ViewById NDSpinner      spinnerGender;

     @ViewById Button         btnSave;
     @ViewById Button         btnCancel;

     @StringRes String        height_cm;
     @StringRes String        weight_kg;

     // @StringArrayRes String[] bodyTypes;
     @StringArrayRes String[] genders;

     private User             userToSave;

     Dialog                   dialog;

     @AfterViews void afterViews() {
          dialog = new Dialog(ActivityUserProfile.this);
          // tbAlarm.setChecked(appPref.isAlarmSet().get());
          InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

          // BodyTypeAdapter bodyTypeAdapter = new BodyTypeAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, bodyTypes);
          GenderAdapter genderAdpater = new GenderAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, genders);
          spinnerGender.setAdapter(genderAdpater);
          // spinnerBodyType.setAdapter(bodyTypeAdapter);
          // dialog.setTitle(R.string.body_type_explanation);
          // final DialogBodyTypeExplanation view = DialogBodyTypeExplanation_.build(ActivityUserProfile.this);
          // dialog.setContentView(view);
          // dialog.setCanceledOnTouchOutside(true);
     }

     @AfterTextChange void etUserWeight(Editable text) {
          try {
               twWeight.setText(weight_kg + String.format("(%.2f) lbs", 2.2 * Double.valueOf(etUserWeight.getText().toString())));
          } catch (Exception ex) {
               twWeight.setText(weight_kg);
          }
     }

     @AfterTextChange void etUserHeight(Editable text) {
          try {
               twHeight.setText(height_cm + "," + String.format("(%.2f) inches", Double.valueOf(etUserHeight.getText().toString()) / 2.54));
          } catch (Exception ex) {
               twHeight.setText(height_cm);
          }
     }

     @Click void btnCancel() {
          onBackPressed();
     }

     @Override protected void onPause() {
          super.onPause();
          startActivity(new Intent(ActivityUserProfile.this, ActivityStart_.class));
     }

     private void setUpUser() {
          User u = DataBaseUtils.getCurrentUser();
          if ( null != u ) {
               etUserName.setText(u.name);
               etUserAge.setText(String.valueOf(u.age));
               // spinnerBodyType.setSelection(u.bodyType, false);
               spinnerGender.setSelection(u.gender);
               etUserWeight.setText(String.valueOf(u.weight));
               etUserHeight.setText(String.valueOf(u.height));
          } else {
               etUserWeight.setText("");
               etUserName.setText("");
               etUserAge.setText("");
               etUserHeight.setText("");
               // spinnerBodyType.setSelection(0, false);
               spinnerGender.setSelection(0);
          }
     }

     @Override protected void onResume() {
          super.onResume();
          setUpUser();
          if ( null != dialog ) {
               dialog.dismiss();
          }
     }

     @Click void btnSave() {
          userToSave = DataBaseUtils.getCurrentUser();
          if ( null == userToSave ) {
               userToSave = new User(getApplicationContext());
          }

          if ( !checkFields() ) {
               Utils.showCustomToast(ActivityUserProfile.this, R.string.name_age_weight_arent_filled_properly, R.drawable.settings_warning);
               return;
          }

          userToSave.name = etUserName.getText().toString();
          userToSave.age = Integer.valueOf(etUserAge.getText().toString());

          userToSave.gender = spinnerGender.getSelectedItemPosition();
          userToSave.height = Double.valueOf(etUserHeight.getText().toString());
          userToSave.weight = Double.valueOf(etUserWeight.getText().toString());
          // userToSave.bodyType = 0; spinnerBodyType.getSelectedItemPosition();
          userToSave.save();
          startActivity(new Intent(ActivityUserProfile.this, ActivityStart_.class));
     }

     private boolean checkFields() {
          boolean result = true;
          try {
               int age = Integer.valueOf(etUserAge.getText().toString());
               // Set limit of age
               if ( age == 0 || age > 100 ) {
                    result = false;
               } else {
                    userToSave.age = age;
               }
               if ( StringUtils.isBlank(etUserName.getText().toString()) ) {
                    result = false;
               }

               if ( StringUtils.isBlank(etUserWeight.getText().toString()) ) {
                    result = false;
               }

               if ( StringUtils.isBlank(etUserHeight.getText().toString()) ) {
                    result = false;
               }
          } catch (Exception ex) {
               ex.printStackTrace();
               result = false;
          }

          return result;
     }

     // =================================================================================
     // =================================================================================
     // ADAPTERS BodyTypeAdapter
     // =================================================================================
     /*
      * public class BodyTypeAdapter extends ArrayAdapter <String> {
      * private final Activity context;
      * String[] data = null;
      * public BodyTypeAdapter ( Activity context , int resource , String[] data ) {
      * super(context, resource, data);
      * this.context = context;
      * this.data = data;
      * }
      * @Override public View getView(int position, View convertView, ViewGroup parent) { // Ordinary view in Spinner, we use
      * // android.R.layout.simple_spinner_item
      * return super.getView(position, convertView, parent);
      * }
      * @Override public View getDropDownView(int position, View convertView, ViewGroup parent) { // This view starts when we click the spinner.
      * View row = convertView;
      * if ( row == null ) {
      * LayoutInflater inflater = context.getLayoutInflater();
      * row = inflater.inflate(R.layout.dropdown_bodytype_layout, parent, false);
      * }
      * ImageView bodyType = (ImageView) row.findViewById(R.id.imageIcon);
      * TextView bodyTypeDescription = (TextView) row.findViewById(R.id.countryName);
      * bodyTypeDescription.setText(data[position]);
      * if ( position == 0 ) {
      * bodyType.setBackgroundResource(R.drawable.ekto);
      * } else if ( position == 1 ) {
      * bodyType.setBackgroundResource(R.drawable.mezo);
      * } else if ( position == 2 ) {
      * bodyType.setBackgroundResource(R.drawable.endo);
      * }
      * return row;
      * }
      * }
      */
     // =================================================================================
     // =================================================================================
     // ADAPTERS GenderAdapter
     // =================================================================================
     public class GenderAdapter extends ArrayAdapter <String> {
          private final Activity context;
          String[]               data = null;

          public GenderAdapter ( Activity context , int resource , String[] data ) {
               super(context, resource, data);
               this.context = context;
               this.data = data;
          }

          @Override public View getView(int position, View convertView, ViewGroup parent) { // Ordinary view in Spinner, we use
                                                                                            // android.R.layout.simple_spinner_item
               return super.getView(position, convertView, parent);
          }

          @Override public View getDropDownView(int position, View convertView, ViewGroup parent) { // This view starts when we click the spinner.
               View row = convertView;
               if ( row == null ) {
                    LayoutInflater inflater = context.getLayoutInflater();
                    row = inflater.inflate(R.layout.dropdown_bodytype_layout, parent, false);
               }

               ImageView bodyType = (ImageView) row.findViewById(R.id.imageIcon);
               TextView bodyTypeDescription = (TextView) row.findViewById(R.id.countryName);
               bodyTypeDescription.setText(data[position]);

               if ( position == 0 ) {
                    bodyType.setBackgroundResource(R.drawable.male);
               } else if ( position == 1 ) {
                    bodyType.setBackgroundResource(R.drawable.female);
               }

               return row;
          }
     }

}
