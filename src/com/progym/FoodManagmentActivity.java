package com.progym;

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
import android.widget.Toast;

import com.progym.constants.GlobalConstants;
import com.progym.custom.NonSwipeableViewPager;
import com.progym.custom.OneProductOnPlateView;
import com.progym.custom.OneProductOnPlateView_;
import com.progym.custom.SinglePlateItemView;
import com.progym.custom.SinglePlateItemView_;
import com.progym.custom.fragments.FoodTypeExpListViewFragment;
import com.progym.custom.fragments.FoodTypeExpListViewFragment_;
import com.progym.custom.fragments.SpecificProductSpecificationFragment;
import com.progym.custom.fragments.SpecificProductSpecificationFragment_;
import com.progym.model.Ingridient;
import com.progym.model.Meal;
import com.progym.model.ReadyMeal;
import com.progym.model.User;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.food_managment_activity ) public class FoodManagmentActivity extends ProgymSuperActivity {

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
          PLATES_BUTTONS = new ArrayList <View>();
          viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
          displaySelectedDate();
          svListOfConsumedMeals.setOnDragListener(new OnDragListener() {

               @Override public boolean onDrag(View v, DragEvent event) {

                    switch (event.getAction()) {
                         case DragEvent.ACTION_DROP:
                              final String tag = event.getClipData().getDescription().getLabel().toString();

                              if ( null == CURRENT_MEAL ) {
                                   Toast.makeText(getApplicationContext(), "Create Plate please", Toast.LENGTH_SHORT).show();
                                   ibCreatePlate.startAnimation(fadeIn);
                                   return false;
                              }

                              Ingridient ingridient = new Ingridient(getApplicationContext());
                              ingridient.meal = CURRENT_MEAL;
                              ingridient.date = CURRENT_MEAL.date;
                              ingridient.carbohydrates = 30;
                              ingridient.fat = 30;
                              ingridient.protein = 30;
                              ingridient.typeOfProduct = "Meat";
                              ingridient.productName = "Chicken";
                              ingridient.user = DataBaseUtils.getCurrentUser();
                              ingridient.save();

                              OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
                              view.ivProduct.setImageResource(R.drawable.ingridient);
                              view.twProductDescription.setText(ingridient.productName + " : " + CURRENT_MEAL.date);
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

               List <ReadyMeal> readyMeals = ReadyMeal.listAll(ReadyMeal.class);
               List <Ingridient> listOfProduct = DataBaseUtils.getProductsOnPlate(readyMeals.get(0).date);

               for ( Ingridient p : listOfProduct ) {
                    Utils.log(p.productName);
               }
          }
     }

     @Click void llLeftPanelDateWithCalendar() {
          calendar = new CaldroidFragment();
          calendar.setCaldroidListener(onDateChangeListener);

          List <Meal> meals = DataBaseUtils.getAllPlates();
          if ( null != meals && !meals.isEmpty() ) {
               HashMap <Date, Integer> datesToHighligt = new HashMap <Date, Integer>();
               for ( Meal meal : meals ) {
                    try {
                         datesToHighligt.put(DateUtils.parseDate(meal.date, GlobalConstants.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), com.caldroid.R.color.caldroid_sky_blue);
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
          Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD);
          twCurrentDate.setText(Utils.dateFormat.format(SELECTED_DATE));
          Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
          loadPlatesByDate(twCurrentDate.getText().toString());
          putProductsOnPlate();
          setLastPlateActive();
     }

     private void setLastPlateActive() {
          if ( !PLATES_BUTTONS.isEmpty() ) {
               for ( View plateView : PLATES_BUTTONS ) {
                    plateView.setBackgroundColor(Color.TRANSPARENT);//setPadding(0, 0, 0, 0);
               }
               // Set CURRENT_PLATE_VIEW - > display amount of products on the meal
               CURRENT_MEAL_VIEW = PLATES_BUTTONS.get(PLATES_BUTTONS.size() - 1);
               PLATES_BUTTONS.get(PLATES_BUTTONS.size() - 1).setBackgroundResource(R.drawable.background_transparent_inside);//.setPadding(15, 15, 15, 15);
          }
     }

     private void putProductsOnPlate() {
          llAlreadyOnPlate.removeAllViews();
          if ( null != CURRENT_MEAL ) {
               List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(CURRENT_MEAL);
               for ( Ingridient ingridient : ingridients ) {
                    OneProductOnPlateView view = OneProductOnPlateView_.build(getApplicationContext());
                    view.ivProduct.setImageResource(R.drawable.ingridient);
                    view.twProductDescription.setText(ingridient.productName + " meal date " + CURRENT_MEAL.date);
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
          Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD);
          properDate = Utils.dateFormat.format(SELECTED_DATE);
          Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_HH_MM_SS);
          properDate += " " + Utils.dateFormat.format(new Date());
          // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

          meal.date = properDate;
          meal.user = u;

          createProductOnPlate(meal);

          // save meal
          meal.save();
          this.CURRENT_MEAL = meal;

          setLastPlateActive();

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
                              Utils.log(String.format("Ingridient -> protein:%s , carbs:%s ", ingridient.protein, ingridient.carbohydrates));
                         }
                    }

                    for ( View plateView : PLATES_BUTTONS ) {
                         //plateView.setPadding(0, 0, 0, 0);
                    	// Unselect all
                         plateView.setBackgroundColor(Color.TRANSPARENT);//Color(Color.BLACK);
                    }
                    v.setBackgroundResource(R.drawable.background_transparent_inside);
                    //v.setPadding(15, 15, 15, 15);

                    putProductsOnPlate();
               }
          });
          llCreatedPlates.addView(itemView);
     }

     /**
      * A simple pager adapter
      */
     private class ViewPagerAdapter extends FragmentStatePagerAdapter {
  
          public FoodTypeExpListViewFragment          foodCategoryExpListViewFragment = new FoodTypeExpListViewFragment_();
          public SpecificProductSpecificationFragment specificFoodFragment            = new SpecificProductSpecificationFragment_();

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
                         returnFragment = specificFoodFragment;
                         break;
               }
               return returnFragment;
          }

          @Override public int getCount() {
               return 2;
          }
     }
}
