package com.progym.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.progym.R;
import com.progym.constants.GlobalConstants;

public class Utils {
     private final static SimpleDateFormat dateFormat = new SimpleDateFormat();

     /**
      * @param monthNumber
      *             Month Number starts with 0. For <b>January</b> it is <b>0</b> and for <b>December</b> it is <b>11</b>.
      * @param year
      * @return amount of days in specific month
      */
     public static int getDaysInMonth(int monthNumber, int year) {
          int days = 0;
          if ( monthNumber >= 0 && monthNumber < 12 ) {
               try {
                    Calendar calendar = Calendar.getInstance();
                    int date = 1;
                    calendar.set(year, monthNumber, date);
                    days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
               } catch (Exception e) {
                    if ( e != null ) {
                         e.printStackTrace();
                    }
               }
          }
          return days;
     }

     public static String formatDate(Date dateToFormat, String pattern) {
          dateFormat.applyPattern(pattern);
          return dateFormat.format(dateToFormat).toString();
     }

     /**
      * "EEEE" - Thursday /////// "MMM" - Jun /////// "MM" - 06 /////// "yyyy" - 2013 /////// "dd"- 20 /////////
      * 
      * @param dateToFormat
      *             Date object
      * @param pattern
      *             one of EEEE, MMM, MM, yyyy, dd values
      */
     public static String getSpecificDateValue(Date dateToFormat, String pattern) {
          return android.text.format.DateFormat.format(pattern, dateToFormat).toString();
     }

