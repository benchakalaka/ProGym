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

import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.progym.R;
import com.progym.activities.ActivityWaterProgress;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_linegraph_yearly ) public class WaterProgressYearlyLineFragment extends Fragment {

     /**
      * 
      100% — FF
      * 95% — F2
      * 90% — E6
      * 85% — D9
      * 80% — CC
      * 75% — BF
      * 70% — B3
      * 65% — A6
      * 60% — 99
      * 55% — 8C
      * 50% — 80
      * 45% — 73
      * 40% — 66
      * 35% — 59
      * 30% — 4D
      * 25% — 40
      * 20% — 33
      * 15% — 26
      * 10% — 1A
      * 5% — 0D
      * 0% — 00
      */
     @ViewById RelativeLayout                          rlRootGraphLayout;
     @ViewById ImageView                               ivPrevYear;
     @ViewById ImageView                               ivNextYear;

     /*
      * create graph
      */
     GraphicalView                                     mChartView;
     private static Date                               DATE;

     @AnimationRes ( R.anim.fadein ) Animation         fadeIn;
     @AnimationRes ( R.anim.fadein ) Animation         fadeOut;
     @AnimationRes ( R.anim.push_left_in ) Animation   leftIn;
     @AnimationRes ( R.anim.push_left_out ) Animation  leftOut;
     @AnimationRes ( R.anim.push_right_in ) Animation  rightIn;
     @AnimationRes ( R.anim.push_right_out ) Animation rightOut;

     @Click void ivPrevYear() {
          ivNextYear.startAnimation(fadeIn);

          rightOut.setDuration(1000);
          rightOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addYears(DATE, -1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setLineData3(DATE, true);
               }
          });
          mChartView.startAnimation(rightOut);

     }

     @Click void ivNextYear() {
          ivNextYear.startAnimation(fadeIn);

          leftOut.setDuration(1000);
          leftOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addYears(DATE, 1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setLineData3(DATE, false);
               }
          });
          mChartView.startAnimation(leftOut);
     }

     public void setLineData3(Date date, boolean isLeftIn) {
          int yMaxAxisValue = 0;
          try {
               rlRootGraphLayout.removeView(mChartView);
          } catch (Exception edsx) {
               edsx.printStackTrace();
          }
          DATE = date;
          // 31 - Amount of days in a month
          int daysInMonth = Utils.getDaysInMonth(date.getMonth(), Integer.valueOf(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));
          // set January as first month
          date.setMonth(0);
          date.setDate(1);

          int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

          // Creating an XYSeries for Consumed water
          XYSeries consumedSeries = new XYSeries("Consumed");

          List <WaterConsumed> list;
          int userShouldConsume = (int) DataBaseUtils.getWaterUserShouldConsumePerDay();
          // Adding data to Income and Expense Series
          for ( int i = 0; i < x.length; i++ ) {
               // get all water records consumed per this month
               list = DataBaseUtils.getAllWaterConsumedInMonth(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY_MM));
               // init "average" data
               int averageWaterConsumedOnYaxis = 0;
               for ( int j = 0; j < list.size(); j++ ) {
                    // calculate sum of all water consumed by user in a month
                    averageWaterConsumedOnYaxis += list.get(j).volumeConsumed;
               }
               averageWaterConsumedOnYaxis = averageWaterConsumedOnYaxis / daysInMonth;
               consumedSeries.add(i, averageWaterConsumedOnYaxis);
               // normaSeries.add(i, userShouldConsume);
               date = DateUtils.addMonths(date, 1);
               yMaxAxisValue = Math.max(yMaxAxisValue, averageWaterConsumedOnYaxis);
          }

          // Creating a dataset to hold each series
          XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
          // Adding Expense Series to dataset
          // dataset.addSeries(normaSeries);
          // Adding Income Series to the dataset
          dataset.addSeries(consumedSeries);

          // Creating XYSeriesRenderer to customize incomeSeries
          XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
          incomeRenderer.setColor(Color.rgb(50, 255, 50));
          incomeRenderer.setFillPoints(true);
          incomeRenderer.setLineWidth(2);
          incomeRenderer.setDisplayChartValues(true);

          /*
           * // Creating XYSeriesRenderer to customize expenseSeries
           * XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
           * expenseRenderer.setColor(Color.rgb(80, 220, 80));
           * expenseRenderer.setFillPoints(true);
           * expenseRenderer.setLineWidth(2);
           * expenseRenderer.setDisplayChartValues(true);
           */

          // Creating a XYMultipleSeriesRenderer to customize the whole chart
          XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
          multiRenderer.setXLabels(0);

          for ( int i = 0; i < x.length; i++ ) {
               multiRenderer.addXTextLabel(i, ActivityWaterProgress.months_short[i]);
          }

          // Adding incomeRenderer and expenseRenderer to multipleRenderer
          // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
          // should be same
          multiRenderer.setChartTitle(String.format("Water statistic for %s year", Utils.formatDate(DATE, DataBaseUtils.DATE_PATTERN_YYYY)));
          multiRenderer.setXTitle("Months");
          multiRenderer.setYTitle("Water volume (ml)");
          multiRenderer.setAxesColor(Color.WHITE);
          multiRenderer.setShowLegend(true);
          multiRenderer.addSeriesRenderer(incomeRenderer);
          multiRenderer.setShowGrid(true);
          multiRenderer.setClickEnabled(true);
          // multiRenderer.addSeriesRenderer(expenseRenderer);
          multiRenderer.setXLabelsAngle(30);
          // multiRenderer.setBackgroundColor(Color.parseColor("#B3FFFFFF"));
          // multiRenderer.setApplyBackgroundColor(true);
          multiRenderer.setXLabelsColor(Color.WHITE);
          multiRenderer.setZoomButtonsVisible(false);
          // configure visible area
          multiRenderer.setXAxisMax(11.5);
          multiRenderer.setXAxisMin(-0.5);
          multiRenderer.setYAxisMax(yMaxAxisValue + (yMaxAxisValue / 5));
          multiRenderer.setYAxisMin(-0.1);

          multiRenderer.setAxisTitleTextSize(15);
          multiRenderer.setBarSpacing(0.1);
          multiRenderer.setZoomEnabled(true);

          mChartView = ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer, Type.DEFAULT);
          rlRootGraphLayout.addView(mChartView, 0);
          if ( isLeftIn ) {
               rightIn.setDuration(1000);
               mChartView.startAnimation(rightIn);
          } else {
               leftIn.setDuration(1000);
               mChartView.startAnimation(leftIn);
          }

     }
}
