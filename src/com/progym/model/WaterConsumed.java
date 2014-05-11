package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class WaterConsumed extends SugarRecord <WaterConsumed> {

	public int	volumeConsumed;
	public String	date;
	public User	user;

	public WaterConsumed ( Context ctx ) {
		super(ctx);
	}

}
