package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.one_product_on_plate )
public class OneProductOnPlateView extends RelativeLayout {

	@ViewById public TextView	twProductDescription;
	@ViewById public ImageView	ivProduct;

	public OneProductOnPlateView ( Context context ) {
		super(context);
	}

}
