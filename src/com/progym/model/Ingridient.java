package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class Ingridient extends SugarRecord <Ingridient> {
	/**
	 * Amount of protein in grams in this product
	 */
	public int	protein;

	/**
	 * Amount of carbohydrates in grams in this product
	 */
	public int	carbohydrates;

	/**
	 * Amount of fat in grams in this product
	 */
	public int	fat;

	/**
	 * For instance - meat or porridge ...
	 */
	public String	typeOfProduct;

	/**
	 * Exact product name, for instance, chicken or oats or potatoes
	 */
	public String	productName;

	/**
	 * User who cunsumed this product
	 */
	public User	user;

	/**
	 * Meal which contains this product
	 */
	public Meal	meal;
	/**
	 * Date when meal has been created
	 */
	public String				date;

	public Ingridient ( Context ctx ) {
		super(ctx);
	}

}
