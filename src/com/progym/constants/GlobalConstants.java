package com.progym.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents all static final constants in app
 * 
 * @author Karpachev Ihor
 */
public class GlobalConstants {

     public static final String TAG = "ProGym";

     public static interface RESTRICTION {
          public static final int RESTRICTION_25 = 25;
          public static final int RESTRICTION_24 = 24;
          public static final int RESTRICTION_19 = 19;
     }

     /**
      * Class represents different advice type
      * 
      * @author Karpachev Ihor
      */
     public static interface ADVICE_TYPE {
          public static final int    INT_BRAIN_WORK       = 0;
          public static final int    INT_FAT_BURNING      = 1;
          public static final int    INT_STRESS_REDUCTION = 2;
          public static final int    INT_ENERGY           = 3;

          public static final String STR_BRAIN_WORK       = "Brain Work";
          public static final String STR_FAT_BURNING      = "Fat burning";
          public static final String STR_STRESS_REDUCTION = "Stress reduction";
          public static final String STR_ENERGY           = "Energy";
     }

     /**
      * Class represents different water volumes
      * 
      * @author Karpachev Ihor
      */
     public static interface WATER_VOLUMES {
          public static final int    INT_GLASS_250_ML  = 250;
          public static final int    INT_BOTTLE_500_ML = 500;
          public static final int    INT_BOTTLE_1_L    = 1000;
          public static final int    INT_BOTTLE_2_L    = 2000;

          public static final String STR_GLASS_250ML   = "250 ml";
          public static final String STR_BOTTLE_500ML  = "500 ml";
          public static final String STR_BOTTLE_1L     = "1 littre";
          public static final String STR_BOTTLE_2L     = "2 littres";
     }

     public static interface INGRIDIENTS {
          // Adding child data
          List <String> MEAT_LIST       = new ArrayList <String>();
          List <String> PORRIDGE_LIST   = new ArrayList <String>();
          List <String> VEGITABLES_LIST = new ArrayList <String>();
          List <String> FRUITS_LIST     = new ArrayList <String>();
          List <String> OTHER_LIST      = new ArrayList <String>();
          List <String> READY_MEALS     = new ArrayList <String>();
     }

     /**
      * Class represents ingridient group names
      * 
      * @author Karpachev Ihor
      */
     public static interface GROUP_INGRIDIENT_NAME {
          public static final int    INT_MEAT_CATALOGUE        = 0;
          public static final int    INT_PORRIDGE_CATALOGUE    = 1;
          public static final int    INT_VEGITABLES_CATALOGUE  = 2;
          public static final int    INT_FRUITS_CATALOGUE      = 3;
          public static final int    INT_READY_MEALS_CATALOGUE = 4;
          public static final int    INT_SWEETS_MILK_CATALOGUE = 5;

          public static final String STR_MEAT_CATALOGUE        = "Meat";
          public static final String STR_PORRIDGE_CATALOGUE    = "Porridge";
          public static final String STR_VEGITABLES_CATALOGUE  = "Vegetables";
          public static final String STR_FRUITS_CATALOGUE      = "Fruits";
          public static final String STR_READY_MEALS_CATALOGUE = "Ready meals";
          public static final String STR_SWEETS_MILK_CATALOGUE = "Other";
     }

}
