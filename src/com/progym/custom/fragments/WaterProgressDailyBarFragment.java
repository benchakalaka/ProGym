package com.progym.custom.fragments;

/*
 * Created by Daniel Nadeau
 * daniel.nadeau01@gmail.com
 * danielnadeau.blogspot.com
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.ArrayList;
import java.util.Date;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.progym.R;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_bargraph_daily ) public class WaterProgressDailyBarFragment extends Fragment {

     @ViewById RelativeLayout                   rlRootDailyBar;
     @ViewById ImageView                        ivPrevYear;
     @ViewById ImageView                        ivNextYear;
     @ViewById BarGraph                         bargraph;
     @ViewById TextView                         twCurrentDay;

     @AnimationRes ( R.anim.fadein ) Animation  fadeIn;
     @AnimationRes ( R.anim.fadeout ) Animation fadeOut;
     private Date                               DATE;

     @Override public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          setBarData(new Date());
     }

     @Click void ivPrevYear() {
          ivNextYear.startAnimation(fadeIn);
          rlRootDailyBar.startAnimation(fadeOut);
          DATE = DateUtils.addDays(DATE, -1);
          setBarData(DATE);
     }

     @Click void ivNextYear() {
          ivNextYear.startAnimation(fadeIn);
          rlRootDailyBar.startAnimation(fadeOut);
          DATE = DateUtils.addDays(DATE, 1);
          setBarData(DATE);
     }

     public void setBarData(Date d) {
          this.DATE = d;
          twCurrentDay.setText(Utils.formatDate(d, "EEEE") + " - " + Utils.formatDate(d, "dd") + " of " + Utils.formatDate(d, "MMM"));
          final String date = Utils.formatDate(this.DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
          final double shouldDrink = DataBaseUtils.getWaterUserShouldConsumePerDay();
          final int consumed = DataBaseUtils.getConsumedPerDay(date);

          final Resources resources = getResources();
          ArrayList <Bar> aBars = new ArrayList <Bar>();

          Bar bar = new Bar();
          bar.setColor(resources.getColor(R.color.green_light));
          bar.setSelectedColor(resources.getColor(R.color.caldroid_white));
          bar.setName("Norma");
          bar.setValue((int) shouldDrink);
          bar.setValueString(String.format("%.2f L", (shouldDrink / 1000f)));
          aBars.add(bar);

          bar = new Bar();
          bar.setColor(resources.getColor(R.color.caldroid_holo_blue_light));
          bar.setName("Consumed");
          bar.setValue(consumed);
          bar.setValueString(String.format("%.2f L", (consumed / 1000f)));
          aBars.add(bar);

          bargraph.setBars(aBars);

          bargraph.setOnBarClickedListener(new OnBarClickedListener() {

               @Override public void onClick(int index) {
                    if ( 0 == index ) {
                         Toast.makeText(getActivity(), "Norma for you " + String.format("%.2f L per day", (shouldDrink / 1000f)), Toast.LENGTH_SHORT).show();
                    }

                    if ( 1 == index ) {
                         Toast.makeText(getActivity(), date + " you consumed " + String.format("%.2f L", (consumed / 1000f)), Toast.LENGTH_SHORT).show();
                    }

               }
          });

     }
}
