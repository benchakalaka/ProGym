package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class CustomWaterVolume extends SugarRecord <CustomWaterVolume> {

     /**
      * Link to user
      */
     public User user;
     /**
      * Water capacity in ml (2000 == 2L)
      */
     public int  customVolume;

     public CustomWaterVolume ( Context ctx ) {
          super(ctx);
     }

}
