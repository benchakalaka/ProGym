package com.progym.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WaterLevelBodyView extends ImageView {

     Paint       paint                      = new Paint();
     public int  PERCENT_COMPLETE           = 0;
     private int PERCENT_COMPLETE_IN_PIXELS = 0;

     public WaterLevelBodyView ( Context context , AttributeSet attrs ) {
          super(context, attrs);
          paint.setColor(Color.BLUE);
     }

     @Override public void onDraw(final Canvas canvas) {
          if ( PERCENT_COMPLETE != 0 ) {
               if ( PERCENT_COMPLETE < 101 ) {
                    PERCENT_COMPLETE_IN_PIXELS = getHeight() - (getHeight() / 100) * PERCENT_COMPLETE;
                    canvas.drawRect(0, PERCENT_COMPLETE_IN_PIXELS, getWidth(), getHeight(), paint);
               } else {
                    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
               }
          }
          super.onDraw(canvas);
     }
}