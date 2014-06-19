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
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.progym.R;
import com.progym.activities.ActivityWaterProgress;
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_linegraph_yearly ) public class FoodProgressYearlyLineFragment extends Fragment {

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

     private static Date                               DATE;
     /*
      * create graph
      */
     View                                              viewChart = null;

     @AnimationRes ( R.anim.fadein ) Animation         fadeIn;
     @AnimationRes ( R.anim.fadein ) Animation         fadeOut;
     @AnimationRes ( R.anim.push_left_in ) Animation   leftIn;
     @AnimationRes ( R.anim.push_left_out ) Animation  leftOut;
     @AnimationRes ( R.anim.push_right_in ) Animation  rightIn;
     @AnimationRes ( R.anim.push_right_out ) Animation rightOut;

     @Click void ivPrevYear() {
          ivPrevYear.startAnimation(fadeIn);

          rightOut.setDuration(300);
          rightOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addYears(DATE, -1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setYearProgressData(DATE, true);
               }
          });
          viewChart.startAnimation(rightOut);

     }

     @Click void ivNextYear() {
          ivNextYear.startAnimation(fadeIn);
          leftOut.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
                    DATE = DateUtils.addYears(DATE, 1);
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    setYearProgressData(DATE, false);
               }
          });
          viewChart.startAnimation(leftOut);

     }

     /**
      * Builds a bar multiple series renderer to use the provided colors.
      * 
      * @param colors
      *             the series renderers colors
      * @return the bar multiple series renderer
      */
     protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
          XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
          renderer.setAxisTitleTextSize(16);
          renderer.setChartTitleTextSize(20);
          renderer.setLabelsTextSize(15);
          renderer.setLegendTextSize(15);
          int length = colors.length;
          for ( int i = 0; i < length; i++ ) {
               // SimpleSeriesRenderer r = new SimpleSeriesRenderer();
               XYSeriesRenderer r = new XYSeriesRenderer();
               r.setColor(colors[i]);
               renderer.addSeriesRenderer(r);
          }
          return renderer;
     }

     /**
      * Sets a few of the series renderer settings.
      * 
      * @param renderer
      *             the renderer to set the properties to
      * @param title
      *             the chart title
      * @param xTitle
      *             the title for the X axis
      * @param yTitle
      *             the title for the Y axis
      * @param xMin
      *             the minimum value on the X axis
      * @param xMax
      *             the maximum value on the X axis
      * @param yMin
      *             the minimum value on the Y axis
      * @param yMax
      *             the maximum value on the Y axis
      * @param axesColor
      *             the axes color
      * @param labelsColor
      *             the labels color
      */
     protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
          renderer.setChartTitle(title);
          renderer.setXTitle(xTitle);
          renderer.setYTitle(yTitle);
          renderer.setXAxisMin(xMin);
          renderer.setXAxisMax(xMax);
          renderer.setYAxisMin(yMin);
          renderer.setYAxisMax(yMax);
          renderer.setAxesColor(axesColor);
          renderer.setLabelsColor(labelsColor);
     }

     public void setYearProgressData(final Date date, final boolean isLeftIn) {

          final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.please_wait), getResources().getString(R.string.populating_data), true);
          ringProgressDialog.setCancelable(true);
          new Thread(new Runnable() {

               @Override public void run() {
                    try {
                         int yMaxAxisValue = 0;

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   try {
                                        rlRootGraphLayout.removeView(viewChart);
                                   } catch (Exception edsx) {
                                        edsx.printStackTrace();
                                   }

                              }
                         });

                         DATE = date;
                         // Get amount of days in a month to find out average
                         int daysInMonth = Utils.getDaysInMonth(date.getMonth(), Integer.valueOf(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));
                         // set January as first month
                         date.setMonth(0);
                         date.setDate(1);

                         int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

                         final XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                         CategorySeries seriesProtein = new CategorySeries("Protein");
                         CategorySeries seriesFat = new CategorySeries("Fat");
                         CategorySeries seriesCarbs = new CategorySeries("Carbs");

                         List <Ingridient> list;
                         Date dt = date;
                         for ( int element : x ) {
                              list = DataBaseUtils.getAllFoodConsumedInMonth(Utils.formatDate(dt, DataBaseUtils.DATE_PATTERN_YYYY_MM));

                              // init "average" data
                              double totalProtein = 0 , totalFat = 0 , totalCarbs = 0 , totalCallories = 0;
                              for ( Ingridient ingridient : list ) {
                                   totalProtein += ingridient.protein;
                                   totalFat += ingridient.fat;
                                   totalCarbs += ingridient.carbohydrates;
                                   totalCallories += ingridient.kkal;
                              }

                              seriesProtein.add((double) Math.round(totalProtein * 100) / 100);
                              seriesFat.add((double) Math.round(totalFat * 100) / 100);
                              seriesCarbs.add((double) Math.round(totalCarbs * 100) / 100);

                              // calculate maximum Y axis values
                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalProtein);
                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalFat);
                              yMaxAxisValue = Math.max(yMaxAxisValue, (int) totalCarbs);

                              // increment month
                              dt = DateUtils.addMonths(dt, 1);
                         }

                         int[] colors = new int[] { getActivity().getResources().getColor(R.color.green), getActivity().getResources().getColor(R.color.orange), getActivity().getResources().getColor(R.color.purple) };
                         final XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
                         setChartSettings(renderer, String.format("Protein/Carbohydrates/Fat statistic for %s year", Utils.getSpecificDateValue(DATE, "yyyy")), "Months", "Amount (g)           ", 0.7, 12.3, 0, yMaxAxisValue + 30, Color.GRAY, Color.LTGRAY);

                         renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
                         renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
                         renderer.getSeriesRendererAt(2).setDisplayChartValues(true);

                         renderer.getSeriesRendererAt(0).setChartValuesTextSize(15f);
                         renderer.getSeriesRendererAt(1).setChartValuesTextSize(15f);
                         renderer.getSeriesRendererAt(2).setChartValuesTextSize(15f);

                         // renderer.getSeriesRendererAt(0).setChartValuesTextAlign(Align.CENTER);
                         // renderer.getSeriesRendererAt(1).setChartValuesTextAlign(Align.LEFT);
                         // renderer.getSeriesRendererAt(2).setChartValuesTextAlign(Align.RIGHT);

                         renderer.setXLabels(0);
                         // renderer.setYLabels(10);
                         // renderer.setXLabelsAlign(Align.LEFT);
                         // renderer.setYLabelsAlign(Align.LEFT);
                         // renderer.setPanEnabled(true, false);
                         renderer.setClickEnabled(false);
                         renderer.setZoomEnabled(false);
                         renderer.setPanEnabled(false, false);
                         renderer.setZoomButtonsVisible(false);
                         renderer.setPanLimits(new double[] { 1, 11 });
                         // renderer.setZoomEnabled(false);
                         // renderer.setZoomRate(1.1f);
                         renderer.setShowGrid(true);
                         renderer.setShowLegend(true);
                         renderer.setFitLegend(true);

                         for ( int i = 0; i < ActivityWaterProgress.months_short.length; i++ ) {
                              renderer.addXTextLabel(i + 1, ActivityWaterProgress.months_short[i]);

                         }

                         dataset.addSeries(seriesProtein.toXYSeries());
                         dataset.addSeries(seriesFat.toXYSeries());
                         dataset.addSeries(seriesCarbs.toXYSeries());

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   try {
                                        viewChart = ChartFactory.getLineChartView(getActivity(), dataset, renderer/* , Type.STACKED */);
                                        rlRootGraphLayout.addView(viewChart, 0);
                                        if ( isLeftIn ) {
                                             rightIn.setDuration(1000);
                                             viewChart.startAnimation(rightIn);
                                        } else {
                                             leftIn.setDuration(1000);
                                             viewChart.startAnimation(leftIn);
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
