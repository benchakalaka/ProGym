package com.progym.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;

import com.progym.R;
import com.progym.custom.roundimageview.RoundedImageView;

@EActivity ( R.layout.activity_choose_type_of_advice )
public class ActivityChooseTypeOfAdvice extends ProgymSuperActivity {

	@ViewById RoundedImageView	roundImageWhatDoYouNeed;
	@ViewById RoundedImageView	roundImageUsefulTable;

	@Override public void displaySelectedDate() {
		// not using
	}

	@Override void afterViews() {
		fade.setDuration(300);
	}

	/**
	 * Show water progress activity
	 */
	@Click void roundImageWhatDoYouNeed() {
		roundImageWhatDoYouNeed.startAnimation(fade);
		startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceLevel_1_.class));
	}

	/**
	 * Show food progress activity
	 */
	@Click void roundImageUsefulTable() {
		roundImageUsefulTable.startAnimation(fade);
		startActivity(new Intent(ActivityChooseTypeOfAdvice.this, ActivityAdviceAgeGroup_.class));
	}

}
