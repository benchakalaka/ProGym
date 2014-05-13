package com.progym.utils;

import java.util.List;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.progym.R;
import com.progym.model.User;
import com.progym.model.WaterConsumed;

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

     /**
      * Class helps to do CRUD on database
      * 
      * @author Karpachev Ihor
      */
     public static class DataBaseUtils {
          // Get all water record
          public static List <WaterConsumed> getAllWaterConsumed() {
               return WaterConsumed.listAll(WaterConsumed.class);
          }

          // Display volumes consumed only in this day
          public static List <WaterConsumed> getWaterConsumedByDate(String date) {
               return Select.from(WaterConsumed.class).where(Condition.prop("date").like(date + "%")).list();
          }

          // return user object by name
          public static User getUserByName(String name) {
               List <User> users = User.find(User.class, "name = ?", name);
               return users.isEmpty() ? null : users.get(0);
          }

          // return amount of water in ml consumed per day
          public static int getConsumedPerDay(String day) {
               int alreadyDrinked = 0;
               List <WaterConsumed> list = DataBaseUtils.getWaterConsumedByDate(day);
               for ( WaterConsumed w : list ) {
                    alreadyDrinked += w.volumeConsumed;
               }
               return alreadyDrinked;
          }
     }

}
