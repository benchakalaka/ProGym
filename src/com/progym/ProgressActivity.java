package com.progym;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.res.Resources;
import android.widget.Toast;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;

@EActivity ( R.layout.progress_managment_activity ) public class ProgressActivity extends ProgymSuperActivity {

     @ViewById BarGraph  bargraph;
     @ViewById PieGraph  piegraph;
     @ViewById LineGraph linegraph;

     @Override public void displaySelectedDate() {

     }

     @AfterViews void afterViews() {
          final Resources resources = getResources();
          ArrayList <Bar> aBars = new ArrayList <Bar>();

          Bar bar = new Bar();
          bar.setColor(resources.getColor(R.color.green_light));
          bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
          bar.setName("Test1");
          bar.setValue(1000);
          bar.setValueString("$1,000");
          aBars.add(bar);

          bar = new Bar();
          bar.setColor(resources.getColor(R.color.orange));
          bar.setName("Test2");
          bar.setValue(2000);
          bar.setValueString("$2,000");
          aBars.add(bar);

          bar = new Bar();
          bar.setColor(resources.getColor(R.color.caldroid_white));
          bar.setName("Test3");
          bar.setValue(10000);
          bar.setValueString("$10,000");
          aBars.add(bar);

          bargraph.setBars(aBars);

          bargraph.setOnBarClickedListener(new OnBarClickedListener() {

               @Override public void onClick(int index) {
                    Toast.makeText(ProgressActivity.this,
                              "Bar " + index + " clicked",
                              Toast.LENGTH_SHORT)
                              .show();
               }
          });

          PieSlice slice = new PieSlice();
          slice.setColor(resources.getColor(R.color.green_light));
          slice.setSelectedColor(resources.getColor(R.color.transparent_orange));
          slice.setValue(2);
          piegraph.addSlice(slice);

          slice = new PieSlice();
          slice.setColor(resources.getColor(R.color.orange));
          slice.setValue(3);
          piegraph.addSlice(slice);

          slice = new PieSlice();
          slice.setColor(resources.getColor(R.color.purple));
          slice.setValue(8);
          piegraph.addSlice(slice);

          piegraph.setOnSliceClickedListener(new OnSliceClickedListener() {

               @Override public void onClick(int index) {
                    Toast.makeText(ProgressActivity.this,
                              "Slice " + index + " clicked",
                              Toast.LENGTH_SHORT)
                              .show();
               }
          });

          Line lile = new Line();
          lile.setUsingDips(true);
          LinePoint p = new LinePoint();
          p.setX(0);
          p.setY(2000);
          p.setColor(resources.getColor(R.color.red));
          p.setSelectedColor(resources.getColor(R.color.transparent_blue));
          lile.addPoint(p);
          p = new LinePoint();
          p.setX(800);
          p.setY(3000);
          p.setColor(resources.getColor(R.color.blue));
          lile.addPoint(p);
          p = new LinePoint();
          p.setX(1600);
          p.setY(2200);
          lile.addPoint(p);
          p.setColor(resources.getColor(R.color.green));
          lile.setColor(resources.getColor(R.color.orange));

          linegraph.setUsingDips(true);
          linegraph.addLine(lile);
          linegraph.setRangeY(0, 3300);
          linegraph.setLineToFill(0);

          linegraph.setOnPointClickedListener(new OnPointClickedListener() {

               @Override public void onClick(int lineIndex, int pointIndex) {
                    Toast.makeText(ProgressActivity.this,
                              "Line " + lineIndex + " / Point " + pointIndex + " clicked",
                              Toast.LENGTH_SHORT)
                              .show();
               }
          });

     }
}
