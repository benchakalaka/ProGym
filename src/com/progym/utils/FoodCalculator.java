package com.progym.utils;

/**
 * Class represents
 * simple calculator which helps convert
 * 
 * @author Karpachev Ihor
 */
public class FoodCalculator {

	/**
	 * @param kkal_In_100G
	 * @param weight
	 * @return
	 */
	public static int getKkal(double kkal_In_100G, int weight) {
		return (int)(kkal_In_100G / 100 * weight);
	}

	/**
	 * @param fat_In_100G
	 * @param weight
	 * @return
	 */
	public static int getFat(double fat_In_100G, int weight) {
		return (int)(fat_In_100G / 100 * weight);
	}

	/**
	 * @param protein_In_100G
	 * @param weight
	 * @return
	 */
	public static int getProtein(double protein_In_100G, int weight) {
		return (int)(protein_In_100G / 100 * weight);
	}

	/**
	 * @param carbs_In_100G
	 * @param weight
	 * @return
	 */
	public static int getCarbs(double carbs_In_100G, int weight) {
		return (int)(carbs_In_100G / 100 * weight);
	}

}
