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
import android.view.View;
import android.widget.TextView;

import com.progym.R;
import com.progym.R.color;
import com.progym.R.drawable;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.NonSwipeableViewPager;
import com.progym.custom.fragments.CalloriesProgressMonthlyLineFragment;
import com.progym.custom.fragments.CalloriesProgressMonthlyLineFragment_;
import com.progym.custom.fragments.FoodCalloriesProgressYearlyLineFragment;
import com.progym.custom.fragments.FoodCalloriesProgressYearlyLineFragment_;
import com.progym.interfaces.IProgressActivity;

@EActivity ( R.layout.callories_progress_activity )
public class ActivityCalloriesProgress extends FragmentActivity implements IProgressActivity {

	public final static String	months[]		= new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	public final static String	months_short[]	= new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	  static Date                                          SELECTED_DATE        = new Date();
	     CaldroidFragmentCustom                               calendar;
	 
	     @ViewById public TextView                            twDaily;
	     @ViewById public TextView                            twMonthly;
	     @ViewById public TextView                            twYearly;
	     @ViewById public NonSwipeableViewPager               viewPager;
	     public int                                           FRAGMENT_TYPE;
	     public static final int                              MONTHLY              = 0;
	     public static final int                              YEARLY               = 1;
	     public static final int                              RANGE                = 33;

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
		calendar = new CaldroidFragmentCustom();
		calendar.setCaldroidListener(onDateChangeListener);
		twMonthly.setBackgroundColor(color.red);
		twDaily.setVisibility(View.GONE);
		
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
          twDaily.setPadding(6, 6, 6, 6);
          twMonthly.setPadding(6, 6, 6, 6);
     }

	@Override public void updateFragment(int fragmentType) {
		setProperPadding(fragmentType);
		switch (fragmentType) {
			case MONTHLY:
				viewPager.setCurrentItem(MONTHLY, true);
				((ProgressCalloriesViewPagerAdapter) viewPager.getAdapter()).linegraphMonthly.setLineData3(new Date());
				break;
			case YEARLY:
				viewPager.setCurrentItem(YEARLY, true);
				((ProgressCalloriesViewPagerAdapter) viewPager.getAdapter()).linegraphYearly.setYearProgressData(SELECTED_DATE);
				break;
		}
	}

	/**
	 * A simple pager adapter
	 */
	private class ProgressCalloriesViewPagerAdapter extends FragmentStatePagerAdapter {

		private static final int						NUM_OF_FRAGMENTS	= 2;
		public CalloriesProgressMonthlyLineFragment		linegraphMonthly	= new CalloriesProgressMonthlyLineFragment_();
		public FoodCalloriesProgressYearlyLineFragment	linegraphYearly	= new FoodCalloriesProgressYearlyLineFragment_();

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
