package com.progym.receivers;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import com.progym.R;

// ALERT DIALOG
// Sources : http://techblogon.com/alert-dialog-with-edittext-in-android-example-with-source-code/

public class AlertDialogActivity extends Activity {

     @Override protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          // setContentView();

          WindowManager.LayoutParams params = getWindow().getAttributes();
          params.x = 0;
          params.height = 1;
          params.width = 1;
          params.y = 0;
          this.getWindow().setAttributes(params);

          Dialog d = new Dialog(AlertDialogActivity.this);
          d.setContentView(R.layout.dialog_alarm);
          d.show();
     }
}