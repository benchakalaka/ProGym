package com.progym.custom.fragments.progress;

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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.progym.R;
import com.progym.WaterProgressActivity;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_linegraph )
public class WaterProgressMonthlyLineFragment extends Fragment {

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
	@ViewById LinearLayout						llRootGraphLayout;
	/*
	 * create graph
	 */
	LineGraphView								graphView;
	GraphViewSeries							seriesShouldDrink;
	GraphViewSeries							seriesAverage;
	GraphViewSeries							seriesConsumed;
	@AnimationRes ( R.anim.hyperspace_out ) Animation	hyperscaleOut;
	@AnimationRes ( R.anim.hyperspace_in ) Animation	hyperscaleIn;
	//@AnimationRes ( R.anim.push_left_in ) Animation	leftIn;

	@AfterViews void afterViews() {
		hyperscaleOut.setFillAfter(true);
		hyperscaleOut.setFillEnabled(true);
		//leftIn.setFillAfter(true);
		//leftIn.setFillEnabled(true);
		hyperscaleIn.setFillAfter(true);
		hyperscaleIn.setFillEnabled(true);
	}

	private void resetData() {
		
	}

	public void setLineData2(List <WaterConsumed> list, int month) {
		if ( null == list || list.isEmpty() ) {
			//resetData();
			Toast.makeText(getActivity(), "There is no data found for "+WaterProgressActivity.months[month], Toast.LENGTH_SHORT).show();
			return;
		}
		
		llRootGraphLayout.startAnimation(hyperscaleOut);
		
		int shouldDrinkWaterMlPerDay = (int) DataBaseUtils.getWaterUserShouldConsumePerDay();

		// TODO : HOW TO clear all these serieses ???
		graphView = new LineGraphView(getActivity(), "Water statistic for " + WaterProgressActivity.months[month]);

		int yMaxAxisValue = shouldDrinkWaterMlPerDay;
		// 31 - Amount of days in a month
		int daysInMonth = 31;
		// first init "should drink" data
		GraphViewData[] data = new GraphViewData[2];
		data[0] = new GraphViewData(1, shouldDrinkWaterMlPerDay);
		data[1] = new GraphViewData(daysInMonth, shouldDrinkWaterMlPerDay);

		seriesShouldDrink = new GraphViewSeries("Should drink", new GraphViewSeriesStyle(Color.rgb(250, 80, 90), 3), data);
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

		seriesAverage = new GraphViewSeries("Average Value", new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), data);

		data = new GraphViewData[daysInMonth];
		// PASS AS PARAMETR
		Date date = new Date();
		// set first day of month
		date.setDate(1);
		date = DateUtils.setMonths(date, month);
		int consumedPerDay = 0;

		for ( int i = 0; i < daysInMonth; i++ ) {
			Utils.dateFormat.applyPattern(DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
			consumedPerDay = DataBaseUtils.getConsumedPerDay(Utils.dateFormat.format(date));
			yMaxAxisValue = Math.max(yMaxAxisValue, consumedPerDay);
			data[i] = new GraphViewData(i + 1, consumedPerDay);
			// increment day
			date = DateUtils.addDays(date, 1);
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

		// graphView.setHorizontalLabels(horizontalLabels);
		// graphView.setVerticalLabels(new String[] { "high", "middle", "low" });

		llRootGraphLayout.removeAllViews();
		llRootGraphLayout.addView(graphView);
		llRootGraphLayout.startAnimation(hyperscaleIn);
	}
}
