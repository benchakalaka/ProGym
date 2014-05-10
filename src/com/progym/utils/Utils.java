package com.progym.utils;

import com.progym.R;

public class Utils {

	public static int getImageIdByVolume(int volume) {

		int retResourceId = R.drawable.glass;
		switch (volume) {

			case GlobalConstants.WATER_VOLUMES.BOTTLE_500_ML:
				retResourceId = R.drawable.bottle;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_1_L:
				retResourceId = R.drawable.bottle2;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_2_L:
				retResourceId = R.drawable.body;
				break;
		}

		return retResourceId;
	}

	public static int getImageIdByTag(String tag) {
		// by default return glass
		int retResourceId = R.drawable.glass;

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
			retResourceId = R.drawable.bottle;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
			retResourceId = R.drawable.bottle2;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
			retResourceId = R.drawable.body;
		}

		return retResourceId;
	}

	public static int getVolumeByTag(String tag) {
		// by default return glass of water (250 ml)
		int retWaterVolume = GlobalConstants.WATER_VOLUMES.GLASS_250_ML;

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_500_ML;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_1_L;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
			retWaterVolume = GlobalConstants.WATER_VOLUMES.BOTTLE_2_L;
		}

		return retWaterVolume;
	}

}
