package com.progym;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.progym.model.User;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

@EActivity ( R.layout.start_activity ) public class StartActivity extends Activity {

	// Sugar basic usage - List<Book> books = Book.find(Book.class, "author = ?", new String{author.getId()});
	// Book.find(Note.class, "name = ? and title = ?", "satya", "title1");
	// http://satyan.github.io/sugar/query.html - query for sugar
	// List<Note> notes = Note.findWithQuery(Note.class, "Select * from Note where name = ?", "satya");
	// Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"),Condition.prop("prop").eq(2)).list();

     @ViewById TextView twTest;
     @ViewById Button btnWater;

     @AfterViews void afterViews() {
          twTest.setText("Test successful");
          User u = new User(getApplicationContext());
          u.age = 23;
          u.gender = "M";
          u.height = 177;
          u.name = "Igor Karpachev";
          u.weight = 85;
          u.bodyType = 0;
          u.save();
          
          List<User> users = User.listAll(User.class);
          
          for (User single: users){
          	Log.d("TAG", single.name);
          }
     }
     
     @Click void btnWater () {
     	startActivity(new Intent(StartActivity.this, WaterManagementActivity_.class));
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
