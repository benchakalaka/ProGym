package com.progym.activities;

import java.util.Date;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.progym.R.color;
import com.progym.R.drawable;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.NonSwipeableViewPager;
import com.progym.interfaces.IProgressActivity;

@EBean public abstract class ActivityAbstractProgress extends FragmentActivity implements IProgressActivity {
     static Date                                          SELECTED_DATE        = new Date();
     CaldroidFragmentCustom                               calendar;
 
     @ViewById public TextView                            twDaily;
     @ViewById public TextView                            twMonthly;
     @ViewById public TextView                            twYearly;
     @ViewById public NonSwipeableViewPager               viewPager;
     public int                                           FRAGMENT_TYPE;
     public static final int                              DAILY                = 0;
     public static final int                              MONTHLY              = 1;
     public static final int                              YEARLY               = 2;
     public static final int                              RANGE                = 3;

     // Setup listener
     public final com.roomorama.caldroid.CaldroidListener onDateChangeListener = new com.roomorama.caldroid.CaldroidListener() {
                                                                                    @Override public void onSelectDate(final Date date, View view) {
                                                                                         SELECTED_DATE = date;
                                                                                         updateFragment(FRAGMENT_TYPE);
                                                                                         calendar.dismiss();
                                                                                    }

                                                                                    @Override public void onChangeMonth(int month, int year) {
                                                                                    }

                                                                                    @Override public void onLongClickDate(Date date, View view) {
                                                                                    }

                                                                                    @Override public void onCaldroidViewCreated() {
                                                                                    }
                                                                               };

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
          twDaily.setPadding(6, 6, 6, 6);
          twMonthly.setPadding(6, 6, 6, 6);
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
               case DAILY:
                    twDaily.setBackgroundColor(color.red);
                    twMonthly.setBackgroundResource(drawable.background_transparent_inside);
                    twYearly.setBackgroundResource(drawable.background_transparent_inside);
                    break;

               case MONTHLY:
                    twMonthly.setBackgroundColor(color.red);
                    twDaily.setBackgroundResource(drawable.background_transparent_inside);
                    twYearly.setBackgroundResource(drawable.background_transparent_inside);
                    break;

               case YEARLY:
                    twYearly.setBackgroundColor(color.red);
                    twMonthly.setBackgroundResource(drawable.background_transparent_inside);
                    twDaily.setBackgroundResource(drawable.background_transparent_inside);
                    break;
          }
     }
}
