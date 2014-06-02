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
import com.progym.custom.fragments.FoodTypeExpListViewFragment;
import com.progym.custom.fragments.FoodTypeExpListViewFragment_;
import com.progym.custom.fragments.FragmentIngridient;
import com.progym.custom.fragments.FragmentIngridient_;
import com.progym.model.Ingridient;
import com.progym.model.Meal;
import com.progym.model.ReadyMeal;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EActivity ( R.layout.food_managment_activity ) public class ActivityFoodManagment extends ProgymSuperActivity {

     public static final int                EXPANDABLE_LISTVIEW_FOOD_TYPES = 0;
     public static final int                SPECIFIC_FOOD_SPECIFICATION    = 1;

     private Meal                           CURRENT_MEAL;
     private View                           CURRENT_MEAL_VIEW;

     @ViewById public NonSwipeableViewPager viewPager;
     @ViewById public ImageView             ivOnPlate;
     @ViewById public ScrollView            svListOfConsumedMeals;
     @ViewById public LinearLayout          llAlreadyOnPlate;
     @ViewById public LinearLayout          llCreatedPlates;

     @ViewById public ImageButton           ibCreatePlate;
     @ViewById public ImageButton           ibSavePlate;

     @ViewById public HorizontalScrollView  horizontalScrollView;

     private ArrayList <View>               PLATES_BUTTONS;

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
                                   Utils.showCustomToast(ActivityFoodManagment.this, "Plate has been created", R.drawable.plate);
                                   ibCreatePlate.startAnimation(fadeIn);
                                   return false;
                              }

                              Ingridient ingridient = new Ingridient(getApplicationContext());
                              ingridient.protein = Integer.valueOf(protein);
                              ingridient.carbohydrates = Integer.valueOf(carbs);
                              ingridient.fat = Integer.valueOf(fat);
                              ingridient.kkal = Integer.valueOf(kkal);
                              ingridient.name = name;
                              ingridient.groupName = groupName;

                              ingridient.weight = Integer.valueOf(weight);
                              ingridient.meal = CURRENT_MEAL;
                              ingridient.date = CURRENT_MEAL.date;
                              ingridient.user = DataBaseUtils.getCurrentUser();
                              ingridient.save();

                              OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
                              view.ivProduct.setImageResource(Utils.getImageIdByGroupPositionInExpListView(FoodTypeExpListViewFragment.CURRENT_GROUPNAME_CATALOGUE));
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

     @Click void ibSavePlate() {
          if ( null != CURRENT_MEAL ) {
               ReadyMeal readyMeal = new ReadyMeal(getApplicationContext());
               readyMeal.user = DataBaseUtils.getCurrentUser();
               readyMeal.date = CURRENT_MEAL.date;
               readyMeal.save();

               // Add to expandable list view ready meal date
               ((ViewPagerAdapter) viewPager.getAdapter()).foodCategoryExpListViewFragment.addToReadyMeal(CURRENT_MEAL.date);
               Utils.showCustomToast(ActivityFoodManagment.this, "Plate has been saved", R.drawable.plate);
          } else {
               Utils.showCustomToast(this, "Create plate before saving it", R.drawable.plate);
               ibCreatePlate.startAnimation(leftIn);
          }
     }

     @Click void llLeftPanelDateWithCalendar() {
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
          Utils.showCustomToast(this, "Plate has been created", R.drawable.plate);
     }

     private void createProductOnPlate(Meal meal) {
          SinglePlateItemView itemView = SinglePlateItemView_.build(getApplicationContext());
          itemView.ivVolumeImage.setBackgroundResource(R.drawable.meal);
          itemView.twIngridientsAmount.setText(String.valueOf(DataBaseUtils.getProductsOnPlate(meal).size()));

          // Set Meal as property to this VEIW :TODO
          itemView.setTag(meal);

          PLATES_BUTTONS.add(itemView);
          CURRENT_MEAL_VIEW = itemView;

          itemView.setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    CURRENT_MEAL = (Meal) v.getTag();
                    CURRENT_MEAL_VIEW = v;
                    Utils.log("CURRENT PLATE DATE :: " + CURRENT_MEAL.date);
                    List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(CURRENT_MEAL);
                    if ( null != ingridients ) {
                         for ( Ingridient ingridient : ingridients ) {
                              Utils.log(String.format("==========prot:%s == carbs:%s == name:%s == fat %s============", ingridient.protein, ingridient.carbohydrates, ingridient.name, ingridient.fat));
                         }
                    }

                    for ( View plateView : PLATES_BUTTONS ) {
                         // Unselect all
                         plateView.setBackgroundColor(Color.TRANSPARENT);// Color(Color.BLACK);
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

          public FoodTypeExpListViewFragment foodCategoryExpListViewFragment = new FoodTypeExpListViewFragment_();
          public FragmentIngridient          fragmentIngridient              = new FragmentIngridient_();

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
               return 2;
          }
     }
}
