package com.progym.activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.progym.R;
import com.progym.model.User;
import com.progym.utils.AppSharedPreferences_;
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
     @ViewById LinearLayout                    llAdvice;

     @AnimationRes ( R.anim.fadein ) Animation fadeIn;

     @Pref AppSharedPreferences_               appPref;

     @AfterViews void afterViews() {
          List <User> users = DataBaseUtils.getUsers();
          if ( users.isEmpty() ) {
               DataBaseUtils.setCurrentUser(null);
          } else {
               DataBaseUtils.setCurrentUser(users.get(0));
               Utils.showCustomToast(ActivityStart.this, "Hi " + users.get(0).name, R.drawable.user);
          }

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
          // startActivity(new Intent(ActivityStart.this, LoginActivity.class));
     }

     @Click void llNutrition() {

          llNutrition.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, "Create profile, before continue, please", R.drawable.info);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivitySelectFoodOrWaterManagment_.class));

     }

     @Click void llAdvice() {
          llAdvice.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, "Create profile, before continue, please", R.drawable.info);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityAdviceLevel_1_.class));
     }

     @Click void llProfile() {
          llProfile.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityUserProfile_.class));
     }

     @Click void llProgress() {
          llProgress.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, "Create profile, before continue, please", R.drawable.info);
               return;
          }
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
