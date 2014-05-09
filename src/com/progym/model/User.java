package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * User entity represents one single user
 * 
 * @author Karpachev Ihor
 */
public class User extends SugarRecord <User> {

	// ------------------------- body fat level, future feature
	public String	name;
	/**
	 * Dropdown for gender
	 */
	public String	gender;
	public double	weight;
	public int	height;
	public int	age;
	/**
	 * Body type represent metabolism speed
	 * 0 - ektomorf
	 * 1 - mezomorf
	 * 2 - endomorf
	 * Dropdown with body type and explanation who is who
	 */
	public int	bodyType;

	public User ( Context arg0 ) {
		super(arg0);
	}

}
