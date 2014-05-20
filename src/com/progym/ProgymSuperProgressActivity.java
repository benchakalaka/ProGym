package com.progym;

import java.util.Date;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.progym.custom.NonSwipeableViewPager;
import com.roomorama.caldroid.CaldroidFragment;

@EBean
public abstract class ProgymSuperProgressActivity extends FragmentActivity implements IProgressActivity {
	static Date									SELECTED_DATE			= new Date();
	CaldroidFragment								calendar;

	@ViewById public TextView						twDaily;
	@ViewById public TextView						twMonthly;
	@ViewById public TextView						twYearly;
	@ViewById public TextView						twRange;
	@ViewById public NonSwipeableViewPager				viewPager;
	public int									FRAGMENT_TYPE;
	public static final int							DAILY				= 0;
	public static final int							MONTHLY				= 1;
	public static final int							YEARLY				= 2;
	public static final int							RANGE				= 3;

	// Setup listener
	public final com.roomorama.caldroid.CaldroidListener	onDateChangeListener	= new com.roomorama.caldroid.CaldroidListener() {
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

}
