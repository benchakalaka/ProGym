//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.progym.R.anim;
import com.progym.R.id;
import com.progym.R.layout;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ActivityChooseTypeOfAdvice_
    extends ActivityChooseTypeOfAdvice
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_choose_type_of_advice);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
<<<<<<< HEAD
        rightIn = AnimationUtils.loadAnimation(this, anim.push_right_in);
        rightOut = AnimationUtils.loadAnimation(this, anim.push_right_out);
        leftOut = AnimationUtils.loadAnimation(this, anim.push_left_out);
        leftIn = AnimationUtils.loadAnimation(this, anim.push_left_in);
        fade = AnimationUtils.loadAnimation(this, anim.fade);
        fadeIn = AnimationUtils.loadAnimation(this, anim.fadein);
        drag = AnimationUtils.loadAnimation(this, anim.drag_animation);
=======
        leftIn = AnimationUtils.loadAnimation(this, anim.push_left_in);
        leftOut = AnimationUtils.loadAnimation(this, anim.push_left_out);
        fadeIn = AnimationUtils.loadAnimation(this, anim.fadein);
        fade = AnimationUtils.loadAnimation(this, anim.fade);
        rightIn = AnimationUtils.loadAnimation(this, anim.push_right_in);
        drag = AnimationUtils.loadAnimation(this, anim.drag_animation);
        rightOut = AnimationUtils.loadAnimation(this, anim.push_right_out);
>>>>>>> develop
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static ActivityChooseTypeOfAdvice_.IntentBuilder_ intent(Context context) {
        return new ActivityChooseTypeOfAdvice_.IntentBuilder_(context);
    }

    public static ActivityChooseTypeOfAdvice_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityChooseTypeOfAdvice_.IntentBuilder_(fragment);
    }

    public static ActivityChooseTypeOfAdvice_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityChooseTypeOfAdvice_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        ivNextDay = ((ImageView) hasViews.findViewById(id.ivNextDay));
        llLeftPanelDateWithCalendar = ((LinearLayout) hasViews.findViewById(id.llLeftPanelDateWithCalendar));
        ivPrevDay = ((ImageView) hasViews.findViewById(id.ivPrevDay));
        twCurrentDate = ((TextView) hasViews.findViewById(id.twCurrentDate));
<<<<<<< HEAD
        llWhatDoYouNeed = ((LinearLayout) hasViews.findViewById(id.llWhatDoYouNeed));
        llCalculateBMI = ((LinearLayout) hasViews.findViewById(id.llCalculateBMI));
        llUsefulTable = ((LinearLayout) hasViews.findViewById(id.llUsefulTable));
=======
        llUsefulTable = ((LinearLayout) hasViews.findViewById(id.llUsefulTable));
        llCalculateBMI = ((LinearLayout) hasViews.findViewById(id.llCalculateBMI));
        llWhatDoYouNeed = ((LinearLayout) hasViews.findViewById(id.llWhatDoYouNeed));
>>>>>>> develop
        llCalculateMyBMI = ((LinearLayout) hasViews.findViewById(id.llCalculateMyBMI));
        {
            View view = hasViews.findViewById(id.ivNextDay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseTypeOfAdvice_.this.ivNextDay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivPrevDay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseTypeOfAdvice_.this.ivPrevDay();
                    }

                }
                );
            }
        }
        {
<<<<<<< HEAD
            View view = hasViews.findViewById(id.llUsefulTable);
=======
            View view = hasViews.findViewById(id.llWhatDoYouNeed);
>>>>>>> develop
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
<<<<<<< HEAD
                        ActivityChooseTypeOfAdvice_.this.llUsefulTable();
=======
                        ActivityChooseTypeOfAdvice_.this.llWhatDoYouNeed();
>>>>>>> develop
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llCalculateBMI);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseTypeOfAdvice_.this.llCalculateBMI();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llUsefulTable);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseTypeOfAdvice_.this.llUsefulTable();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llCalculateMyBMI);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseTypeOfAdvice_.this.llCalculateMyBMI();
                    }

                }
                );
            }
        }
        afterViews();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ActivityChooseTypeOfAdvice_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityChooseTypeOfAdvice_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityChooseTypeOfAdvice_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ActivityChooseTypeOfAdvice_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

    }

}
