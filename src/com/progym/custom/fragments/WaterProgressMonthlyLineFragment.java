package com.progym.custom.fragments;

import java.util.Date;
import java.util.List;

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

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.progym.R;
import com.progym.activities.ActivityWaterProgress;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_linegraph_monthly ) public class WaterProgressMonthlyLineFragment extends Fragment {

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
     LineGraphView                                     graphView;
     GraphViewSeries                                   seriesShouldDrink;
     GraphViewSeries                                   seriesAverage;
     GraphViewSeries                                   seriesConsumed;

     @AnimationRes ( R.anim.fadein ) Animation         fadeIn;
     @AnimationRes ( R.anim.fadeout ) Animation        fadeOut;
     @AnimationRes ( R.anim.push_left_in ) Animation   leftIn;
     @AnimationRes ( R.anim.push_left_out ) Animation  leftOut;
     @AnimationRes ( R.anim.push_right_in ) Animation  rightIn;
     @AnimationRes ( R.anim.push_right_out ) Animation rightOut;
     private Date                                      DATE;

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
                    setLineData2(DATE, true);
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
                    setLineData2(DATE, false);
               }
          });
          graphView.startAnimation(leftOut);

     }

     public void setLineData2(final Date date, final boolean isLeftIn) {

          final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.please_wait), getResources().getString(R.string.populating_data), true);
          ringProgressDialog.setCancelable(true);
          new Thread(new Runnable() {

               @Override public void run() {
                    try {

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   try {
                                        // magic 3, 2 arrows and graphview
                                        if ( rlRootGraphLayout.getChildCount() == 3 ) {
                                             rlRootGraphLayout.removeViewAt(0);
                                        }
                                   } catch (Exception edsx) {
                                        edsx.printStackTrace();
                                   }
                              }
                         });

                         DATE = date;
                         // 31 - Amount of days in a month
                         int daysInMonth = Utils.getDaysInMonth(date.getMonth(), Integer.valueOf(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));

                         // Add to expandable list view ready meal date
                         List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumedInMonth(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY_MM));

                         int shouldDrinkWaterMlPerDay = (int) DataBaseUtils.getWaterUserShouldConsumePerDay();

                         // TODO : HOW TO clear all these serieses ???
                         graphView = new LineGraphView(getActivity(), String.format("Water statistic for %s  %s", ActivityWaterProgress.months[date.getMonth()], Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));

                         int yMaxAxisValue = shouldDrinkWaterMlPerDay;

                         // first init "should drink" data
                         GraphViewData[] data = new GraphViewData[2];
                         data[0] = new GraphViewData(1, shouldDrinkWaterMlPerDay);
                         data[1] = new GraphViewData(daysInMonth, shouldDrinkWaterMlPerDay);

                         seriesShouldDrink = new GraphViewSeries("Normal", new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), data);
                         // init "average" data
                         int averageWaterConsumedOnYaxis = 0;
                         for ( int i = 0; i < list.size(); i++ ) {
                              // calculate sum of all water consumed by user in a month
                              averageWaterConsumedOnYaxis += list.get(i).volumeConsumed;
                         }
                         averageWaterConsumedOnYaxis = averageWaterConsumedOnYaxis / daysInMonth;

                         yMaxAxisValue = Math.max(yMaxAxisValue, averageWaterConsumedOnYaxis);

                         data = new GraphViewData[2];
                         data[0] = new GraphViewData(1, averageWaterConsumedOnYaxis);
                         data[1] = new GraphViewData(daysInMonth, averageWaterConsumedOnYaxis);

                         seriesAverage = new GraphViewSeries("Average Value", new GraphViewSeriesStyle(Color.rgb(250, 80, 90), 3), data);

                         data = new GraphViewData[daysInMonth];
                         // set first day of month
                         Date dt = date; // *
                         dt.setDate(1);

                         // date.setDate(1);
                         // date = DateUtils.setMonths(date, date.getMonth());

                         dt = DateUtils.setMonths(dt, dt.getMonth());;

                         int consumedPerDay = 0;

                         for ( int i = 0; i < daysInMonth; i++ ) {
                              consumedPerDay = DataBaseUtils.getConsumedPerDay(Utils.formatDate(dt, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD));
                              yMaxAxisValue = Math.max(yMaxAxisValue, consumedPerDay);
                              data[i] = new GraphViewData(i + 1, consumedPerDay);
                              // increment day
                              // date = DateUtils.addDays(date, 1);
                              dt = DateUtils.addDays(dt, 1);
                         }

                         seriesConsumed = new GraphViewSeries("Consumed", new GraphViewSeriesStyle(Color.BLUE, 4), data);

                         // add data
                         graphView.addSeries(seriesAverage);
                         graphView.addSeries(seriesShouldDrink);
                         graphView.addSeries(seriesConsumed);
                         // optional - set view port, start=2, size=10
                         // graphView.setViewPort(2, 10);

                         graphView.getGraphViewStyle().setTextSize(15);
                         graphView.getGraphViewStyle().setVerticalLabelsColor(Color.DKGRAY);
                         graphView.getGraphViewStyle().setGridColor(Color.DKGRAY);

                         graphView.setManualYAxisBounds(yMaxAxisValue, 0);
                         graphView.getGraphViewStyle().setNumHorizontalLabels(daysInMonth / 3);
                         // optional - legend
                         graphView.setShowLegend(true);

                         getActivity().runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   rlRootGraphLayout.addView(graphView, 0);

                                   if ( isLeftIn ) {
                                        rightIn.setDuration(1000);
                                        graphView.startAnimation(rightIn);
                                   } else {
                                        leftIn.setDuration(1000);
                                        graphView.startAnimation(leftIn);
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
