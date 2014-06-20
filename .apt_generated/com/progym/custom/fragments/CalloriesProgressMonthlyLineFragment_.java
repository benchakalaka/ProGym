//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym.custom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.progym.R.anim;
import com.progym.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class CalloriesProgressMonthlyLineFragment_
    extends CalloriesProgressMonthlyLineFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_callories_monthly, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        leftOut = AnimationUtils.loadAnimation(getActivity(), anim.push_left_out);
        fadeIn = AnimationUtils.loadAnimation(getActivity(), anim.fadein);
        rightOut = AnimationUtils.loadAnimation(getActivity(), anim.push_right_out);
        fadeOut = AnimationUtils.loadAnimation(getActivity(), anim.fadein);
        leftIn = AnimationUtils.loadAnimation(getActivity(), anim.push_left_in);
        rightIn = AnimationUtils.loadAnimation(getActivity(), anim.push_right_in);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static CalloriesProgressMonthlyLineFragment_.FragmentBuilder_ builder() {
        return new CalloriesProgressMonthlyLineFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        ivNextYear = ((ImageView) hasViews.findViewById(com.progym.R.id.ivNextYear));
        rlRootGraphLayout = ((RelativeLayout) hasViews.findViewById(com.progym.R.id.rlRootGraphLayout));
        ivPrevYear = ((ImageView) hasViews.findViewById(com.progym.R.id.ivPrevYear));
        {
            View view = hasViews.findViewById(com.progym.R.id.ivPrevYear);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CalloriesProgressMonthlyLineFragment_.this.ivPrevYear();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.progym.R.id.ivNextYear);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CalloriesProgressMonthlyLineFragment_.this.ivNextYear();
                    }

                }
                );
            }
        }
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public CalloriesProgressMonthlyLineFragment build() {
            CalloriesProgressMonthlyLineFragment_ fragment_ = new CalloriesProgressMonthlyLineFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}