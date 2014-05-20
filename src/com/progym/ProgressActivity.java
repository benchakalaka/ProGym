package com.progym;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.apache.commons.lang3.time.DateUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.progym.constants.GlobalConstants;
import com.progym.custom.fragments.BarFragment;
import com.progym.custom.fragments.BarFragment_;
import com.progym.model.WaterConsumed;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;

@EActivity ( R.layout.progress_activity ) public class ProgressActivity extends ProgymSuperProgressActivity implements IProgressActivity {

     @Click void twDaily() {
          FRAGMENT_TYPE = DAILY;
          calendar = new CaldroidFragment();
          calendar.setCaldroidListener(onDateChangeListener);

          List <WaterConsumed> list = DataBaseUtils.getAllWaterConsumed();

          HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
          double shouldConsumePerDay = DataBaseUtils.getWaterUserShouldConsumePerDay();
          Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD);
          for ( WaterConsumed singleDate : list ) {
               try {

                    int consumedPerDay = DataBaseUtils.getConsumedPerDay(singleDate.date);
                    boolean isDrinkedEnough = consumedPerDay < shouldConsumePerDay;
                    int color;
                    if ( isDrinkedEnough ) {
                         color = R.color.caldroid_yellow;
                    } else {
                         color = R.color.caldroid_sky_blue;
                    }
                    datesAndColour.put(DateUtils.parseDate(singleDate.date, GlobalConstants.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), color);
               } catch (ParseException e) {
                    e.printStackTrace();
                    Utils.log(e.getMessage());
               }
          }
          // highlight dates in calendar with blue color
          calendar.setBackgroundResourceForDates(datesAndColour);
          calendar.show(getSupportFragmentManager(), GlobalConstants.TAG);

     }

     @AfterViews void afterViews() {
          viewPager.setAdapter(new ProgressViewPagerAdapter(getSupportFragmentManager()));
          /*
           * PieSlice slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.green_light));
           * slice.setSelectedColor(resources.getColor(R.color.transparent_orange));
           * slice.setValue(2);
           * piegraph.addSlice(slice);
           * slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.orange));
           * slice.setValue(3);
           * piegraph.addSlice(slice);
           * slice = new PieSlice();
           * slice.setColor(resources.getColor(R.color.purple));
           * slice.setValue(8);
           * piegraph.addSlice(slice);
           * piegraph.setOnSliceClickedListener(new OnSliceClickedListener() {
           * @Override public void onClick(int index) {
           * Toast.makeText(ProgressActivity.this,
           * "Slice " + index + " clicked",
           * Toast.LENGTH_SHORT)
           * .show();
           * }
           * });
           * Line lile = new Line();
           * lile.setUsingDips(true);
           * LinePoint p = new LinePoint();
           * p.setX(0);
           * p.setY(2000);
           * p.setColor(resources.getColor(R.color.red));
           * p.setSelectedColor(resources.getColor(R.color.transparent_blue));
           * lile.addPoint(p);
           * p = new LinePoint();
           * p.setX(800);
           * p.setY(3000);
           * p.setColor(resources.getColor(R.color.blue));
           * lile.addPoint(p);
           * p = new LinePoint();
           * p.setX(1600);
           * p.setY(2200);
           * lile.addPoint(p);
           * p.setColor(resources.getColor(R.color.green));
           * lile.setColor(resources.getColor(R.color.orange));
           * linegraph.setUsingDips(true);
           * linegraph.addLine(lile);
           * linegraph.setRangeY(0, 3300);
           * linegraph.setLineToFill(0);
           * linegraph.setOnPointClickedListener(new OnPointClickedListener() {
           * @Override public void onClick(int lineIndex, int pointIndex) {
           * Toast.makeText(ProgressActivity.this,
           * "Line " + lineIndex + " / Point " + pointIndex + " clicked",
           * Toast.LENGTH_SHORT)
           * .show();
           * }
           * });
           */
     }

     @Override public void updateFragment(int fragmentType) {
          switch (fragmentType) {
               case DAILY:
                    Utils.dateFormat.applyPattern(GlobalConstants.DATE_PATTERN_YYYY_MM_DD);
                    String date = Utils.dateFormat.format(SELECTED_DATE);

                    // Add to expandable list view ready meal date
                    ((ProgressViewPagerAdapter) viewPager.getAdapter()).bargraph.setBarData(date, DataBaseUtils.getWaterUserShouldConsumePerDay(), DataBaseUtils
                              .getConsumedPerDay(date));
                    break;
               case MONTHLY:

                    break;
               case YEARLY:

                    break;
               case RANGE:

                    break;
          }
     }

     /**
      * A simple pager adapter
      */
     private class ProgressViewPagerAdapter extends FragmentStatePagerAdapter {

          public BarFragment bargraph = new BarFragment_();

          public ProgressViewPagerAdapter ( FragmentManager fm ) {
               super(fm);
          }

          @Override public Fragment getItem(int position) {
               Fragment returnFragment = null;
               switch (position) {
                    case 0:
                         returnFragment = bargraph;
                         break;

               /*
                * case 1:
                * returnFragment = new BarFragment_();
                * break;
                */
               }
               return returnFragment;
          }

          @Override public int getCount() {
               return 1;
          }
     }

}
