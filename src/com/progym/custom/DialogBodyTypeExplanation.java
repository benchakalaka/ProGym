package com.progym.custom;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.progym.R;

@EViewGroup ( R.layout.dialog_bodytype_explanation ) public class DialogBodyTypeExplanation extends RelativeLayout {

     Context            context;
     @ViewById TextView twBodyTypeExplanation;

     public DialogBodyTypeExplanation ( Context context ) {
          super(context);
          this.context = context;
     }

     /**
      * @param bodyType
      *             int value
      *             Ektomorf ------------------- 0
      *             Mezomorf ------------------- 1
      *             Endomorf ------------------- 2
      */
     /*
      * public void setBodyTypeToExplain(int bodyType) {
      * String description = "";
      * switch (bodyType) {
      * // Ektomorf
      * case 0:
      * description = this.context.getResources().getString(R.string.ektomorf);
      * break;
      * // Mezomorf
      * case 1:
      * description = this.context.getResources().getString(R.string.mezomorf);
      * break;
      * // Endomorf
      * case 2:
      * description = this.context.getResources().getString(R.string.endomorf);
      * break;
      * }
      * twBodyTypeExplanation.setText(description);
      * }
      */
}
