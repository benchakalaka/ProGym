package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

public class Meal extends SugarRecord <Meal> {

	/**
	 * Date when meal has been created
	 */
	public String				date;
	public User				user;

	public Meal ( Context arg0 ) {
		super(arg0);
	}

}
