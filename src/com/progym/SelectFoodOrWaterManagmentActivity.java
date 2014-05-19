package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.LinearLayout;

@EActivity ( R.layout.select_food_or_water_managment_activity )
public class SelectFoodOrWaterManagmentActivity extends Activity {

	@ViewById LinearLayout					llFoodTracking;
	@ViewById LinearLayout					llWaterTracking;

	@AnimationRes ( R.anim.fadein ) Animation	fadeIn;

	@AfterViews void afterViews() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int marginLeft = size.x / 4;

		MarginLayoutParams params = (MarginLayoutParams) llFoodTracking.getLayoutParams();
		params.leftMargin = marginLeft;
		llFoodTracking.setLayoutParams(params);

		params = (MarginLayoutParams) llWaterTracking.getLayoutParams();
		params.leftMargin = marginLeft;
		llWaterTracking.setLayoutParams(params);
	}

	@Click void llFoodTracking() {
		llFoodTracking.startAnimation(fadeIn);
		startActivity(new Intent(SelectFoodOrWaterManagmentActivity.this, FoodManagmentActivity_.class));
	}

	@Click void llWaterTracking() {
		llWaterTracking.startAnimation(fadeIn);
		startActivity(new Intent(SelectFoodOrWaterManagmentActivity.this, WaterManagementActivity_.class));
	}
}
