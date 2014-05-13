package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * User entity represents one single user
 * 
 * @author Karpachev Ihor
 */
public class User extends SugarRecord <User> {

     // ------------------------- body fat level, future feature
     public String name;
     /**
      * Male - 0
      * Female - 1
      */
     public int    gender;
     public double weight;
     public int    height;
     public int    age;
     /**
      * Ektomorf ------------------- 0
      * Mezomorf ------------------- 1
      * Endomorf ------------------- 2
      * Ekto-Mezo ------------------ 3
      * Endo-Mezo ------------------ 4
      */
     public int    bodyType;

     public User ( Context arg0 ) {
          super(arg0);
     }

}
