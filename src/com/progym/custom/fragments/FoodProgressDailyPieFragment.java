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

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.apache.commons.lang3.time.DateUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.progym.R;
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_foodprogress_piechart_daily )
public class FoodProgressDailyPieFragment extends Fragment {

	@ViewById RelativeLayout					rlRootDailyBar;
	@ViewById ImageView						ivPrevYear;
	@ViewById ImageView						ivNextYear;
	@ViewById PieGraph						pieGraphFoodStats;
	@ViewById TextView						twCurrentDay;
	@ViewById TextView						twProtein;
	@ViewById TextView						twFat;
	@ViewById TextView						twCarbs;
	@ViewById TextView						twCalories;

	// protein, carbs, fat pie slices
	PieSlice								sliceProtein	= new PieSlice();
	PieSlice								sliceFat		= new PieSlice();
	PieSlice								sliceCarbs	= new PieSlice();

	@AnimationRes ( R.anim.fadein ) Animation	fadeIn;
	@AnimationRes ( R.anim.fadeout ) Animation	fadeOut;
	private Date							DATE;
	double								totalProtein	= 0 , totalFat = 0 , totalCarbs = 0;
	int									totalCallories	= 0;

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		sliceProtein.setColor(getActivity().getResources().getColor(R.color.green));
		sliceFat.setColor(getActivity().getResources().getColor(R.color.orange));
		sliceCarbs.setColor(getActivity().getResources().getColor(R.color.purple));

		sliceProtein.setSelectedColor(getActivity().getResources().getColor(R.color.caldroid_white));
		sliceFat.setSelectedColor(getActivity().getResources().getColor(R.color.caldroid_white));
		sliceCarbs.setSelectedColor(getActivity().getResources().getColor(R.color.caldroid_white));
		setPieData(new Date());
		pieGraphFoodStats.setOnSliceClickedListener(new OnSliceClickedListener() {
			@Override public void onClick(int index) {
				// pieGraph.getSlice(index).setValue(pieGraph.getSlice(index).getValue() + 1);

				// 0 -> prot | 1 -> carbs | 2 -> fat
				switch (index) {
					case 0:
						Toast.makeText(getActivity(), " You consumed " + String.format("%.2f", totalProtein) + " g of protein", Toast.LENGTH_SHORT).show();
						break;

					case 1:
						Toast.makeText(getActivity(), " You consumed " + String.format("%.2f", totalCarbs) + " g of carbohydrates", Toast.LENGTH_SHORT).show();
						break;

					case 2:
						Toast.makeText(getActivity(), " You consumed " + String.format("%.2f", totalFat) + " g of fat", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});
	}

	@Click void ivPrevYear() {
		ivNextYear.startAnimation(fadeIn);
		rlRootDailyBar.startAnimation(fadeOut);
		DATE = DateUtils.addDays(DATE, -1);
		setPieData(DATE);
	}

	@Click void ivNextYear() {
		ivNextYear.startAnimation(fadeIn);
		rlRootDailyBar.startAnimation(fadeOut);
		DATE = DateUtils.addDays(DATE, 1);
		setPieData(DATE);
	}

	public void setPieData(Date dateToSetUp) {
		this.DATE = dateToSetUp;
		totalProtein = 0;
		totalFat = 0;
		totalCarbs = 0;
		totalCallories = 0;
		final String date = Utils.formatDate(this.DATE, DataBaseUtils.DATE_PATTERN_YYYY_MM_DD);
		List <Ingridient> ingridients = DataBaseUtils.getProductsOnPlate(date);
		if ( null != ingridients ) {
			for ( Ingridient ingridient : ingridients ) {
				totalProtein += ingridient.protein;
				totalFat += ingridient.fat;
				totalCarbs += ingridient.carbohydrates;
				totalCallories += ingridient.kkal;
				Utils.log(String.format("==========prot:%s == carbs:%s == name:%s == fat %s============", ingridient.protein, ingridient.carbohydrates, ingridient.name, ingridient.fat));
			}

			twCurrentDay.setText(Utils.formatDate(dateToSetUp, "EEEE") + " - " + Utils.formatDate(dateToSetUp, "dd") + " of " + Utils.formatDate(dateToSetUp, "MMM"));
			setUpPieChart(totalProtein, totalFat, totalCarbs);
		}

	}

	private void setUpPieChart(double protein, double fat, double carbs) {

		twProtein.setText(String.format("%.2f", protein));
		twFat.setText(String.format("%.2f", fat));
		twCarbs.setText(String.format("%.2f", carbs));
		twCalories.setText(String.valueOf("Calories per day " + totalCallories));

		// set up pie chart parameters
		fat = (fat < 1) ? 1 : fat;
		protein = (protein < 1) ? 1 : protein;
		carbs = (carbs < 1) ? 1 : carbs;

		sliceFat.setValue((int) fat);
		sliceProtein.setValue((int) protein);
		sliceCarbs.setValue((int) carbs);

		// remove existing slices (if exists)
		pieGraphFoodStats.removeSlices();
		// PROTEIN INDEX = 0
		pieGraphFoodStats.addSlice(sliceProtein);
		// CARBS INDEX = 1
		pieGraphFoodStats.addSlice(sliceCarbs);
		// FAT INDEX = 2
		pieGraphFoodStats.addSlice(sliceFat);
	}
}