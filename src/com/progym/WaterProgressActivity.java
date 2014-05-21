package com.progym;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.apache.commons.lang3.time.DateUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.progym.constants.GlobalConstants;
import com.progym.custom.fragments.progress.WaterProgressDailyBarFragment;
import com.progym.custom.fragments.progress.WaterProgressDailyBarFragment_;
import com.progym.custom.fragments.progress.WaterProgressMonthlyLineFragment;
import com.progym.custom.fragments.progress.WaterProgressMonthlyLineFragment_;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.progress_activity ) public class WaterProgressActivity extends ProgymSuperProgressActivity implements IProgressActivity {

     final String months[] = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

     @Click void twDaily() {
          FRAGMENT_TYPE = DAILY;
          calendar = new CaldroidFragment();
          calendar.setCaldroidListener(onDateChangeListener);
          List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumed();
          HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
          Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
          for ( WaterConsumed singleDate : list ) {
               try {
                    datesAndColour.put(DateUtils.parseDate(singleDate.date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), R.color.caldroid_sky_blue);
               } catch (ParseException e) {
                    e.printStackTrace();
                    Utils.log(e.getMessage());
               }
          }
          // highlight dates in calendar with blue color
          calendar.setBackgroundResourceForDates(datesAndColour);
          calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);

     }

     @Click void twMonthly() {
          FRAGMENT_TYPE = MONTHLY;
          updateFragment(FRAGMENT_TYPE);
     }

     @AfterViews void afterViews() {
          viewPager.setAdapter(new ProgressViewPagerAdapter(getSupportFragmentManager()));
          /*
           * PieSlice slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.green_light));
           * slice.setSelectedColor(resources.getColor(R.color.transparent_orange));
           * slice.setValue(2);
           * piegraph.addSlice(slice);
           * slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.orange));
           * slice.setValue(3);
           * piegraph.addSlice(slice);
           * slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.purple));
           * slice.setValue(8);
           * piegraph.addSlice(slice);
           * piegraph.setOnSliceClickedListener(new OnSliceClickedListener() {
           * @Override public void onClick(int index) {
           * Toast.makeText(ProgressActivity.this,
           * "Slice " + index + " clicked",
           * Toast.LENGTH_SHORT)
           * .show();
           * }
           * });
           */
     }

     @Override public void updateFragment(int fragmentType) {
          String date;
          switch (fragmentType) {
               case DAILY:
                    viewPager.setCurrentItem(DAILY, true);
                    Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
                    date = Utils.dateFormat.format(SELECTED_DATE);
                    // Add to expandable list view ready meal date
                    ((ProgressViewPagerAdapter) viewPager.getAdapter()).bargraph.setBarData(date, DataBaseUtils.getWaterUserShouldConsumePerDay(), DataBaseUtils
                              .getConsumedPerDay(date));
                    break;
               case MONTHLY:

                    Calendar calendar = Calendar.getInstance();
                    final WheelView month = new WheelView(getApplicationContext());
                    // month
                    int curMonth = calendar.get(Calendar.MONTH);

                    month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
                    month.setCurrentItem(curMonth);

                    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setView(month);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         @Override public void onClick(DialogInterface dialog, int whichButton) {
                              Toast.makeText(getApplicationContext(), months[month.getCurrentItem()], Toast.LENGTH_SHORT).show();
                              viewPager.setCurrentItem(MONTHLY, true);
                              Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM);
                              SELECTED_DATE.setMonth(month.getCurrentItem());
                              // Add to expandable list view ready meal date
                              ((ProgressViewPagerAdapter) viewPager.getAdapter()).linegpah.setLineData2(DataBaseUtils.getAllWaterConsumedInMonth(Utils.dateFormat
                                        .format(SELECTED_DATE)), (int)
                                        DataBaseUtils.getWaterUserShouldConsumePerDay(), month.getCurrentItem());
                         }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                         @Override public void onClick(DialogInterface dialog, int whichButton) {
                              dialog.cancel();
                         }
                    });
                    alert.show();

                    // month.addChangingListener(listener);

                    /*
                     * UUUUUUUUUUUUUUSEFUL, WEEK PICKER
                     * final Calendar c = Calendar.getInstance();
                     * int y = c.get(Calendar.YEAR) + 4;
                     * int m = c.get(Calendar.MONTH) - 2;
                     * int d = c.get(Calendar.DAY_OF_MONTH);
                     * final String[] MONTH = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                     * DatePickerDialog dp = new DatePickerDialog(WaterProgressActivity.this,
                     * new DatePickerDialog.OnDateSetListener() {
                     * @Override public void onDateSet(DatePicker view, int year,
                     * int monthOfYear, int dayOfMonth) {
                     * String erg = "";
                     * erg = String.valueOf(dayOfMonth);
                     * erg += "." + String.valueOf(monthOfYear + 1);
                     * erg += "." + year;
                     * }
                     * }, y, m, d);
                     * dp.setTitle("Calender");
                     * dp.setMessage("Select Your Graduation date Please?");
                     * dp.show();
                     */
                    break;
               case YEARLY:

                    break;
               case RANGE:

                    break;
          }
     }

     /**
      * A simple pager adapter
      */
     private class ProgressViewPagerAdapter extends FragmentStatePagerAdapter {

          public WaterProgressDailyBarFragment    bargraph = new WaterProgressDailyBarFragment_();
          public WaterProgressMonthlyLineFragment linegpah = new WaterProgressMonthlyLineFragment_();

          public ProgressViewPagerAdapter ( FragmentManager fm ) {
               super(fm);
          }

          @Override public Fragment getItem(int position) {
               Fragment returnFragment = null;
               switch (position) {
                    case DAILY:
                         returnFragment = bargraph;
                         break;

                    case MONTHLY:
                         returnFragment = linegpah;
                         break;

               }
               return returnFragment;
          }

          @Override public int getCount() {
               return 2;
          }
     }

     /**
      * Adapter for string based wheel. Highlights the current value.
      */
     private class DateArrayAdapter extends ArrayWheelAdapter <String> {
          // Index of current item
          int currentItem;
          // Index of item to be highlighted
          int currentValue;

          /**
           * Constructor
           */
          public DateArrayAdapter ( Context context , String[] items , int current ) {
               super(context, items);
               this.currentValue = current;
               setTextSize(16);
          }

          @Override protected void configureTextView(TextView view) {
               super.configureTextView(view);
               if ( currentItem == currentValue ) {
                    view.setTextColor(0xFF0000F0);
               }
               view.setTypeface(Typeface.SANS_SERIF);
          }

          @Override public View getItem(int index, View cachedView, ViewGroup parent) {
               currentItem = index;
               return super.getItem(index, cachedView, parent);
          }
     }

}
