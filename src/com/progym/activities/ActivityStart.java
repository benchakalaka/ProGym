package com.progym.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.progym.R;
import com.progym.custom.SetAlarmView_;
import com.progym.receivers.AlarmWaterReceiver;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_start ) public class ActivityStart extends Activity {

     // Sugar basic usage - List<Book> books = Book.find(Book.class, "author = ?", new String{author.getId()});
     // Book.find(Note.class, "name = ? and title = ?", "satya", "title1");
     // http://satyan.github.io/sugar/query.html - query for sugar
     // List<Note> notes = Note.findWithQuery(Note.class, "Select * from Note where name = ?", "satya");
     // Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"),Condition.prop("prop").eq(2)).list();

     @ViewById LinearLayout                    llNutrition;
     @ViewById LinearLayout                    llProgress;
     @ViewById LinearLayout                    llProfile;

     @AnimationRes ( R.anim.fadein ) Animation fadeIn;

     @AfterViews void afterViews() {
          DataBaseUtils.setCurrentUser(DataBaseUtils.getUserByName("Eleonora Kosheleva"));
          Display display = getWindowManager().getDefaultDisplay();
          Point size = new Point();
          display.getSize(size);
          int marginLeft = size.x / 4;

          MarginLayoutParams params = (MarginLayoutParams) llNutrition.getLayoutParams();
          params.leftMargin = marginLeft;
          llNutrition.setLayoutParams(params);

          params = (MarginLayoutParams) llProgress.getLayoutParams();
          params.leftMargin = marginLeft;
          llProgress.setLayoutParams(params);
  
          params = (MarginLayoutParams) llProfile.getLayoutParams();
          params.leftMargin = marginLeft;
          llProfile.setLayoutParams(params);

          // Set up ingridient database
          DataBaseUtils.setUpIngridientsDatabase(getApplicationContext());

          Cursor c = DataBaseUtils.test();
          while ( c.moveToNext() ) {
               float d = c.getFloat(1);
               d = c.getFloat(2);
               d = c.getFloat(3);
               Utils.log(String.valueOf(d));
          } 

          Dialog d = new Dialog(ActivityStart.this);

          d.setContentView(SetAlarmView_.build(getApplicationContext()));
          d.show();
     }

     @Override protected void onResume() {
          super.onResume();
          AlarmWaterReceiver a = new AlarmWaterReceiver();
          a.SetAlarm(ActivityStart.this);
     }

     @Click void llNutrition() {
          llNutrition.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivitySelectFoodOrWaterManagment_.class));
     }

     @Click void llProfile() {
          llProfile.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityUserProfile_.class));
     }

     @Click void llProgress() {
          llProgress.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityChooseProgressType_.class));
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
