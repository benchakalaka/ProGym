package com.progym.custom;

import java.lang.reflect.Field;

import android.support.v4.app.Fragment;

import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

public class CaldroidFragmentCustom extends CaldroidFragment {
     private static final Field sChildFragmentManagerField;

     static {
          Field f = null;
          try {
               f = Fragment.class.getDeclaredField("mChildFragmentManager");
               f.setAccessible(true);
          } catch (NoSuchFieldException e) {
               Utils.log("Error getting mChildFragmentManager field");
          }
          sChildFragmentManagerField = f;
     }

     @Override public void onDetach() {
          super.onDetach();

          if ( sChildFragmentManagerField != null ) {
               try {
                    sChildFragmentManagerField.set(this, null);
               } catch (Exception e) {
                    Utils.log("Error setting mChildFragmentManager field");
               }
          }
     }
}