     public static void showCustomToast(final Activity activity, final String message, final BitmapDrawable background) {
          activity.runOnUiThread(new Runnable() {

               @SuppressWarnings ( "deprecation" ) @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = ((ImageView) layout.findViewById(R.id.toast_image_view));
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(message);
                         image.setBackgroundDrawable(background);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         // text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         e.printStackTrace();
                         Utils.showToast(activity.getApplicationContext(), message, true);
                    }
               }
          });
     }

     /**
      * Show short toast on UI thread
      * 
      * @param context
      *             application context or activity's context
      * @param message
      *             text representation of message to display
      * @param isShort
      *             define toast duration
      */
     public static void showToast(final Context context, final String message, boolean isShort) {
          final int toastDuration = isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
          // Define toast duration
          try {
               Toast.makeText(context, message, toastDuration).show();
          } catch (Exception ex) {
               Activity activity = (Activity) context;
               activity.runOnUiThread(new Runnable() {

                    @Override public void run() {
                         Toast.makeText(context, message, toastDuration).show();
                    }
               });
          }
     }

     public static void showCustomToast(final Activity activity, final String message, final int imageResourcesId) {
          activity.runOnUiThread(new Runnable() {

               @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = ((ImageView) layout.findViewById(R.id.toast_image_view));
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(message);
                         image.setImageResource(imageResourcesId);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         // text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         e.printStackTrace();
                         Utils.showToast(activity.getApplicationContext(), message, true);
                    }
               }
          });
     }

     public static void showCustomToast(final Activity activity, final int messageResourcesId, final int imageResourcesId) {

          activity.runOnUiThread(new Runnable() {

               @Override public void run() {
                    try {
                         View layout = activity.getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

                         ImageView image = ((ImageView) layout.findViewById(R.id.toast_image_view));
                         TextView text = (TextView) layout.findViewById(R.id.toast_text_view);

                         text.setText(activity.getResources().getString(messageResourcesId));
                         image.setImageResource(imageResourcesId);

                         Toast toast = new Toast(activity.getApplicationContext());

                         toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                         toast.setDuration(Toast.LENGTH_SHORT);
                         toast.setView(layout);

                         toast.show();
                         // text.startAnimation(AnimationManager.load(android.R.anim.slide_in_left));
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }
          });
     }

     public static int getImageIdByGroupName(String groupName) {
          int retResourceId = R.drawable.ready;
          if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_MEAT_CATALOGUE) ) {
               retResourceId = R.drawable.meat;
          } else if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_PORRIDGE_CATALOGUE) ) {
               retResourceId = R.drawable.porridge;
          } else if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_VEGITABLES_CATALOGUE) ) {
               retResourceId = R.drawable.vegetables;
          } else if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_READY_MEALS_CATALOGUE) ) {
               retResourceId = R.drawable.ready;
          } else if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_OTHER_CATALOGUE) ) {
               retResourceId = R.drawable.custom_type;
          } else if ( groupName.equals(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_FRUITS_CATALOGUE) ) {
               retResourceId = R.drawable.fruits;
          }
          return retResourceId;
     }

     public static int getImageIdByGroupPositionInExpListView(int groupPosition) {

          int retResourceId = 0;
          switch (groupPosition) {
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_MEAT_CATALOGUE:
                    retResourceId = R.drawable.meat;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_FRUITS_CATALOGUE:
                    retResourceId = R.drawable.fruits;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_OTHER_CATALOGUE:
                    retResourceId = R.drawable.custom_type;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_PORRIDGE_CATALOGUE:
                    retResourceId = R.drawable.porridge;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE:
                    retResourceId = R.drawable.ready;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_VEGITABLES_CATALOGUE:
                    retResourceId = R.drawable.vegetables;
                    break;
          }

          return retResourceId;
     }

     public static String getGroupNameByCataloguePosition(int groupPosition) {
          String retResourceId = "";
          switch (groupPosition) {
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_MEAT_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_MEAT_CATALOGUE;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_FRUITS_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_FRUITS_CATALOGUE;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_OTHER_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_OTHER_CATALOGUE;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_PORRIDGE_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_PORRIDGE_CATALOGUE;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_READY_MEALS_CATALOGUE;
                    break;

               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_VEGITABLES_CATALOGUE:
                    retResourceId = GlobalConstants.GROUP_INGRIDIENT_NAME.STR_VEGITABLES_CATALOGUE;
                    break;
          }

          return retResourceId;
     }

     public static void log(String message) {

          Log.d(GlobalConstants.TAG, message == null ? "message == null" : message);
     }

     public static void log(int message) {
          Log.d(GlobalConstants.TAG, String.valueOf(message));
     }

     public static int getImageIdByVolume(int volume) {

          int retResourceId = 0;
          switch (volume) {
               case GlobalConstants.WATER_VOLUMES.INT_GLASS_250_ML:
                    retResourceId = R.drawable.glass_025;
                    break;

               case GlobalConstants.WATER_VOLUMES.INT_BOTTLE_500_ML:
                    retResourceId = R.drawable.bottle_05;
                    break;

               case GlobalConstants.WATER_VOLUMES.INT_BOTTLE_1_L:
                    retResourceId = R.drawable.bottle_1;
                    break;

               case GlobalConstants.WATER_VOLUMES.INT_BOTTLE_2_L:
                    retResourceId = R.drawable.bottle_2;
                    break;
               default:
                    return R.drawable.custom;
          }

          return retResourceId;
     }

     public static int getImageIdByTag(String tag) {
          // by default return glass
          int retResourceId = 0;

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_GLASS_250ML) ) {
               retResourceId = R.drawable.glass_025;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_500ML) ) {
               retResourceId = R.drawable.bottle_05;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_1L) ) {
               retResourceId = R.drawable.bottle_1;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_2L) ) {
               retResourceId = R.drawable.bottle_2;
          }

          if ( 0 == retResourceId ) {
               retResourceId = R.drawable.custom;
          }

          return retResourceId;
     }

     public static int getVolumeByTag(String tag) {
          // by default
          int retWaterVolume = 0;

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_GLASS_250ML) ) {
               retWaterVolume = GlobalConstants.WATER_VOLUMES.INT_GLASS_250_ML;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_500ML) ) {
               retWaterVolume = GlobalConstants.WATER_VOLUMES.INT_BOTTLE_500_ML;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_1L) ) {
               retWaterVolume = GlobalConstants.WATER_VOLUMES.INT_BOTTLE_1_L;
          }

          if ( tag.equals(GlobalConstants.WATER_VOLUMES.STR_BOTTLE_2L) ) {
               retWaterVolume = GlobalConstants.WATER_VOLUMES.INT_BOTTLE_2_L;
          }
          // Custom water volume
          if ( 0 == retWaterVolume ) {
               retWaterVolume = Integer.valueOf(tag);
          }

          return retWaterVolume;
     }

     // for debugging, return string with time (display it in notification manager)
     public static String isNeedToAddAlarm(SharedPreferences appPref) {
          String date = Utils.formatDate(new Date(), "HHmm");
          Map <String, String> allAlarms = (Map <String, String>) appPref.getAll();

          for ( Map.Entry <String, String> entry : allAlarms.entrySet() ) {
               try {
                    String value = entry.getValue();
                    if ( date.equals(value) ) { return value; }
               } catch (Exception ex) {
                    ex.printStackTrace();
               }
          }
          return "";
     }

     /*
      * Working copy
      * public static boolean isNeedToAddAlarm(SharedPreferences appPref) {
      * boolean result = false;
      * Date now = new Date();
      * String date = Utils.formatDate(now, "HHmm");
      * Map <String, String> allAlarms = (Map <String, String>) appPref.getAll();
      * for ( Map.Entry <String, String> entry : allAlarms.entrySet() ) {
      * if ( date.equals(entry.getValue()) ) return true;
      * }
      * return result;
      * }
      */

     public static List <String> getIngridientsListByCatalogue(int cataloguePosition) {
          switch (cataloguePosition) {
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_MEAT_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.MEAT_LIST;
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_FRUITS_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.FRUITS_LIST;
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_PORRIDGE_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.PORRIDGE_LIST;
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_VEGITABLES_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.VEGITABLES_LIST;
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.READY_MEALS;
               case GlobalConstants.GROUP_INGRIDIENT_NAME.INT_OTHER_CATALOGUE:
                    return GlobalConstants.INGRIDIENTS.OTHER_LIST;
          }
          return new ArrayList <String>();
     }
}
