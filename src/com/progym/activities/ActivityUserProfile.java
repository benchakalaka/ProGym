package com.progym.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import android.app.Activity; 
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
import android.widget.Spinner;
import android.widget.TextView;

import com.progym.R;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_user_profile ) public class ActivityUserProfile extends Activity {

     @ViewById EditText       etUserName;
     @ViewById EditText       etUserWeight;
     @ViewById EditText       etUserAge;
     @ViewById Spinner        spinnerBodyType;
     @ViewById Spinner        spinnerGender;

     @ViewById Button         btnSave;

     @StringArrayRes String[] bodyTypes;
     @StringArrayRes String[] genders;

     private User             userToSave;

     @AfterViews void afterViews() {
          etUserName.setText("Eleonora Kosheleva");
          InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

          BodyTypeAdapter bodyTypeAdapter = new BodyTypeAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, new String[] { "Ektomorf", "Mezo", "Endo" });
          GenderAdapter genderAdpater = new GenderAdapter(ActivityUserProfile.this, android.R.layout.simple_spinner_item, new String[] { "Male", "Female" });
          spinnerBodyType.setAdapter(bodyTypeAdapter);
          spinnerGender.setAdapter(genderAdpater);
     }

     private void setUpUser() {
          User u = DataBaseUtils.getCurrentUser();
          if ( null != u ) {
               etUserName.setText(u.name);
               etUserAge.setText(String.valueOf(u.age));
               spinnerBodyType.setSelection(u.bodyType);
               spinnerGender.setSelection(u.gender);
               etUserWeight.setText(String.valueOf(u.weight));
          }
     }

     @Override protected void onResume() {
          super.onResume();
          setUpUser();
     }

     @Click void btnSave() {
          userToSave = DataBaseUtils.getCurrentUser();
          if ( null == userToSave ) {
               userToSave = new User(getApplicationContext());
          }
          userToSave.name = etUserName.getText().toString();
          try {
               userToSave.age = Integer.valueOf(etUserAge.getText().toString());
          } catch (Exception ex) {
               ex.printStackTrace();
               Utils.showCustomToast(ActivityUserProfile.this, "Wrong AGE", R.drawable.water);
               return;
          }
          userToSave.gender = spinnerGender.getSelectedItemPosition(); // Static for now :TODO
          userToSave.height = 0; // Static for now :TODO
          userToSave.weight = Double.valueOf(etUserWeight.getText().toString());
          userToSave.bodyType = spinnerBodyType.getSelectedItemPosition(); // Static for now :TODO
          userToSave.save();
          startActivity(new Intent(ActivityUserProfile.this, ActivityStart_.class));
     }

     @Click void btnClearProfile() {
          User.deleteAll(User.class);
          WaterConsumed.deleteAll(WaterConsumed.class);
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
     // ADAPTERS
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
     // ADAPTERS
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
