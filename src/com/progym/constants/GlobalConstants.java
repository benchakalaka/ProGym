package com.progym.constants;

/**
 * Class which represents all static final constants in app
 * 
 * @author Karpachev Ihor
 */
public class GlobalConstants {

	public static final String	TAG							= "ProGym";
	public static final String	DATE_PATTERN_YYYY_MM_DD_HH_MM_SS	= "yyyy/MM/dd HH:mm:ss";
	public static final String	DATE_PATTERN_HH_MM_SS			= "HH:mm:ss";
	public static final String	DATE_PATTERN_YYYY_MM_DD			= "yyyy/MM/dd";

	/**
	 * Class represents different water volumes for
	 * water activity
	 * 
	 * @author Karpachev Ihor
	 */
	public static interface WATER_VOLUMES {
		public static final int		GLASS_250_ML	= 250;
		public static final int		BOTTLE_500_ML	= 500;
		public static final int		BOTTLE_1_L	= 1000;
		public static final int		BOTTLE_2_L	= 2000;

		public static final String	GLASS_250ML	= "250 ml";
		public static final String	BOTTLE_500ML	= "500 ml";
		public static final String	BOTTLE_1L		= "1 littre";
		public static final String	BOTTLE_2L		= "2 littres";
	}
}
