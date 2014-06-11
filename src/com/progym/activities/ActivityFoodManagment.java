package com.progym.activities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.progym.R;
import com.progym.constants.GlobalConstants;
import com.progym.custom.CaldroidFragmentCustom;
import com.progym.custom.NonSwipeableViewPager;
import com.progym.custom.OneProductOnPlateView;
import com.progym.custom.OneProductOnPlateView_;
import com.progym.custom.SinglePlateItemView;
import com.progym.custom.SinglePlateItemView_;
import com.progym.custom.fragments.FoodTypeFragment;
import com.progym.custom.fragments.FoodTypeFragment_;
import com.progym.custom.fragments.FragmentIngridient;
import com.progym.custom.fragments.FragmentIngridient_;
import com.progym.model.Ingridient;
import com.progym.model.Meal;
import com.progym.model.ReadyIngridient;
import com.progym.model.ReadyMeal;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.food_managment_activity )
public class ActivityFoodManagment extends ProgymSuperActivity {

	public static final int				EXPANDABLE_LISTVIEW_FOOD_TYPES	= 0;
	public static final int				SPECIFIC_FOOD_SPECIFICATION		= 1;

	private Meal						CURRENT_MEAL;
	private View						CURRENT_MEAL_VIEW;

	@ViewById public NonSwipeableViewPager	viewPager;
	@ViewById public ImageView			ivOnPlate;
	@ViewById public ScrollView			svListOfConsumedMeals;
	@ViewById public LinearLayout			llAlreadyOnPlate;
	@ViewById public LinearLayout			llCreatedPlates;

	@ViewById public ImageButton			ibCreatePlate;
	@ViewById public ImageButton			ibSavePlate;

	@ViewById public HorizontalScrollView	horizontalScrollView;

	private ArrayList <View>				PLATES_BUTTONS;

	@Override @AfterViews void afterViews() {
		calendar = new CaldroidFragmentCustom();
		calendar.setCaldroidListener(onDateChangeListener);
		PLATES_BUTTONS = new ArrayList <View>();
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		displaySelectedDate();
		svListOfConsumedMeals.setOnDragListener(new OnDragListener() {

			@Override public boolean onDrag(View v, DragEvent event) {

				switch (event.getAction()) {
					case DragEvent.ACTION_DROP:
						// 0 - element name of product
						// 1 - element weight of prodcut
						// 2 - element groupName of prodcut
						// 3 - element protein of prodcut
						// 4 - element carbs of prodcut
						// 5 - element fat of prodcut
						// 6 - element kkal of prodcut
						final String name = event.getClipData().getItemAt(0).getText().toString();
						final String weight = event.getClipData().getItemAt(1).getText().toString();
						final String groupName = event.getClipData().getItemAt(2).getText().toString();
						final String protein = event.getClipData().getItemAt(3).getText().toString();
						final String carbs = event.getClipData().getItemAt(4).getText().toString();
						final String fat = event.getClipData().getItemAt(5).getText().toString();
						final String kkal = event.getClipData().getItemAt(6).getText().toString();

						if ( null == CURRENT_MEAL ) {
							Utils.showCustomToast(ActivityFoodManagment.this, "Create plate", R.drawable.meal_title);
							ibCreatePlate.startAnimation(fadeIn);
							return false;
						}

						Ingridient ingridient = new Ingridient(getApplicationContext());
						ingridient.protein = Double.valueOf(protein);
						ingridient.carbohydrates = Double.valueOf(carbs);
						ingridient.fat = Double.valueOf(fat);
						ingridient.kkal = Integer.valueOf(kkal);
						ingridient.name = name;
						ingridient.groupName = groupName;

						ingridient.weight = Double.valueOf(weight);
						ingridient.meal = CURRENT_MEAL;
						ingridient.date = CURRENT_MEAL.date;
						ingridient.user = DataBaseUtils.getCurrentUser();
						ingridient.save();

						OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
						view.ivProduct.setImageResource(Utils.getImageIdByGroupPositionInExpListView(FoodTypeFragment.CURRENT_GROUPNAME_CATALOGUE));
						view.twProductDescription.setText(String.format("%s : %sg (%skkal)", ingridient.name, ingridient.weight, ingridient.kkal));
						llAlreadyOnPlate.addView(view);

						int currentAmountOfIngridients = Integer.valueOf(((SinglePlateItemView) CURRENT_MEAL_VIEW).twIngridientsAmount.getText().toString());
						((SinglePlateItemView) CURRENT_MEAL_VIEW).twIngridientsAmount.setText(String.valueOf(currentAmountOfIngridients + 1));
						svListOfConsumedMeals.post(new Runnable() {
							@Override public void run() {
								// This method works even better because there are no animations.
								svListOfConsumedMeals.smoothScrollTo(0, svListOfConsumedMeals.getBottom());
							}
						});
				}
				return true;
			}
		});
	}

