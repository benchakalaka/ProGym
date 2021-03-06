package com.progym.activities;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import com.progym.R;
import com.progym.R.color;
import com.progym.R.drawable;
import com.progym.custom.NonSwipeableViewPager;
import com.progym.custom.fragments.CalloriesProgressMonthlyLineFragment;
import com.progym.custom.fragments.CalloriesProgressMonthlyLineFragment_;
import com.progym.custom.fragments.CalloriesProgressYearlyLineFragment;
import com.progym.custom.fragments.CalloriesProgressYearlyLineFragment_;
import com.progym.interfaces.IProgressActivity;

@EActivity ( R.layout.callories_progress_activity ) public class ActivityCalloriesProgress extends FragmentActivity implements IProgressActivity {

     static Date                            SELECTED_DATE = new Date();

     @ViewById public TextView              twMonthly;
     @ViewById public TextView              twYearly;
     @ViewById public NonSwipeableViewPager viewPager;
     public int                             FRAGMENT_TYPE;
     public static final int                MONTHLY       = 0;
     public static final int                YEARLY        = 1;

     @Click void twMonthly() {
          FRAGMENT_TYPE = MONTHLY;
          updateFragment(FRAGMENT_TYPE);
     }

     @Click void twYearly() {
          FRAGMENT_TYPE = YEARLY;
          updateFragment(FRAGMENT_TYPE);
     }

     @AfterViews void afterViews() {
          viewPager.setAdapter(new ProgressCalloriesViewPagerAdapter(getSupportFragmentManager()));
          twMonthly.setBackgroundColor(color.red);
     }

     /**
      * DAILY = selectDailyTextView;
      * MONTHLY = selectMontlyTextView;
      * YEARLY = selectMontlyYearlyView;
      * 
      * @param viewIdentifier
      */
     public void setProperPadding(int viewIdentifier) {
          setProperSelectedView(viewIdentifier);
          twYearly.setPadding(6, 6, 6, 6);
          twMonthly.setPadding(6, 6, 6, 6);
     }

     @Override public void updateFragment(int fragmentType) {
          setProperPadding(fragmentType);
          switch (fragmentType) {
               case MONTHLY:
                    viewPager.setCurrentItem(MONTHLY, true);
                    ((ProgressCalloriesViewPagerAdapter) viewPager.getAdapter()).linegraphMonthly.setLineData3(new Date(), false);
                    break;
               case YEARLY:
                    viewPager.setCurrentItem(YEARLY, true);
                    ((ProgressCalloriesViewPagerAdapter) viewPager.getAdapter()).linegraphYearly.setYearProgressData(SELECTED_DATE, true);
                    break;
          }
     }

     /**
      * A simple pager adapter
      */
     private class ProgressCalloriesViewPagerAdapter extends FragmentStatePagerAdapter {

          private static final int                    NUM_OF_FRAGMENTS = 2;
          public CalloriesProgressMonthlyLineFragment linegraphMonthly = new CalloriesProgressMonthlyLineFragment_();
          public CalloriesProgressYearlyLineFragment  linegraphYearly  = new CalloriesProgressYearlyLineFragment_();

          public ProgressCalloriesViewPagerAdapter ( FragmentManager fm ) {
               super(fm);
          }

          @Override public Fragment getItem(int position) {
               Fragment returnFragment = null;
               switch (position) {

                    case MONTHLY:
                         returnFragment = linegraphMonthly;
                         break;

                    case YEARLY:
                         returnFragment = linegraphYearly;
                         break;

               }
               return returnFragment;
          }

          @Override public Parcelable saveState() {
               return null;
          }

          @Override public int getCount() {
               return NUM_OF_FRAGMENTS;
          }
     }

     /**
      * DAILY = selectDailyTextView;
      * MONTHLY = selectMontlyTextView;
      * YEARLY = selectMontlyYearlyView;
      * 
      * @param viewIdentifier
      */
     private void setProperSelectedView(int viewIdentifier) {
          switch (viewIdentifier) {
               case MONTHLY:
                    twMonthly.setBackgroundColor(color.red);
                    twYearly.setBackgroundResource(drawable.background_round_transparent_real);
                    break;

               case YEARLY:
                    twMonthly.setBackgroundResource(drawable.background_round_transparent_real);
                    twYearly.setBackgroundColor(color.red);
                    break;
          }
     }

}
