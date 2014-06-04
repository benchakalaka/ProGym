package com.progym.receivers;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.progym.R;

public class AlarmWaterReceiver extends BroadcastReceiver {
     final public static String ONE_TIME = "onetime";

     static Random              random   = new Random();

     @SuppressLint ( "NewApi" ) @Override public void onReceive(Context context, Intent intent) {
          Notification noti = new NotificationCompat.Builder(context).setContentTitle("Dont miss to drink some water...").setContentText("ProGym").setSmallIcon(R.drawable.water).build();

          NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
          // hide the notification after its selected
          noti.flags |= Notification.FLAG_AUTO_CANCEL;
          notificationManager.notify(random.nextInt(), noti);
     }

     public void SetAlarm(Context context) {
          AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
          Intent intent = new Intent(context, AlarmWaterReceiver.class);
          PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
          // am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR, pi);
          // start alarm now
          am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10, pi);
     }

     public void CancelAlarm(Context context) {
          Intent intent = new Intent(context, AlarmWaterReceiver.class);
          PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
          AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
          alarmManager.cancel(sender);
     }
}
