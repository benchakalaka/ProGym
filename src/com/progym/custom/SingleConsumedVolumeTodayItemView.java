package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.single_consumed_volume_list_item ) public class SingleConsumedVolumeTodayItemView extends RelativeLayout {

     @ViewById public ImageView ivVolumeImage;
     @ViewById public TextView  twWaterVolume;
     @ViewById public TextView  twTime;

     public SingleConsumedVolumeTodayItemView ( Context context ) {
          super(context);
     }

}
