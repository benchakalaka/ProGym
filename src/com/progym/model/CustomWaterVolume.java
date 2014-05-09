package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class CustomWaterVolume extends SugarRecord <CustomWaterVolume> {

	public double	customVolume;

	public CustomWaterVolume ( Context ctx ) {
		super(ctx);
	}

}
