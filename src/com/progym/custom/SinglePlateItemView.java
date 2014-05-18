package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.custom_single_ingridient_on_plate_item ) public class SinglePlateItemView extends RelativeLayout {

     @ViewById public ImageView ivVolumeImage;
     @ViewById public TextView twIngridientsAmount;

     public SinglePlateItemView ( Context context ) {
          super(context);
     }

}
