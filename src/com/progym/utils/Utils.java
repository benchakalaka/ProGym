package com.progym.utils;

import com.progym.R;

public class Utils {

	public static int getImageIdByVolume(int volume) {

		int retResourceId = 0;
		switch (volume) {
			case GlobalConstants.WATER_VOLUMES.GLASS_250_ML:
				retResourceId = R.drawable.glass;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_500_ML:
				retResourceId = R.drawable.bottle;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_1_L:
				retResourceId = R.drawable.bottle2;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_2_L:
				retResourceId = R.drawable.body;
				break;
			default:
				return R.drawable.custom;
		}

		return retResourceId;
	}

	public static int getImageIdByTag(String tag) {
		// by default return glass
		int retResourceId = 0;

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.GLASS_250ML) ) {
			retResourceId = R.drawable.glass;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
			retResourceId = R.drawable.bottle;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
			retResourceId = R.drawable.bottle2;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
			retResourceId = R.drawable.body;
		}

		if ( 0 == retResourceId ) {
			retResourceId = R.drawable.custom;
		}

		return retResourceId;
	}

	public static int getVolumeByTag(String tag) {
		// by default
		int retWaterVolume = 0;

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.GLASS_250ML) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.GLASS_250_ML;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_500_ML;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_1_L;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_2_L;
		}
		// Custom water volume
		if ( 0 == retWaterVolume ) {
			retWaterVolume = Integer.valueOf(tag);
		}

		return retWaterVolume;
	}

}
