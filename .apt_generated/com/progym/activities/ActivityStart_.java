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
import android.widget.LinearLayout;
import com.progym.R.anim;
import com.progym.R.id;
import com.progym.R.layout;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ActivityStart_
    extends ActivityStart
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_start);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        shake = AnimationUtils.loadAnimation(this, anim.shake);
        leftIn1 = AnimationUtils.loadAnimation(this, anim.push_left_in);
        leftIn2 = AnimationUtils.loadAnimation(this, anim.push_left_in);
        leftIn3 = AnimationUtils.loadAnimation(this, anim.push_left_in);
        fadeIn = AnimationUtils.loadAnimation(this, anim.fadein);
        leftIn4 = AnimationUtils.loadAnimation(this, anim.push_left_in);
        leftIn0 = AnimationUtils.loadAnimation(this, anim.push_left_in);
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

    public static ActivityStart_.IntentBuilder_ intent(Context context) {
        return new ActivityStart_.IntentBuilder_(context);
    }

    public static ActivityStart_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityStart_.IntentBuilder_(fragment);
    }

    public static ActivityStart_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityStart_.IntentBuilder_(supportFragment);
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
        llProgress = ((LinearLayout) hasViews.findViewById(id.llProgress));
        llProfile = ((LinearLayout) hasViews.findViewById(id.llProfile));
        llAdvice = ((LinearLayout) hasViews.findViewById(id.llAdvice));
        llWater = ((LinearLayout) hasViews.findViewById(id.llWater));
        llNutrition = ((LinearLayout) hasViews.findViewById(id.llNutrition));
        {
            View view = hasViews.findViewById(id.llNutrition);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityStart_.this.llNutrition();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llProgress);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityStart_.this.llProgress();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llAdvice);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityStart_.this.llAdvice();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llProfile);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityStart_.this.llProfile();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.llWater);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityStart_.this.llWater();
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
            intent_ = new Intent(context, ActivityStart_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityStart_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityStart_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ActivityStart_.IntentBuilder_ flags(int flags) {
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
