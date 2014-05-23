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

import com.progym.R;
import com.progym.activities.ActivityFoodManagment;
import com.progym.custom.ExpandableListAdapter;
import com.progym.model.Ingridient;
import com.progym.model.ReadyMeal;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_food_type_list_view ) public class FoodTypeExpListViewFragment extends Fragment {
     @ViewById ExpandableListView    expListView;
     ExpandableListAdapter           listAdapter;
     List <String>                   listDataHeader;
     HashMap <String, List <String>> listDataChild;
     
     List <String> readyMeals = new ArrayList <String>();
     
     public void addToReadyMeal (String date){
     	readyMeals.add(date);
     	listAdapter.notifyDataSetChanged();
     }
     
     int FOOD_CATALOGUE;

     @AfterViews void afterViews() {
          // preparing list data
          prepareListData();

          listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

          // setting list adapter
          expListView.setAdapter(listAdapter);

          // Listview on child click listener
          expListView.setOnChildClickListener(new OnChildClickListener() {

               @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               	FOOD_CATALOGUE = groupPosition;
               	
               	// READY MEALS GROUP HAS BEEN CLICKED
               	if (FOOD_CATALOGUE == 4){
          			List<Ingridient> listOfProduct = DataBaseUtils.getProductsOnPlate(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
          			
          			for (Ingridient p : listOfProduct){
          				Utils.log(p.productName);
          			}
               	}
               	
                    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                    ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.SPECIFIC_FOOD_SPECIFICATION, true);
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
          listDataHeader.add("Meat"); // 0 - MEAT_CATALOGUE
          listDataHeader.add("Poridge"); // 1 - PORRIDGE_CATALOGUE
          listDataHeader.add("Vegitables"); // 2 - VEGITABLES_CATALOGUE
          listDataHeader.add("Fruits"); // 3 - FRUITS_CATALOGUE
          listDataHeader.add("Ready meals"); // 4 - READY_MEALS_CATALOGUE
          listDataHeader.add("Other"); // 5 OTHER_CATALOGUE

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

          
          
          List <ReadyMeal> meals = ReadyMeal.listAll(ReadyMeal.class);
          for (ReadyMeal meal :meals){
          	readyMeals.add(meal.date);
          }

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
