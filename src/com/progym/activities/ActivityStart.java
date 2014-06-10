package com.progym.activities;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import com.progym.R;
import com.progym.model.User;
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
          List <User> users = DataBaseUtils.getUsers();
          if ( users.size() == 0 ) {
               Utils.showToast(getApplicationContext(), "Go to -Your profile- and create user before continue", true);
          } else {
               if ( users.size() == 1 ) {
                    DataBaseUtils.setCurrentUser(DataBaseUtils.getUsers().get(0));
               } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStart.this).setTitle(R.string.choose_user);

                    ListView listView = new ListView(ActivityStart.this);

                    List <String> usersArray = new ArrayList <String>();

                    for ( User u : users ) {
                         usersArray.add(u.name);
                    }

                    ChooseUserAdapter adapter = new ChooseUserAdapter(ActivityStart.this, usersArray);

                    SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);

                    // Assign the ListView to the AnimationAdapter and vice versa
                    swingRightInAnimationAdapter.setAbsListView(listView);

                    listView.setAdapter(swingRightInAnimationAdapter);
                    builder.setView(listView);
                    final Dialog dialog = builder.create();

                    listView.setOnItemClickListener(new OnItemClickListener() {

                         @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                              String userName = adapter.getItemAtPosition(pos).toString();
                              try {
                                   DataBaseUtils.setCurrentUser(DataBaseUtils.getUserByName(userName));
                                   Utils.showCustomToast(ActivityStart.this, "Hi " + userName, R.drawable.profile);
                              } catch (Exception ex) {
                                   Utils.showCustomToast(ActivityStart.this, "Cannot find user " + userName, R.drawable.profile);
                              }
                              dialog.dismiss();
                         }
                    });

                    dialog.show();
               }
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
               Utils.showToast(getApplicationContext(), "Go to -Your profile- and create user before continue", true);
               return;
          }
          startActivity(new Intent(ActivityStart.this, ActivitySelectFoodOrWaterManagment_.class));
     }

     @Click void llProfile() {
          llProfile.startAnimation(fadeIn);
          startActivity(new Intent(ActivityStart.this, ActivityUserProfile_.class));
     }

     @Click void llProgress() {
          llProgress.startAnimation(fadeIn);
          if ( null == DataBaseUtils.getCurrentUser() ) {
               Utils.showToast(getApplicationContext(), "Go to -Your profile- and create user before continue", true);
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

     private class ChooseUserAdapter extends com.nhaarman.listviewanimations.ArrayAdapter <String> {

          private final Context mContext;

          public ChooseUserAdapter ( Context context , List <String> users ) {
               super(users);
               mContext = context;
          }

          @Override public View getView(int position, View convertView, ViewGroup parent) {
               View view = convertView;
               if ( view == null ) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.list_group, parent, false);
               }
               TextView tw = (TextView) view.findViewById(R.id.text);
               ImageView image = (ImageView) view.findViewById(R.id.image);
               image.setBackgroundResource(R.drawable.profile);
               tw.setText(getItem(position));
               return view;
          }
     }
}
