package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.progym.custom.NonSwipeableViewPager;
import com.progym.custom.fragments.FoodTypeExpListViewFragment_;
import com.progym.custom.fragments.SpecificProductSpecificationFragment_;

@EActivity ( R.layout.food_managment_activity ) public class FoodManagmentActivity extends FragmentActivity {

     public static final int                EXPANDABLE_LISTVIEW_FOOD_TYPES = 0;
     public static final int                SPECIFIC_FOOD_SPECIFICATION    = 1;

     @ViewById public NonSwipeableViewPager viewPager;
     @ViewById public ImageView             ivOnPlate;
     @ViewById public TextView              twListOfMeals;

     @AfterViews void afterViews() {
          viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
          ivOnPlate.setOnDragListener(new OnDragListener() {

               @Override public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                         case DragEvent.ACTION_DROP:
                              final String tag = event.getClipData().getDescription().getLabel().toString();
                              twListOfMeals.setText(twListOfMeals.getText().toString() + tag);
                    }
                    return true;
               }
          });
     }

     /**
      * A simple pager adapter
      */
     private class ViewPagerAdapter extends FragmentStatePagerAdapter {

          public ViewPagerAdapter ( FragmentManager fm ) {
               super(fm);
          }

          @Override public Fragment getItem(int position) {
               Fragment returnFragment = null;
               switch (position) {
                    case EXPANDABLE_LISTVIEW_FOOD_TYPES:
                         returnFragment = new FoodTypeExpListViewFragment_();
                         break;

                    case SPECIFIC_FOOD_SPECIFICATION:
                         returnFragment = new SpecificProductSpecificationFragment_();
                         break;
               }
               return returnFragment;
          }

          @Override public int getCount() {
               return 2;
          }
     }
}
