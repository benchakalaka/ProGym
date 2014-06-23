package com.progym.model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * User entity represents one single user
 * 
 * @author Karpachev Ihor
 */
public class User extends SugarRecord <User> {

     public String name;
     /**
      * Male - 0
      * Female - 1
      */
     public int    gender;
     /**
      * User weight (kg)
      */
     public double weight;
     /**
      * User height (cm)
      */
     public double height;
     /**
      * User age
      */
     public int    age;

     public User ( Context arg0 ) {
          super(arg0);
     }

}
