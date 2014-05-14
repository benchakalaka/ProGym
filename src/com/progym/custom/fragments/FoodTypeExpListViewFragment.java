package com.progym.custom.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.progym.FoodManagmentActivity;
import com.progym.R;
import com.progym.custom.ExpandableListAdapter;

@EFragment ( R.layout.fragment_food_type_list_view ) public class FoodTypeExpListViewFragment extends Fragment {
     @ViewById ExpandableListView    expListView;
     ExpandableListAdapter           listAdapter;
     List <String>                   listDataHeader;
     HashMap <String, List <String>> listDataChild;

     @AfterViews void afterViews() {
          // preparing list data
          prepareListData();

          listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

          // setting list adapter
          expListView.setAdapter(listAdapter);

          // Listview on child click listener
          expListView.setOnChildClickListener(new OnChildClickListener() {

               @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition))
                              .get(childPosition), Toast.LENGTH_SHORT).show();
                    ((FoodManagmentActivity) getActivity()).viewPager.setCurrentItem(FoodManagmentActivity.SPECIFIC_FOOD_SPECIFICATION, true);
                    return false;
               }
          });
     }

     /*
      * Preparing the list data
      */
     private void prepareListData() {
          listDataHeader = new ArrayList <String>();
          listDataChild = new HashMap <String, List <String>>();

          // Adding child data
          listDataHeader.add("Meat");
          listDataHeader.add("Poridge");
          listDataHeader.add("Vegitables");
          listDataHeader.add("Fruits");
          listDataHeader.add("Ready meals");
          listDataHeader.add("Other");

          // Adding child data
          List <String> meat = new ArrayList <String>();
          meat.add("Fish");
          meat.add("Ckicken");
          meat.add("Tourkey");
          meat.add("Beef");
          meat.add("Lamb");
          meat.add("Pork");
          meat.add("Prowns");

          List <String> porridge = new ArrayList <String>();
          porridge.add("Oats");
          porridge.add("Rise");
          porridge.add("Buckweat");
          porridge.add("Manka");
          porridge.add("Pweno");
          porridge.add("Perlovka");

          List <String> vegitables = new ArrayList <String>();
          vegitables.add("Zelenb");
          vegitables.add("Bobu");
          vegitables.add("Kaba4ki");
          vegitables.add("Baklazhanu");
          vegitables.add("Cucumber");
          vegitables.add("Tomatoes");
          vegitables.add("Onion");
          vegitables.add("Salad");
          vegitables.add("Kapysta");
          vegitables.add("Paper");
          vegitables.add("Brocolli");
          vegitables.add("Byr9k");
          vegitables.add("Kartowka");
          vegitables.add("Carrots");
          vegitables.add("Goroh");

          List <String> fruits = new ArrayList <String>();
          fruits.add("Apple");
          fruits.add("Bananas");
          fruits.add("Mango");
          fruits.add("Mandarinu");
          fruits.add("Appelsinu");

          List <String> readyMeals = new ArrayList <String>();
          readyMeals.add("Ready meal 1");
          readyMeals.add("Ready meal 2");

          List <String> other = new ArrayList <String>();
          other.add("Mushroom");

          // Header, Child data
          listDataChild.put(listDataHeader.get(0), meat);
          listDataChild.put(listDataHeader.get(1), porridge);
          listDataChild.put(listDataHeader.get(2), vegitables);
          listDataChild.put(listDataHeader.get(3), fruits);
          listDataChild.put(listDataHeader.get(4), readyMeals);
          listDataChild.put(listDataHeader.get(5), other);
     }
}
