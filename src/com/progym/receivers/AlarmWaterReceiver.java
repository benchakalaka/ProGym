package com.progym.receivers;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.progym.R;
import com.progym.utils.Utils;

public class AlarmWaterReceiver extends BroadcastReceiver {

	static Random	random	= new Random();

	@SuppressLint ( "NewApi" ) @Override public void onReceive(Context context, Intent intent) {

		try {
			SharedPreferences prefs = context.getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE);
			String alarmTime = Utils.isNeedToAddAlarm(prefs);
			if ( !alarmTime.equals("") ) {
				Notification noti = new NotificationCompat.Builder(context).setContentTitle("Dont miss to drink some water...").setContentText("ProGym ("+alarmTime+")").setSmallIcon(R.drawable.water).build();
				NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				// hide the notification after its selected
				noti.flags |= Notification.FLAG_AUTO_CANCEL;
				notificationManager.notify(random.nextInt(100), noti);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void SetAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmWaterReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		/*// am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR, pi);
		// start alarm now
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10, pi);*/
		
		// Set the alarm to start at approximately 2:00 p.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 30);
		// With setInexactRepeating(), you have to use one of the AlarmManager interval
		am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000*60/*AlarmManager.INTERVAL_HALF_HOUR*/, pi);
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, AlarmWaterReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
