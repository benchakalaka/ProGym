package com.progym.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.progym.R;
import com.progym.custom.DialogBodyTypeExplanation;
import com.progym.custom.DialogBodyTypeExplanation_;
import com.progym.custom.NDSpinner;
import com.progym.model.User;
import com.progym.utils.AppSharedPreferences_;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_user_profile ) public class ActivityUserProfile extends Activity {

     @Pref AppSharedPreferences_ appPref;

     @ViewById EditText          etUserName;
     @ViewById EditText          etUserWeight;
     @ViewById EditText          etUserAge;
     @ViewById NDSpinner         spinnerBodyType;
     @ViewById NDSpinner         spinnerGender;

     @ViewById Button            btnSave;
     @ViewById Button            btnCancel;

     @StringArrayRes String[]    bodyTypes;
     @StringArrayRes String[]    genders;

     private User                userToSave;

     Dialog                      dialog;

     @AfterViews void afterViews() {
          dialog = new Dialog(ActivityUserProfile.this);
          // tbAlarm.setChecked(appPref.isAlarmSet().get());
          InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

          BodyTypeAdapter bodyTypeAdapter = new BodyTypeAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, new String[] { "Ektomorf", "Mezo", "Endo" });
          GenderAdapter genderAdpater = new GenderAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, new String[] { "Male", "Female" });
          spinnerGender.setAdapter(genderAdpater);
          spinnerBodyType.setAdapter(bodyTypeAdapter);
          dialog.setTitle("Body type explanation");
          final DialogBodyTypeExplanation view = DialogBodyTypeExplanation_.build(ActivityUserProfile.this);
          dialog.setContentView(view);
          dialog.setCanceledOnTouchOutside(true);
     }

     @Click void btnCancel() {
          onBackPressed();
     }

     private void setUpUser() {
          User u = DataBaseUtils.getCurrentUser();
          if ( null != u ) {
               etUserName.setText(u.name);
               etUserAge.setText(String.valueOf(u.age));
               spinnerBodyType.setSelection(u.bodyType, false);
               spinnerGender.setSelection(u.gender);
               etUserWeight.setText(String.valueOf(u.weight));
          } else {
               etUserName.setText("");
               etUserAge.setText("");
               spinnerBodyType.setSelection(0, false);
               spinnerGender.setSelection(0);
               etUserWeight.setText(String.valueOf(0));
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
               Utils.showCustomToast(ActivityUserProfile.this, "Name/Age/Weight aren't filled properly", R.drawable.settings_warning);
               return;
          }

          userToSave.name = etUserName.getText().toString();
          userToSave.age = Integer.valueOf(etUserAge.getText().toString());

          userToSave.gender = spinnerGender.getSelectedItemPosition();
          userToSave.height = 0; // Static for now :TODO
          userToSave.weight = Double.valueOf(etUserWeight.getText().toString());
          userToSave.bodyType = spinnerBodyType.getSelectedItemPosition();
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
          } catch (Exception ex) {
               ex.printStackTrace();
               result = false;
          }

          return result;
     }

     @Override public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          // commmited from home
          getMenuInflater().inflate(R.menu.start, menu);
          return true;
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the Home/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();
          if ( id == R.id.action_settings ) { return true; }
          return super.onOptionsItemSelected(item);
     }

     // =================================================================================
     // =================================================================================
     // ADAPTERS BodyTypeAdapter
     // =================================================================================

     public class BodyTypeAdapter extends ArrayAdapter <String> {
          private final Activity context;
          String[]               data = null;

          public BodyTypeAdapter ( Activity context , int resource , String[] data ) {
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
                    bodyType.setBackgroundResource(R.drawable.ectomorf);
               } else if ( position == 1 ) {
                    bodyType.setBackgroundResource(R.drawable.mezomorf);
               } else if ( position == 2 ) {
                    bodyType.setBackgroundResource(R.drawable.endomorf);
               }

               return row;
          }
     }

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
