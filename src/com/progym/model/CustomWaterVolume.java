package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class CustomWaterVolume extends SugarRecord <CustomWaterVolume> {

	public User	user;
	public int	customVolume;

	public CustomWaterVolume ( Context ctx ) {
		super(ctx);
	}

}
