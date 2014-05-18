package com.progym;

import java.util.Date;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;

@EBean
public abstract class ProgymSuperActivity extends FragmentActivity implements SuperInterface {
	static Date									SELECTED_DATE			= new Date();
	CaldroidFragment								calendar;
	@ViewById ImageView								ivNextDay;
	@ViewById ImageView								ivPrevDay;
	@ViewById TextView								twCurrentDate;

	@AnimationRes ( R.anim.push_left_in ) Animation		leftIn;
	@AnimationRes ( R.anim.push_left_out ) Animation		leftOut;

	@AnimationRes ( R.anim.push_right_in ) Animation		rightIn;
	@AnimationRes ( R.anim.push_right_out ) Animation		rightOut;

	@AnimationRes ( R.anim.fadein ) Animation			fadeIn;

	@ViewById LinearLayout							llLeftPanelDateWithCalendar;

	// Setup listener
	public final com.roomorama.caldroid.CaldroidListener	onDateChangeListener	= new com.roomorama.caldroid.CaldroidListener() {
																		@Override public void onSelectDate(final Date date, View view) {
																			SELECTED_DATE = date;
																			displaySelectedDate();
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
	 * Calendar control button
	 * display next day
	 */
	@Click void ivNextDay() {
		ivNextDay.startAnimation(fadeIn);
		leftOut.setAnimationListener(new AnimationListener() {

			@Override public void onAnimationStart(Animation animation) {
			}

			@Override public void onAnimationRepeat(Animation animation) {
			}

			@Override public void onAnimationEnd(Animation animation) {
				// Add one day (add one day to currrnet date)
				SELECTED_DATE = DateUtils.addDays(SELECTED_DATE, 1);
				displaySelectedDate();
				llLeftPanelDateWithCalendar.startAnimation(leftIn);
			}
		});
		llLeftPanelDateWithCalendar.startAnimation(leftOut);
	}

	/**
	 * Calendar control button
	 * display previous day
	 */
	@Click void ivPrevDay() {
		ivPrevDay.startAnimation(fadeIn);
		rightOut.setAnimationListener(new AnimationListener() {

			@Override public void onAnimationStart(Animation animation) {
			}

			@Override public void onAnimationRepeat(Animation animation) {
			}

			@Override public void onAnimationEnd(Animation animation) {
				// Sub one day from selected date
				SELECTED_DATE = DateUtils.addDays(SELECTED_DATE, -1);
				displaySelectedDate();
				llLeftPanelDateWithCalendar.startAnimation(rightIn);
			}
		});
		llLeftPanelDateWithCalendar.startAnimation(rightOut);
	}
}
