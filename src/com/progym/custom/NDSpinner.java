package com.progym.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/** Spinner extension that calls onItemSelected even when the selection is the same as its previous value */
public class NDSpinner extends Spinner {
     private int lastSelected = 3;

     public NDSpinner ( Context context ) {
          super(context);
     }

     public NDSpinner ( Context context , AttributeSet attrs ) {
          super(context, attrs);
     }

     public NDSpinner ( Context context , AttributeSet attrs , int defStyle ) {
          super(context, attrs, defStyle);
     }

     @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
          if ( this.lastSelected == this.getSelectedItemPosition() && getOnItemSelectedListener() != null ) {
               getOnItemSelectedListener().onItemSelected(this, getSelectedView(), this.getSelectedItemPosition(), getSelectedItemId());
          }
          if ( !changed ) {
               lastSelected = this.getSelectedItemPosition();
          }

          super.onLayout(changed, l, t, r, b);
     }
}
