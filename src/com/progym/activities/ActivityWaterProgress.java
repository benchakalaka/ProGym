package com.progym.activities;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews; 
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.apache.commons.lang3.time.DateUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
    
import com.progym.R;
import com.progym.R.color;
import com.progym.constants.GlobalConstants;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.fragments.WaterProgressDailyBarFragment;
import com.progym.custom.fragments.WaterProgressDailyBarFragment_;
import com.progym.custom.fragments.WaterProgressMonthlyLineFragment; 
import com.progym.custom.fragments.WaterProgressMonthlyLineFragment_;
import com.progym.custom.fragments.WaterProgressYearlyLineFragment;
import com.progym.custom.fragments.WaterProgressYearlyLineFragment_;
import com.progym.interfaces.IProgressActivity;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.water_progress_activity )
public class ActivityWaterProgress extends ActivityAbstractProgress implements IProgressActivity {

	public final static String	months[]		= new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
	public final static String	months_short[]	= new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	@Click void twDaily() {
		FRAGMENT_TYPE = DAILY;
		List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumed();
		HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
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
				((ProgressViewPagerAdapter) viewPager.getAdapter()).bargraphDaily.setBarData(SELECTED_DATE);
				break;
			case MONTHLY:

				viewPager.setCurrentItem(MONTHLY, true);
				((ProgressViewPagerAdapter) viewPager.getAdapter()).linegraphMonthly.setLineData2(SELECTED_DATE);
				break;
			case YEARLY:
				viewPager.setCurrentItem(YEARLY, true);
				((ProgressViewPagerAdapter) viewPager.getAdapter()).linegraphYearly.setLineData3(SELECTED_DATE);
				break;
		}
	}

	/**
	 * A simple pager adapter
	 */
	private class ProgressViewPagerAdapter extends FragmentStatePagerAdapter {

		private static final int				NUM_OF_FRAGMENTS	= 3;
		public WaterProgressDailyBarFragment	bargraphDaily		= new WaterProgressDailyBarFragment_();
		public WaterProgressMonthlyLineFragment	linegraphMonthly	= new WaterProgressMonthlyLineFragment_();
		public WaterProgressYearlyLineFragment	linegraphYearly	= new WaterProgressYearlyLineFragment_();

		public ProgressViewPagerAdapter ( FragmentManager fm ) {
			super(fm);
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
