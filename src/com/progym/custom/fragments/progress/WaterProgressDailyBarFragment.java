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

import java.util.ArrayList;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.progym.R;

@EFragment ( R.layout.fragment_bargraph ) public class WaterProgressDailyBarFragment extends Fragment {

     @ViewById BarGraph bargraph;

     public void setBarData(final String date, final double shouldDrink, final int consumed) {
          final Resources resources = getResources();
          ArrayList <Bar> aBars = new ArrayList <Bar>();

          Bar bar = new Bar();
          bar.setColor(resources.getColor(R.color.green_light));
          bar.setSelectedColor(resources.getColor(R.color.caldroid_white));
          bar.setName("Should Drink");
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
                         Toast.makeText(getActivity(), "You shoud drink " + String.format("%.2f L per day", (shouldDrink / 1000f)), Toast.LENGTH_SHORT).show();
                    }

                    if ( 1 == index ) {
                         Toast.makeText(getActivity(), date + " you consumed " + String.format("%.2f L", (consumed / 1000f)), Toast.LENGTH_SHORT).show();
                    }

               }
          });

     }

}
