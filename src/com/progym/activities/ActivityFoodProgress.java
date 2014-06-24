package com.progym.activities;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.apache.commons.lang3.time.DateUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.progym.R;
import com.progym.R.color;
import com.progym.constants.GlobalConstants;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.fragments.FoodProgressDailyPieFragment;
import com.progym.custom.fragments.FoodProgressDailyPieFragment_;
import com.progym.custom.fragments.FoodProgressMonthlyLineFragment;
import com.progym.custom.fragments.FoodProgressMonthlyLineFragment_;
import com.progym.custom.fragments.FoodProgressYearlyLineFragment;
import com.progym.custom.fragments.FoodProgressYearlyLineFragment_;
import com.progym.interfaces.IProgressActivity;
import com.progym.model.Meal;
import com.progym.utils.DataBaseUtils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.food_progress_activity ) public class ActivityFoodProgress extends ActivityAbstractProgress implements IProgressActivity {

     @Click void twDaily() {

          showProgressBar(ActivityFoodProgress.this);

          Thread t = new Thread(new Runnable() {

               @Override public void run() {

                    FRAGMENT_TYPE = DAILY;
                    List <Meal> meals = DataBaseUtils.getAllPlates();
                    HashMap <Date, Integer> datesToHighligt = new HashMap <Date, Integer>();
                    if ( null != meals && !meals.isEmpty() ) {

                         for ( Meal meal : meals ) {
                              try {
                                   datesToHighligt.put(DateUtils.parseDate(meal.date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS_MILLIS), com.caldroid.R.color.caldroid_sky_blue);
                              } catch (ParseException e) {
                                   e.printStackTrace();
                              }
                         }
                    }
                    // set up title
                    Bundle bundle = new Bundle();
                    bundle.putString(com.roomorama.caldroid.CaldroidFragment.DIALOG_TITLE, getResources().getString(R.string.select_date));
                    bundle.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
                    calendar.setArguments(bundle);

                    // highlight dates in calendar with blue color
                    calendar.setBackgroundResourceForDates(datesToHighligt);
                    calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);

                    hideProgressBar(ActivityFoodProgress.this);
               }
          });
          t.start();

     }

     @Override protected void onPause() {
          super.onPause();
          startActivity(new Intent(ActivityFoodProgress.this, ActivityStart_.class));
     }

     @Click void twMonthly() {
          FRAGMENT_TYPE = MONTHLY;
          updateFragment(FRAGMENT_TYPE);
     }

     @Click void twYearly() {
          FRAGMENT_TYPE = YEARLY;
          updateFragment(FRAGMENT_TYPE);
     }

     @AfterViews void afterViews() {
          viewPager.setAdapter(new ProgressViewPagerAdapter(getSupportFragmentManager()));
          calendar = new CaldroidFragmentCustom();
          calendar.setCaldroidListener(onDateChangeListener);
          twDaily.setBackgroundColor(color.red);
     }

     @Override public void updateFragment(int fragmentType) {
          setProperPadding(fragmentType);
          switch (fragmentType) {
               case DAILY:
                    viewPager.setCurrentItem(DAILY, true);
                    ((ProgressViewPagerAdapter) viewPager.getAdapter()).bargraphDaily.setPieData(SELECTED_DATE, true);
                    break;
               case MONTHLY:
                    viewPager.setCurrentItem(MONTHLY, true);
                    ((ProgressViewPagerAdapter) viewPager.getAdapter()).linegraphMonthly.setLineData3(new Date(), true);
                    break;
               case YEARLY:
                    viewPager.setCurrentItem(YEARLY, true);
                    ((ProgressViewPagerAdapter) viewPager.getAdapter()).linegraphYearly.setYearProgressData(SELECTED_DATE, true);
                    break;
          }
     }

     /**
      * A simple pager adapter
      */
     private class ProgressViewPagerAdapter extends FragmentStatePagerAdapter {

          private static final int               NUM_OF_FRAGMENTS = 3;
          public FoodProgressDailyPieFragment    bargraphDaily    = new FoodProgressDailyPieFragment_();
          public FoodProgressMonthlyLineFragment linegraphMonthly = new FoodProgressMonthlyLineFragment_();
          public FoodProgressYearlyLineFragment  linegraphYearly  = new FoodProgressYearlyLineFragment_();

          public ProgressViewPagerAdapter ( FragmentManager fm ) {
               super(fm);
          }

          @Override public Parcelable saveState() {
               return null;
          }

          @Override public Fragment getItem(int position) {
               Fragment returnFragment = null;
               switch (position) {
                    case DAILY:
                         returnFragment = bargraphDaily;
                         break;

                    case MONTHLY:
                         returnFragment = linegraphMonthly;
                         break;

                    case YEARLY:
                         returnFragment = linegraphYearly;
                         break;

               }
               return returnFragment;
          }

          @Override public int getCount() {
               return NUM_OF_FRAGMENTS;
          }
     }
}