	@Override public void onBackPressed() {
		// 1 - ingridient specification, go back to expandable list view
		if ( viewPager.getCurrentItem() == ActivityFoodManagment.SPECIFIC_FOOD_SPECIFICATION ) {
			viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);
		} else {
			super.onBackPressed();
		}
	}

	@Click void ibSavePlate() {
		if ( null != CURRENT_MEAL ) {

			List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(CURRENT_MEAL);

			if ( ingridients.isEmpty() ) {
				Utils.showCustomToast(ActivityFoodManagment.this, "Have a look, there is nothing on this plate. Add some food please!", R.drawable.unhappy);
				return;
			}
			

			ReadyMeal readyMeal = new ReadyMeal(getApplicationContext());
			readyMeal.user = DataBaseUtils.getCurrentUser();
			readyMeal.date = Utils.formatDate(new Date(),DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS_MILLIS);
			readyMeal.save();

			// GET ALL READY_MEAL_CATALOGUE INGRIDIENTS
			List <ReadyMeal> meals = ReadyMeal.listAll(ReadyMeal.class);
			if ( !GlobalConstants.INGRIDIENTS.READY_MEALS.isEmpty() ) {
				GlobalConstants.INGRIDIENTS.READY_MEALS.clear();
			}
			for ( ReadyMeal meal : meals ) {
				GlobalConstants.INGRIDIENTS.READY_MEALS.add(meal.date);
			}

			for ( Ingridient i : ingridients ) {
				ReadyIngridient readiIngridien = new ReadyIngridient(ActivityFoodManagment.this);
				readiIngridien.carbohydrates = i.carbohydrates;
				readiIngridien.fat = i.fat;
				readiIngridien.protein = i.protein;
				readiIngridien.groupName = i.groupName;
				readiIngridien.name = i.name;
				readiIngridien.kkal = i.kkal;
				readiIngridien.user = DataBaseUtils.getCurrentUser();
				readiIngridien.meal = CURRENT_MEAL;
				readiIngridien.weight = i.weight;
				readiIngridien.date = readyMeal.date;
				readiIngridien.save();
			}

			// Add to expandable list view ready meal date
			((ViewPagerAdapter) viewPager.getAdapter()).foodCategoryExpListViewFragment.addToReadyMeal(CURRENT_MEAL.date);
			Utils.showCustomToast(ActivityFoodManagment.this, "List of products has been saved. You can find it under the -Ready meals- catalogue", R.drawable.save);
		} else {
			Utils.showCustomToast(this, "Create plate before saving it", R.drawable.meal_title);
			ibCreatePlate.startAnimation(fade);
		}
	}

	@Click void llLeftPanelDateWithCalendar() {
		llLeftPanelDateWithCalendar.startAnimation(fade);
		showProgressBar(ActivityFoodManagment.this);

		Thread t = new Thread(new Runnable() {

			@Override public void run() {
				List <Meal> meals = DataBaseUtils.getAllPlates();
				if ( null != meals && !meals.isEmpty() ) {
					HashMap <Date, Integer> datesToHighligt = new HashMap <Date, Integer>();
					for ( Meal meal : meals ) {
						try {
							datesToHighligt.put(DateUtils.parseDate(meal.date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), com.caldroid.R.color.caldroid_sky_blue);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					// highlight dates in calendar with blue color
					calendar.setBackgroundResourceForDates(datesToHighligt);
				}
				calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);
				hideProgressBar(ActivityFoodManagment.this);
			}
		});
		t.start();

	}

	@Override public void displaySelectedDate() {
		// Apply pattern for displaying into left panel without time
		twCurrentDate.setText(Utils.formatDate(SELECTED_DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD));
		loadPlatesByDate(twCurrentDate.getText().toString());
		putProductsOnPlate();
		setLastPlateActive();
	}

	private void setLastPlateActive() {
		if ( !PLATES_BUTTONS.isEmpty() ) {
			for ( View plateView : PLATES_BUTTONS ) {
				plateView.setBackgroundColor(Color.TRANSPARENT);// setPadding(0, 0, 0, 0);
			}
			// Set CURRENT_PLATE_VIEW - > display amount of products on the meal
			CURRENT_MEAL_VIEW = PLATES_BUTTONS.get(PLATES_BUTTONS.size() - 1);
			PLATES_BUTTONS.get(PLATES_BUTTONS.size() - 1).setBackgroundResource(R.drawable.background_round_transparent_real);// .setPadding(15, 15, 15, 15);
		}
	}

	private void putProductsOnPlate() {
		llAlreadyOnPlate.removeAllViews();
		if ( null != CURRENT_MEAL ) {
			List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(CURRENT_MEAL);
			for ( Ingridient ingridient : ingridients ) {
				OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
				view.ivProduct.setImageResource(Utils.getImageIdByGroupName(ingridient.groupName));
				view.twProductDescription.setText(String.format("%s : %sg (%skkal)", ingridient.name, ingridient.weight, ingridient.kkal));
				llAlreadyOnPlate.addView(view);
			}
		}
	}

	private void loadPlatesByDate(String date) {
		List <Meal> meals = DataBaseUtils.getPlatesConsumedByDate(date);
		llCreatedPlates.removeAllViews();
		if ( null != meals && !meals.isEmpty() ) {
			// set active meal last meal
			CURRENT_MEAL = meals.get(meals.size() - 1);
			for ( Meal meal : meals ) {
				createProductOnPlate(meal);
			}
		} else {
			CURRENT_MEAL = null;
		}
	}

	@Click void ibCreatePlate() {

		llAlreadyOnPlate.removeAllViews();
		User u = DataBaseUtils.getCurrentUser();
		Meal meal = new Meal(getApplicationContext());

		// //////////////////////// Format proper date (Date from twCurrentDate + CURRENT_TIME) /////////////////////////////
		String properDate = "";
		properDate = Utils.formatDate(SELECTED_DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
		properDate += " " + Utils.formatDate(new Date(), DataBaseUtils.DATE_PATTERN_HH_MM_SS);
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		meal.date = properDate;
		meal.user = u;

		createProductOnPlate(meal);

		// save meal
		meal.save();
		this.CURRENT_MEAL = meal;

		setLastPlateActive();
		Utils.showCustomToast(this, "Plate has been created", R.drawable.plus);
	}

	public void ibReadyMealPlate(List <ReadyIngridient> ingridientsOfReadyMeal) {

		llAlreadyOnPlate.removeAllViews();
		User u = DataBaseUtils.getCurrentUser();
		Meal meal = new Meal(getApplicationContext());

		// //////////////////////// Format proper date (Date from twCurrentDate + CURRENT_TIME) /////////////////////////////
		String properDate = "";
		properDate = Utils.formatDate(SELECTED_DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
		properDate += " " + Utils.formatDate(new Date(), DataBaseUtils.DATE_PATTERN_HH_MM_SS);
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		for ( ReadyIngridient i : ingridientsOfReadyMeal ) {
			Ingridient ingridientToLog = new Ingridient(getApplicationContext());
			ingridientToLog.carbohydrates = i.carbohydrates;
			ingridientToLog.fat = i.fat;
			ingridientToLog.protein = i.protein;
			ingridientToLog.groupName = i.groupName;
			ingridientToLog.name = i.name;
			ingridientToLog.kkal = i.kkal;
			ingridientToLog.user = u;
			ingridientToLog.meal = meal;
			ingridientToLog.weight = i.weight;
			ingridientToLog.date = properDate;
			ingridientToLog.save();
		}

		meal.date = properDate;
		meal.user = u;
		// save meal
		meal.save();
		this.CURRENT_MEAL = meal;

		createProductOnPlate(meal);

		setLastPlateActive();
		Utils.showCustomToast(this, "Plate has been created", R.drawable.meal_title);
	}

	public void addReadyMealToCurrentPlate(List <ReadyIngridient> ingridientsOfReadyMeal) {
		User u = DataBaseUtils.getCurrentUser();
		if ( null == CURRENT_MEAL ) {
			Utils.showCustomToast(this, "Create plate before", R.drawable.meal_title);
			ibCreatePlate.startAnimation(fade);
			return;
		}
		Meal meal = CURRENT_MEAL;

		for ( ReadyIngridient i : ingridientsOfReadyMeal ) {
			Ingridient ingridientToLog = new Ingridient(getApplicationContext());
			ingridientToLog.carbohydrates = i.carbohydrates;
			ingridientToLog.fat = i.fat;
			ingridientToLog.protein = i.protein;
			ingridientToLog.groupName = i.groupName;
			ingridientToLog.name = i.name;
			ingridientToLog.kkal = i.kkal;
			ingridientToLog.user = u;
			ingridientToLog.meal = meal;
			ingridientToLog.weight = i.weight;
			ingridientToLog.date = meal.date;
			ingridientToLog.save();
			OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
			view.ivProduct.setImageResource(Utils.getImageIdByGroupName(ingridientToLog.groupName));
			view.twProductDescription.setText(String.format("%s : %sg (%skkal)", ingridientToLog.name, ingridientToLog.weight, ingridientToLog.kkal));
			llAlreadyOnPlate.addView(view);

			int currentAmountOfIngridients = Integer.valueOf(((SinglePlateItemView) CURRENT_MEAL_VIEW).twIngridientsAmount.getText().toString());
			((SinglePlateItemView) CURRENT_MEAL_VIEW).twIngridientsAmount.setText(String.valueOf(currentAmountOfIngridients + 1));
			svListOfConsumedMeals.post(new Runnable() {
				@Override public void run() {
					// This method works even better because there are no animations.
					svListOfConsumedMeals.smoothScrollTo(0, svListOfConsumedMeals.getBottom());
				}
			});
		}

		// meal.date = properDate;
		// meal.user = u;

		// createProductOnPlate(meal);

		// save meal
		// meal.save();
		this.CURRENT_MEAL = meal;

		setLastPlateActive();
		Utils.showCustomToast(this, "Plate has been created", R.drawable.meal_title);

	}

	private void createProductOnPlate(Meal meal) {
		SinglePlateItemView itemView = SinglePlateItemView_.build(getApplicationContext());
		itemView.ivVolumeImage.setBackgroundResource(R.drawable.meal_title);
		itemView.twIngridientsAmount.setText(String.valueOf(DataBaseUtils.getProductsOnPlate(meal).size()));

		// Set Meal as property to this VEIW :TODO
		itemView.setTag(meal);

		PLATES_BUTTONS.add(itemView);
		CURRENT_MEAL_VIEW = itemView;

		itemView.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				CURRENT_MEAL = (Meal) v.getTag();
				CURRENT_MEAL_VIEW = v;

				CURRENT_MEAL_VIEW.startAnimation(fade);
				Utils.showCustomToast(ActivityFoodManagment.this, String.format("Created %s", CURRENT_MEAL.date), R.drawable.meal_title);

				/*
				 * Utils.log("CURRENT PLATE DATE :: " + CURRENT_MEAL.date);
				 * List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(CURRENT_MEAL);
				 * if ( null != ingridients ) {
				 * for ( Ingridient ingridient : ingridients ) {
				 * Utils.log(String.format("==========prot:%s == carbs:%s == name:%s == fat %s============", ingridient.protein, ingridient.carbohydrates,
				 * ingridient.name, ingridient.fat));
				 * }
				 * }
				 */

				for ( View plateView : PLATES_BUTTONS ) {
					// Unselect all
					plateView.setBackgroundColor(Color.TRANSPARENT);
				}
				v.setBackgroundResource(R.drawable.background_round_transparent_real);

				putProductsOnPlate();
			}
		});
		llCreatedPlates.addView(itemView);
	}

	/**
	 * A simple pager adapter
	 */
	public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
		int						NUM_OF_FRAGMENTS				= 2;

		public FoodTypeFragment		foodCategoryExpListViewFragment	= new FoodTypeFragment_();
		public FragmentIngridient	fragmentIngridient				= new FragmentIngridient_();

		public ViewPagerAdapter ( FragmentManager fm ) {
			super(fm);
		}

		@Override public Fragment getItem(int position) {
			Fragment returnFragment = null;
			switch (position) {
				case EXPANDABLE_LISTVIEW_FOOD_TYPES:
					returnFragment = foodCategoryExpListViewFragment;
					break;

				case SPECIFIC_FOOD_SPECIFICATION:
					returnFragment = fragmentIngridient;
					break;
			}
			return returnFragment;
		}

		@Override public int getCount() {
			return NUM_OF_FRAGMENTS;
		}
	}
}
