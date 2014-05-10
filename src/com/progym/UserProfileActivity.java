package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.progym.model.User;
import com.progym.model.WaterConsumed;

@EActivity ( R.layout.user_profile_activity )
public class UserProfileActivity extends Activity {

	@ViewById EditText	etName;
	@ViewById EditText	etGender;
	@ViewById EditText	etWeight;
	@ViewById EditText	etHeight;
	@ViewById EditText	etAge;
	@ViewById EditText	etBodyType;

	@ViewById Button	btnSave;

	@AfterViews void afterViews() {
		etName.setText("Eleonora Kosheleva");
		etGender.setText("F");
		etWeight.setText("59");
		etHeight.setText("168");
		etAge.setText("21");
		etBodyType.setText("0");
	}

	@Click void btnSave() {
		User u = new User(getApplicationContext());

		u.name = etName.getText().toString();
		u.age = Integer.valueOf(etAge.getText().toString());
		u.gender = etGender.getText().toString();
		u.height = Integer.valueOf(etHeight.getText().toString());
		u.weight = Double.valueOf(etWeight.getText().toString());
		u.bodyType = Integer.valueOf(etBodyType.getText().toString());
		u.save();

		startActivity(new Intent(UserProfileActivity.this, StartActivity_.class));
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
}
