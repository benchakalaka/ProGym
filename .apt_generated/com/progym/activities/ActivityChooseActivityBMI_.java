//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import com.progym.R.anim;
import com.progym.R.id;
import com.progym.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class ActivityChooseActivityBMI_
    extends ActivityChooseActivityBMI
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_bmi_choose_activity);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        fade = AnimationUtils.loadAnimation(this, anim.fade);
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

    public static ActivityChooseActivityBMI_.IntentBuilder_ intent(Context context) {
        return new ActivityChooseActivityBMI_.IntentBuilder_(context);
    }

    public static ActivityChooseActivityBMI_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ActivityChooseActivityBMI_.IntentBuilder_(fragment);
    }

    public static ActivityChooseActivityBMI_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ActivityChooseActivityBMI_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
<<<<<<< HEAD
        rbLow = ((RadioButton) hasViews.findViewById(id.rbLow));
=======
>>>>>>> develop
        rbModerate = ((RadioButton) hasViews.findViewById(id.rbModerate));
        btnNext = ((Button) hasViews.findViewById(id.btnNext));
        btnBack = ((Button) hasViews.findViewById(id.btnBack));
        rbInactive = ((RadioButton) hasViews.findViewById(id.rbInactive));
        {
            View view = hasViews.findViewById(id.btnBack);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseActivityBMI_.this.btnBack();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btnNext);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActivityChooseActivityBMI_.this.btnNext();
                    }

                }
                );
            }
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ActivityChooseActivityBMI_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityChooseActivityBMI_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ActivityChooseActivityBMI_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ActivityChooseActivityBMI_.IntentBuilder_ flags(int flags) {
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
