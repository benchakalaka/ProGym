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
          }
          // Set up ingridient database
          DataBaseUtils.setUpIngridientsDatabase(getApplicationContext());
     }

     @Click void llNutrition() {

          llNutrition.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivitySelectFoodOrWaterManagment_.class));

     }

     @Click void llAdvice() {
          llAdvice.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityChooseTypeOfAdvice_.class));
     }

     @Click void llProfile() {
          llProfile.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityUserProfile_.class));
     }

     @Click void llProgress() {
          llProgress.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showCustomToast(ActivityStart.this, R.string.create_profile_please, R.drawable.info);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivityChooseProgressType_.class));
     }

     @Override public void onBackPressed() {
          moveTaskToBack(true);
     }
}
