package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@EActivity ( R.layout.start_activity ) public class StartActivity extends Activity {

     @ViewById TextView twTest;

     @AfterViews void afterViews() {
          twTest.setText("Test successful");
     }

     @Override public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
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
