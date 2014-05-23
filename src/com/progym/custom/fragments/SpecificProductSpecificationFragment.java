package com.progym.custom.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.Button;
import android.widget.ImageView;

import com.progym.R;
import com.progym.activities.ActivityFoodManagment;

@EFragment ( R.layout.fragment_specific_product_spec ) public class SpecificProductSpecificationFragment extends Fragment {

     @ViewById Button    btnShowFoodTypes;
     @ViewById ImageView ivFoodImage;

     @Click void btnShowFoodTypes() {
          ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);
     }

     @Touch void ivFoodImage(MotionEvent event, View v) {
          v.setTag("tag");
          dragView(v);
     }

     private void dragView(View v) {
          String tag = v.getTag().toString();
          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);
     }

}
