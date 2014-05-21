//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import com.progym.R.array;
import com.progym.R.id;
import com.progym.R.layout;
import kankan.wheel.widget.WheelView;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class UserProfileActivity_
    extends UserProfileActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.user_profile_activity);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        Resources resources_ = this.getResources();
        genders = resources_.getStringArray(array.genders);
        bodyTypes = resources_.getStringArray(array.bodyTypes);
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

    public static UserProfileActivity_.IntentBuilder_ intent(Context context) {
        return new UserProfileActivity_.IntentBuilder_(context);
    }

    public static UserProfileActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new UserProfileActivity_.IntentBuilder_(fragment);
    }

    public static UserProfileActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new UserProfileActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        wheelHeight = ((WheelView) hasViews.findViewById(id.wheelHeight));
        etName = ((EditText) hasViews.findViewById(id.etName));
        wheelAge = ((WheelView) hasViews.findViewById(id.wheelAge));
        wheelGender = ((WheelView) hasViews.findViewById(id.wheelGender));
        wheelWeight = ((WheelView) hasViews.findViewById(id.wheelWeight));
        btnSave = ((Button) hasViews.findViewById(id.btnSave));
        wheelBodyType = ((WheelView) hasViews.findViewById(id.wheelBodyType));
        {
            View view = hasViews.findViewById(id.btnClearProfile);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserProfileActivity_.this.btnClearProfile();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btnSave);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserProfileActivity_.this.btnSave();
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
            intent_ = new Intent(context, UserProfileActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, UserProfileActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, UserProfileActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public UserProfileActivity_.IntentBuilder_ flags(int flags) {
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
