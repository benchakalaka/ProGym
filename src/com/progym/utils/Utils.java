package com.progym.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.progym.R;
import com.progym.constants.GlobalConstants;
import com.progym.custom.ExpandableListAdapter;

public class Utils {
	public final static SimpleDateFormat	dateFormat	= new SimpleDateFormat();
	
	public static int getImageIdByGroupPositionInExpListView(int groupPosition) {

		int retResourceId = 0;
		switch (groupPosition) {
			case ExpandableListAdapter.MEAT_CATALOGUE:
				retResourceId = R.drawable.food;
				break;

			case ExpandableListAdapter.FRUITS_CATALOGUE:
				retResourceId = R.drawable.bottle;
				break;

			case ExpandableListAdapter.OTHER_CATALOGUE:
				retResourceId = R.drawable.custom;
				break;

			case ExpandableListAdapter.PORRIDGE_CATALOGUE:
				retResourceId = R.drawable.training;
				break;
				
			case ExpandableListAdapter.READY_MEALS_CATALOGUE:
				retResourceId = R.drawable.male;
				break;
				
			case ExpandableListAdapter.VEGITABLES_CATALOGUE:
				retResourceId = R.drawable.female;
				break;
		}

		return retResourceId;
	}

	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	public static void log(String message) {

		Log.d(GlobalConstants.TAG, message == null ? "message == null" : message);
	}

	public static void log(int message) {
		Log.d(GlobalConstants.TAG, String.valueOf(message));
	}

	public static int getImageIdByVolume(int volume) {

		int retResourceId = 0;
		switch (volume) {
			case GlobalConstants.WATER_VOLUMES.GLASS_250_ML:
				retResourceId = R.drawable.glass_025;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_500_ML:
				retResourceId = R.drawable.bottle_05;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_1_L:
				retResourceId = R.drawable.bottle_1;
				break;

			case GlobalConstants.WATER_VOLUMES.BOTTLE_2_L:
				retResourceId = R.drawable.bottle_2;
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
			retResourceId = R.drawable.glass_025;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
			retResourceId = R.drawable.bottle_05;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
			retResourceId = R.drawable.bottle_1;
		}

		if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
			retResourceId = R.drawable.bottle_2;
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
