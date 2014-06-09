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
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.progym.R;
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_callories_monthly )
public class CalloriesProgressMonthlyLineFragment extends Fragment {

	/**
	 * 100% — FF
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
	@ViewById RelativeLayout					rlRootGraphLayout;
	@ViewById ImageView						ivPrevYear;
	@ViewById ImageView						ivNextYear;

	private static Date						DATE;

	@AnimationRes ( R.anim.fadein ) Animation	fadeIn;
	@AnimationRes ( R.anim.fadein ) Animation	fadeOut;

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setLineData3(new Date());
	}

	@Click void ivPrevYear() {
		ivNextYear.startAnimation(fadeIn);
		rlRootGraphLayout.startAnimation(fadeOut);
		DATE = DateUtils.addMonths(DATE, -1);
		// invert TYPE OF GRAPH flag
		setLineData3(DATE);
	}

	@Click void ivNextYear() {
		ivNextYear.startAnimation(fadeIn);
		rlRootGraphLayout.startAnimation(fadeOut);
		// invert TYPE OF GRAPH flag
		DATE = DateUtils.addMonths(DATE, 1);
		setLineData3(DATE);
	}

	public void setLineData3(Date date) {
		int yMaxAxisValue = 0;
		try {
			if ( rlRootGraphLayout.getChildCount() == 3 ) {
				rlRootGraphLayout.removeViewAt(0);
			}
		} catch (Exception edsx) {
			edsx.printStackTrace();
		}
		date.setDate(1);
		DATE = date;
		// Get amount of days in a month to find out average
		int daysInMonth = Utils.getDaysInMonth(date.getMonth(), Integer.valueOf(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY)));
		// set First day of the month as first month

		int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

		// Creating an XYSeries for Consumed water
		XYSeries callories = new XYSeries("Callories");

		List <Ingridient> list;
		// Adding data to Income and Expense Series
		for ( int i = 1; i <= daysInMonth; i++ ) {
			// get all water records consumed per this month
			list = DataBaseUtils.getAllFoodConsumedInMonth(Utils.formatDate(date, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD));
			// init "average" data
			int totalCallories = 0;
			for ( Ingridient ingridient : list ) {
				totalCallories += ingridient.kkal;
			}
			callories.add(i, totalCallories);
			date = DateUtils.addDays(date, 1);
			yMaxAxisValue = Math.max(yMaxAxisValue, totalCallories);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(callories);

		// Creating XYSeriesRenderer to customize protein series
		XYSeriesRenderer calloriesRenderer = new XYSeriesRenderer();
		calloriesRenderer.setColor(Color.rgb(220, 255, 110));
		calloriesRenderer.setFillPoints(true);
		calloriesRenderer.setLineWidth(3);
		calloriesRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		// multiRenderer.setXLabels(0);

		for ( int i = 0; i < x.length; i++ ) {
			multiRenderer.addXTextLabel(i, String.valueOf(x[i]));
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
		// should be same
		multiRenderer.setChartTitle(String.format("Callories statistic"));
		multiRenderer.setXTitle(Utils.getSpecificDateValue(DATE, "MMM") + " of " + Utils.formatDate(DATE, DataBaseUtils.DATE_PATTERN_YYYY));
		multiRenderer.setYTitle("Callories consumption     ");
		multiRenderer.setAxesColor(Color.WHITE);
		multiRenderer.setShowLegend(true);
		multiRenderer.addSeriesRenderer(calloriesRenderer);
		multiRenderer.setShowGrid(true);
		multiRenderer.setClickEnabled(true);
		multiRenderer.setXLabelsAngle(20);
		multiRenderer.setYAxisMax(yMaxAxisValue + 200);
		multiRenderer.setXLabelsColor(Color.WHITE);
		multiRenderer.setZoomButtonsVisible(false);
		// configure visible area
		multiRenderer.setXAxisMax(31);
		multiRenderer.setXAxisMin(1);
		multiRenderer.setAxisTitleTextSize(15);
		multiRenderer.setZoomEnabled(true);

		GraphicalView mChartView = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
		rlRootGraphLayout.addView(mChartView, 0);
	}
}
