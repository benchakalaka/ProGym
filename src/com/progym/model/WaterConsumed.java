package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Karpachev Ihor
 */
public class WaterConsumed extends SugarRecord <WaterConsumed> {

     /**
      * Capacity of consumed water in ml
      */
     public int    volumeConsumed;
     /**
      * Date when water has been consumed
      */
     public String date;
     /**
      * Link to user
      */
     public User   user;

     public WaterConsumed ( Context ctx ) {
          super(ctx);
     }

}
