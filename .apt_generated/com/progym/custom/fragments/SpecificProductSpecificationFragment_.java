//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym.custom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.progym.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SpecificProductSpecificationFragment_
    extends SpecificProductSpecificationFragment
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
            contentView_ = inflater.inflate(layout.fragment_specific_product_spec, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static SpecificProductSpecificationFragment_.FragmentBuilder_ builder() {
        return new SpecificProductSpecificationFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        btnShowFoodTypes = ((Button) hasViews.findViewById(com.progym.R.id.btnShowFoodTypes));
        ivFoodImage = ((ImageView) hasViews.findViewById(com.progym.R.id.ivFoodImage));
        {
            View view = hasViews.findViewById(com.progym.R.id.btnShowFoodTypes);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SpecificProductSpecificationFragment_.this.btnShowFoodTypes();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.progym.R.id.ivFoodImage);
            if (view!= null) {
                view.setOnTouchListener(new OnTouchListener() {


                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        SpecificProductSpecificationFragment_.this.ivFoodImage(event, view);
                        return true;
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

        public SpecificProductSpecificationFragment build() {
            SpecificProductSpecificationFragment_ fragment_ = new SpecificProductSpecificationFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
