package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class ReadyIngridient extends SugarRecord <ReadyIngridient> {

	/**
	 * Weight of product in grams, 100 g by default
	 */
	public double	weight;
	/**
	 * Amount of KKAL in 100 g in this product
	 */
	public int	kkal;

	/**
	 * Amount of protein in grams in this product
	 */
	public double	protein;

	/**
	 * Amount of carbohydrates in grams in this product
	 */
	public double	carbohydrates;

	/**
	 * Amount of fat in grams in this product
	 */
	public double	fat;

	/**
	 * For instance - meat or porridge ...
	 */
	public String	groupName;

	/**
	 * Exact product name, for instance, chicken or oats or potatoes
	 */
	public String	name;

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
	public String	date;

	public ReadyIngridient ( Context ctx ) {
		super(ctx);
	}

}
