package com.progym.activities;

import java.util.Date;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.ProgressDialog;
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
     ProgressDialog                                       pb;

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

     protected void showProgressBar(Activity activity) {
          if ( null != pb ) {
               pb.show();
          } else {
               initProgressBar(activity);
          }
     }

     private void initProgressBar(Activity activity) {
          pb = new ProgressDialog(activity);
          pb.setIndeterminate(true);
          pb.setTitle("Please wait...");
          pb.setMessage("Populating data");
          pb.show();
     }

     protected void hideProgressBar(Activity activity) {
          if ( null != pb ) {
               pb.dismiss();
          } else {
               initProgressBar(activity);
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
               case DAILY:
                    twDaily.setBackgroundColor(color.red);
                    twMonthly.setBackgroundResource(drawable.background_round_transparent_real);
                    twYearly.setBackgroundResource(drawable.background_round_transparent_real);
                    break;

               case MONTHLY:
                    twMonthly.setBackgroundColor(color.red);
                    twDaily.setBackgroundResource(drawable.background_round_transparent_real);
                    twYearly.setBackgroundResource(drawable.background_round_transparent_real);
                    break;

               case YEARLY:
                    twYearly.setBackgroundColor(color.red);
                    twMonthly.setBackgroundResource(drawable.background_round_transparent_real);
                    twDaily.setBackgroundResource(drawable.background_round_transparent_real);
                    break;
          }
     }
}
