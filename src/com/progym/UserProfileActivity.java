package com.progym;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.progym.custom.BodyTypeItemView;
import com.progym.custom.BodyTypeItemView_;
import com.progym.model.User;
import com.progym.model.WaterConsumed;

@EActivity ( R.layout.user_profile_activity ) public class UserProfileActivity extends Activity {

     @ViewById EditText       etName;
     @ViewById Spinner        spinnerGender;

     @ViewById WheelView      wheelWeight;
     @ViewById WheelView      wheelHeight;
     @ViewById WheelView      wheelAge;
     @ViewById WheelView      wheelBodyType;

     @ViewById Button         btnSave;

     @StringArrayRes String[] bodyTypes;
     @StringArrayRes String[] genders;

     @AfterViews void afterViews() {
          spinnerGender.setAdapter(new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, genders));
          wheelBodyType.setViewAdapter(new NumericWheelAdapter(this, 20, 100));
          wheelWeight.setViewAdapter(new NumericWheelAdapter(this, 20, 100));
          wheelHeight.setViewAdapter(new NumericWheelAdapter(this, 20, 100));
          wheelAge.setViewAdapter(new NumericWheelAdapter(this, 5, 100));
          wheelBodyType.setViewAdapter(new BodyTypeAdapter(this));
          wheelBodyType.setCyclic(true);

          wheelWeight.setInterpolator(new AnticipateOvershootInterpolator());
          // wheel.addChangingListener(changedListener);
          // wheel.addScrollingListener(scrolledListener);
          etName.setText("Eleonora Kosheleva");
     }

     @Click void btnSave() {
          /*
           * User u = new User(getApplicationContext());
           * u.name = etName.getText().toString();
           * u.age = Integer.valueOf(etAge.getText().toString());
           * u.gender = spinnerGender.getSelectedItemPosition();
           * u.height = Integer.valueOf(etHeight.getText().toString());
           * // u.weight = Double.valueOf(etWeight.getText().toString());
           * u.bodyType = spinnerBodyType.getSelectedItemPosition();
           * u.save();
           */
          // startActivity(new Intent(UserProfileActivity.this, StartActivity_.class));
          int a = wheelBodyType.getCurrentItem();
          a = 100;
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
          private final String bodyTypes[]       = new String[] { "Ektomorf", "Mezomorf", "Endomorf" };
          // Countries flags
          private final int    bodyTypesImages[] = new int[] { R.drawable.glass, R.drawable.bottle, R.drawable.bottle2 };

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

}
