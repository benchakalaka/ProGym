package com.progym.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.progym.R;
import com.progym.constants.GlobalConstants;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_advice_level_1 ) public class ActivityAdviceLevel_1 extends Activity {
     @ViewById RelativeLayout                 rlSlideMe;
     @ViewById LinearLayout                   llDragOnMe;

     @ViewById ImageView                      ivBrain;
     @ViewById ImageView                      ivEnergy;
     @ViewById ImageView                      ivFatBurinig;
     @ViewById ImageView                      ivStressReduction;

     @AnimationRes ( R.anim.shake ) Animation shake;

     @AfterViews void afterViews() {
          Utils.showCustomToast(ActivityAdviceLevel_1.this, R.string.drag_items_to_screen_center, R.drawable.info);
          ivBrain.setTag(String.valueOf(GlobalConstants.ADVICE_TYPE.INT_BRAIN_WORK));
          ivEnergy.setTag(String.valueOf(GlobalConstants.ADVICE_TYPE.INT_ENERGY));
          ivFatBurinig.setTag(String.valueOf(GlobalConstants.ADVICE_TYPE.INT_FAT_BURNING));
          ivStressReduction.setTag(String.valueOf(GlobalConstants.ADVICE_TYPE.INT_STRESS_REDUCTION));

          llDragOnMe.setOnDragListener(new OnDragListener() {

               @Override public boolean onDrag(View v, DragEvent event) {
                    if ( event.getAction() == DragEvent.ACTION_DROP ) {
                         final String tagAdviceType = event.getClipData().getDescription().getLabel().toString();
                         ActivityAdviceLevel_2_.CURRENT_ACTIVITY_TYPE = Integer.valueOf(tagAdviceType);
                         switch (ActivityAdviceLevel_2_.CURRENT_ACTIVITY_TYPE) {
                              case GlobalConstants.ADVICE_TYPE.INT_BRAIN_WORK:
                                   Utils.showCustomToast(ActivityAdviceLevel_1.this, R.string.brain_work, R.drawable.brain);
                                   Intent brain = new Intent(ActivityAdviceLevel_1.this, ActivityAdviceLevel_2_.class);
                                   brain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                   startActivity(brain);
                                   ActivityAdviceLevel_1.this.overridePendingTransition(R.anim.push_down_in, 0);
                                   break;

                              case GlobalConstants.ADVICE_TYPE.INT_ENERGY:
                                   Utils.showCustomToast(ActivityAdviceLevel_1.this, R.string.energy, R.drawable.energy);
                                   Intent energy = new Intent(ActivityAdviceLevel_1.this, ActivityAdviceLevel_2_.class);
                                   energy.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                   startActivity(energy);
                                   ActivityAdviceLevel_1.this.overridePendingTransition(R.anim.push_up_in, 0);
                                   break;

                              case GlobalConstants.ADVICE_TYPE.INT_FAT_BURNING:
                                   Utils.showCustomToast(ActivityAdviceLevel_1.this, R.string.fat_burning, R.drawable.pfc);
                                   Intent fat = new Intent(ActivityAdviceLevel_1.this, ActivityAdviceLevel_2_.class);
                                   fat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                   startActivity(fat);
                                   ActivityAdviceLevel_1.this.overridePendingTransition(R.anim.push_right_in, 0);
                                   break;

                              case GlobalConstants.ADVICE_TYPE.INT_STRESS_REDUCTION:
                                   Utils.showCustomToast(ActivityAdviceLevel_1.this, R.string.stress_reduction, R.drawable.stress);
                                   Intent stress = new Intent(ActivityAdviceLevel_1.this, ActivityAdviceLevel_2_.class);
                                   stress.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                   startActivity(stress);
                                   ActivityAdviceLevel_1.this.overridePendingTransition(R.anim.push_left_in, 0);
                                   break;
                         }

                    }
                    return true;
               }
          });

          /*
           * rlSlideMe.setOnTouchListener(new OnSwipeListener(ActivityAdviceLevel_1.this) {
           * @Override public void onSwipeTop() {}
           * @Override public void onSwipeRight() {}
           * @Override public void onSwipeLeft() {}
           * @Override public void onSwipeBottom() {}
           * @Override public boolean onTouch(View v, MotionEvent event) {
           * return gestureDetector.onTouchEvent(event);
           * }
           * });
           */
     }

     @Touch void ivBrain(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateCenter();
               dragView(v);
          }
     }

     @Touch void ivEnergy(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateCenter();
               dragView(v);
          }
     }

     @Touch void ivFatBurinig(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateCenter();
               dragView(v);
          }
     }

     private void animateCenter() {
          llDragOnMe.startAnimation(shake);
     }

     @Touch void ivStressReduction(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               animateCenter();
               dragView(v);
          }
     }

     private void dragView(View v) {
          String tag = v.getTag().toString();
          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);
     }
}
