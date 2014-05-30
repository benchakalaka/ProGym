package com.progym.activities;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.progym.R;
import com.progym.custom.BodyTypeItemView;
import com.progym.custom.BodyTypeItemView_;
import com.progym.model.User;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;

@EActivity ( R.layout.activity_user_profile ) public class ActivityUserProfile extends Activity {

     @ViewById EditText       etName;

     @ViewById WheelView      wheelWeight;
     @ViewById WheelView      wheelHeight;
     @ViewById WheelView      wheelAge;
     @ViewById WheelView      wheelBodyType;
     @ViewById WheelView      wheelGender;

     @ViewById Button         btnSave;

     @StringArrayRes String[] bodyTypes;
     @StringArrayRes String[] genders;

     private User             userToSave;

     @AfterViews void afterViews() {
          wheelWeight.setViewAdapter(new NumericWheelAdapter(this, 0, 200));
          wheelHeight.setViewAdapter(new NumericWheelAdapter(this, 0, 200));
          wheelAge.setViewAdapter(new NumericWheelAdapter(this, 0, 99));

          wheelBodyType.setViewAdapter(new BodyTypeAdapter(this));
          wheelGender.setViewAdapter(new MaleFemaleAdapter(this));

          wheelWeight.setInterpolator(new AnticipateOvershootInterpolator());
          etName.setText("Eleonora Kosheleva");
          InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
     }

     private void setUpUser() {
          User u = DataBaseUtils.getCurrentUser();
          if ( null != u ) {
               etName.setText(u.name);
               wheelAge.setCurrentItem(u.age);
               wheelBodyType.setCurrentItem(u.bodyType); // , true
               wheelHeight.setCurrentItem(u.height); // , true
               wheelWeight.setCurrentItem((int) u.weight); // , true
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
          userToSave.name = etName.getText().toString();
          userToSave.age = Integer.valueOf(wheelAge.getCurrentItem());
          userToSave.gender = wheelGender.getCurrentItem();
          userToSave.height = wheelHeight.getCurrentItem();
          userToSave.weight = wheelWeight.getCurrentItem();
          userToSave.bodyType = wheelBodyType.getCurrentItem();
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

     /**
      * Adapter for countries
      */
     private class BodyTypeAdapter extends AbstractWheelTextAdapter {
          // Countries names
          private final String bodyTypes[]       = new String[] { "Ectomorf", "Mezomorf", "Endomorf" };
          // Countries flags
          private final int    bodyTypesImages[] = new int[] { R.drawable.ectomorf, R.drawable.mezomorf, R.drawable.endomorf };

          /**
           * Constructor
           */
          protected BodyTypeAdapter ( Context context ) {
               super(context, R.layout.custom_wheel_bodytype_item, NO_RESOURCE);
          }

          @Override public View getItem(int index, View cachedView, ViewGroup parent) {
               BodyTypeItemView view = BodyTypeItemView_.build(context);
               view.ivBodyType.setImageResource(bodyTypesImages[index]);
               view.twBodyType.setText(bodyTypes[index]);
               return view;
          }

          @Override public int getItemsCount() {
               return bodyTypes.length;
          }

          @Override protected CharSequence getItemText(int index) {
               return bodyTypes[index];
          }
     }

     /**
      * Adapter for countries
      */
     private class MaleFemaleAdapter extends AbstractWheelTextAdapter {
          // Countries names
          private final String bodyTypes[]       = new String[] { "Male", "Female" };
          // Countries flags
          private final int    bodyTypesImages[] = new int[] { R.drawable.female, R.drawable.male };

          /**
           * Constructor
           */
          protected MaleFemaleAdapter ( Context context ) {
               super(context, R.layout.custom_wheel_bodytype_item, NO_RESOURCE);
          }

          @Override public View getItem(int index, View cachedView, ViewGroup parent) {
               BodyTypeItemView view = BodyTypeItemView_.build(context);
               view.ivBodyType.setImageResource(bodyTypesImages[index]);
               view.twBodyType.setText(bodyTypes[index]);
               return view;
          }

          @Override public int getItemsCount() {
               return bodyTypes.length;
          }

          @Override protected CharSequence getItemText(int index) {
               return bodyTypes[index];
          }
     }

}
