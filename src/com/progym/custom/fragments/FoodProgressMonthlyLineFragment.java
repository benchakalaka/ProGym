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
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.progym.R;
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_linegraph_monthly ) public class FoodProgressMonthlyLineFragment extends Fragment {

     /**
      * 100% � FF
      * 95% � F2
      * 90% � E6
      * 85% � D9
      * 80% � CC
      * 75% � BF
      * 70% � B3
      * 65% � A6
      * 60% � 99
      * 55% � 8C
      * 50% � 80
      * 45% � 73
      * 40% � 66
      * 35% � 59
      * 30% � 4D
      * 25% � 40
      * 20% � 33
      * 15% � 26
      * 10% � 1A
      * 5% � 0D
      * 0% � 00
      */
     @ViewById RelativeLayout                          rlRootGraphLayout;
     @ViewById ImageView                               ivPrevYear;
     @ViewById ImageView                               ivNextYear;

     /*
      * create graph
      */
     private static Date                               DATE;

     /**
      * GraphVies
      */
     GraphicalView                                     graphView;

     @AnimationRes ( R.anim.fadein ) Animation         fadeIn;
     @AnimationRes ( R.anim.fadein ) Animation         fadeOut;

     @AnimationRes ( R.anim.push_left_in ) Animation   leftIn;
     @AnimationRes ( R.anim.push_left_out ) Animation  leftOut;
     @AnimationRes ( R.anim.push_right_in ) Animation  rightIn;
     @AnimationRes ( R.anim.push_right_out ) Animation rightOut;

     @Click void ivPrevYear() {
          ivPrevYear.startAnimation(fadeIn);

          rightOut.setDuration(1000);
          rightOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addMonths(DATE, -1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setLineData3(DATE, true);
               }
          });
          graphView.startAnimation(rightOut);
     }

     @Click void ivNextYear() {
          ivNextYear.startAnimation(fadeIn);
          leftOut.setDuration(1000);
          leftOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addMonths(DATE, 1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setLineData3(DATE, false);
               }
          });
          graphView.startAnimation(leftOut);
     }

     public void setLineData3(final Date date, final boolean isLeftIn) {

          final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.please_wait), getResources().getString(R.string.populating_data), true);
          ringProgressDialog.setCancelable(true);
          new Thread(new Runnable() {

               @Override public void run() {
                    try {
                         int yMaxAxisValue = 0;

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   try {
                                        rlRootGraphLayout.removeView(graphView);
                                   } catch (Exception edsx) {
                                        edsx.printStackTrace();
                                   }

                              }
                         });

                         date.setDate(1);
                         DATE = date;
                         // Get amount of days in a month to find out average
                         int daysInMonth = Utils.getDaysInMonth(date.getMonth(), Integer.valueOf(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));
                         // set First day of the month as first month

                         int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

                         // Creating an XYSeries for Consumed water
                         XYSeries protein = new XYSeries("Protein");
                         XYSeries fat = new XYSeries("Fat");
                         XYSeries carbs = new XYSeries("Carbs");

                         List <Ingridient> list;
                         Date dt = date; // *
                         // Adding data to Income and Expense Series
                         for ( int i = 1; i <= daysInMonth; i++ ) {
                              // get all water records consumed per this month
                              list = DataBaseUtils.getAllFoodConsumedInMonth(Utils.formatDate(dt, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD));
                              // init "average" data
                              double totalProtein = 0 , totalFat = 0 , totalCarbs = 0;
                              if ( null != list ) {
                                   for ( Ingridient ingridient : list ) {
                                        totalProtein += ingridient.protein;
                                        totalFat += ingridient.fat;
                                        totalCarbs += ingridient.carbohydrates;
                                   }
                              }

                              protein.add(i, (double) Math.round(totalProtein * 100) / 100);
                              fat.add(i, (double) Math.round(totalFat * 100) / 100);
                              carbs.add(i, (double) Math.round(totalCarbs * 100) / 100);
                              // calories.add(i, totalCallories);
                              dt = DateUtils.addDays(dt, 1);

                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalProtein);
                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalFat);
                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalCarbs);
                         }

                         // Creating a dataset to hold each series
                         final XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                         // Adding Income Series to the dataset
                         dataset.addSeries(protein);
                         dataset.addSeries(carbs);
                         dataset.addSeries(fat);

                         // Creating XYSeriesRenderer to customize protein series
                         XYSeriesRenderer proteinRenderer = new XYSeriesRenderer();
                         proteinRenderer.setColor(Color.rgb(50, 255, 50));
                         proteinRenderer.setFillPoints(true);
                         proteinRenderer.setLineWidth(3);
                         proteinRenderer.setDisplayChartValues(true);

                         // Creating XYSeriesRenderer to customize protein series
                         XYSeriesRenderer fatRenderer = new XYSeriesRenderer();
                         fatRenderer.setColor(Color.rgb(123, 111, 00));
                         fatRenderer.setFillPoints(true);
                         fatRenderer.setLineWidth(3);
                         fatRenderer.setDisplayChartValues(true);

                         // Creating XYSeriesRenderer to customize protein series
                         XYSeriesRenderer carbsRenderer = new XYSeriesRenderer();
                         carbsRenderer.setColor(Color.rgb(222, 13, 11));
                         carbsRenderer.setFillPoints(true);
                         carbsRenderer.setLineWidth(3);
                         carbsRenderer.setDisplayChartValues(true);

                         // Creating a XYMultipleSeriesRenderer to customize the whole chart
                         final XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
                         // multiRenderer.setXLabels(0);

                         for ( int i = 0; i < x.length; i++ ) {
                              multiRenderer.addXTextLabel(i, String.valueOf(x[i]));
                         }

                         // Adding incomeRenderer and expenseRenderer to multipleRenderer
                         // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
                         // should be same
                         multiRenderer.setChartTitle(String.format("Protein/Carbs/Fat statistic "));
                         multiRenderer.setXTitle(Utils.getSpecificDateValue(DATE, "MMM") + " of " + Utils.formatDate(DATE, DataBaseUtils.DATE_PATTERN_YYYY));
                         multiRenderer.setYTitle("Amount (g)          ");
                         multiRenderer.setAxesColor(Color.WHITE);
                         multiRenderer.setShowLegend(true);
                         multiRenderer.addSeriesRenderer(proteinRenderer);
                         multiRenderer.addSeriesRenderer(carbsRenderer);
                         multiRenderer.addSeriesRenderer(fatRenderer);

                         multiRenderer.setShowGrid(true);
                         multiRenderer.setClickEnabled(true);
                         multiRenderer.setXLabelsAngle(20);
                         multiRenderer.setXLabelsColor(Color.WHITE);
                         multiRenderer.setZoomButtonsVisible(false);
                         // configure visible area
                         multiRenderer.setXAxisMax(31);
                         multiRenderer.setXAxisMin(1);
                         multiRenderer.setYAxisMax(yMaxAxisValue + 30);
                         multiRenderer.setAxisTitleTextSize(15);
                         multiRenderer.setZoomEnabled(true);

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   try {
                                        graphView = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
                                        rlRootGraphLayout.addView(graphView, 0);

                                        if ( isLeftIn ) {
                                             rightIn.setDuration(1000);
                                             graphView.startAnimation(rightIn);
                                        } else {
                                             leftIn.setDuration(1000);
                                             graphView.startAnimation(leftIn);
                                        }
                                   } catch (Exception edsx) {
                                        edsx.printStackTrace();
                                   }

                              }
                         });

                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                    ringProgressDialog.dismiss();
               }
          }).start();

     }
}
