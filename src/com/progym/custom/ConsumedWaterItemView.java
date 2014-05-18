package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.custom_hsv_consumed_water_item ) public class ConsumedWaterItemView extends RelativeLayout {

     @ViewById public ImageView ivVolumeImage;

     public ConsumedWaterItemView ( Context context ) {
          super(context);
     }

}
