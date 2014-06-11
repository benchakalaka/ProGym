package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

public class ReadyMeal extends SugarRecord <ReadyMeal> {

     public User   user;
     /**
      * Meal date in format YYYY_MM_DD_HH_MM_SSSS
      */
     public String date;

     public ReadyMeal ( Context arg0 ) {
          super(arg0);
     }
}
