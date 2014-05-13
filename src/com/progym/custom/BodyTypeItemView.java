package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.custom_wheel_bodytype_item ) public class BodyTypeItemView extends RelativeLayout {

     @ViewById public TextView  twBodyType;
     @ViewById public ImageView ivBodyType;

     public BodyTypeItemView ( Context context ) {
          super(context);
     }

}
