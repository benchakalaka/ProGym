//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.progym.custom.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.echo.holographlibrary.PieGraph;
import com.progym.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class FragmentIngridient_
    extends FragmentIngridient
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
            contentView_ = inflater.inflate(layout.fragment_ingridient, container, false);
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

    public static FragmentIngridient_.FragmentBuilder_ builder() {
        return new FragmentIngridient_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        pieGraph = ((PieGraph) hasViews.findViewById(com.progym.R.id.pieGraph));
        etFat = ((EditText) hasViews.findViewById(com.progym.R.id.etFat));
        ivEditIngridient = ((ImageView) hasViews.findViewById(com.progym.R.id.ivEditIngridient));
        etWeight = ((EditText) hasViews.findViewById(com.progym.R.id.etWeight));
        twGroupNameAndIngridientName = ((TextView) hasViews.findViewById(com.progym.R.id.twGroupNameAndIngridientName));
        etCarbs = ((EditText) hasViews.findViewById(com.progym.R.id.etCarbs));
        etProtein = ((EditText) hasViews.findViewById(com.progym.R.id.etProtein));
        ivFoodImage = ((ImageView) hasViews.findViewById(com.progym.R.id.ivFoodImage));
        etKkal = ((EditText) hasViews.findViewById(com.progym.R.id.etKkal));
        {
            View view = hasViews.findViewById(com.progym.R.id.ivEditIngridient);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        FragmentIngridient_.this.ivEditIngridient();
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
                        FragmentIngridient_.this.ivFoodImage(event, view);
                        return true;
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(com.progym.R.id.etProtein));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        FragmentIngridient_.this.etProtein(s);
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(com.progym.R.id.etCarbs));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        FragmentIngridient_.this.etCarbs(s);
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(com.progym.R.id.etFat));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        FragmentIngridient_.this.etFat(s);
                    }

                }
                );
            }
        }
        afterViews();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public FragmentIngridient build() {
            FragmentIngridient_ fragment_ = new FragmentIngridient_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
