package com.progym.custom.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.progym.R;
import com.progym.activities.ActivityFoodManagment;
import com.progym.constants.GlobalConstants;
import com.progym.custom.ExpandableListAdapter;
import com.progym.model.ReadyMeal;
import com.progym.utils.DataBaseUtils;

@EFragment ( R.layout.fragment_food_type_list_view )
public class FoodTypeExpListViewFragment extends Fragment {
	@ViewById ExpandableListView		expListView;
	ExpandableListAdapter			listAdapter;
	List <String>					listDataHeader;
	HashMap <String, List <String>>	listDataChild;

	List <String>					readyMeals	= new ArrayList <String>();

	public void addToReadyMeal(String date) {
		readyMeals.add(date);
		listAdapter.notifyDataSetChanged();
	}

	public static int	CURRENT_GROUPNAME_CATALOGUE;

	@AfterViews void afterViews() {
		// preparing list data
		prepareListData();
		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(listAdapter);
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				CURRENT_GROUPNAME_CATALOGUE = groupPosition;

				// READY MEALS GROUP HAS BEEN CLICKED
				/*
				 * if ( CURRENT_GROUPNAME_CATALOGUE == 4 ) {
				 * List <Ingridient> listOfProduct =
				 * DataBaseUtils.getProductsOnPlate(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
				 * for ( Ingridient p : listOfProduct ) {
				 * Utils.log(p.productName);
				 * }
				 * }
				 */
				Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
				((ActivityFoodManagment.ViewPagerAdapter) ((ActivityFoodManagment) getActivity()).viewPager.getAdapter()).fragmentIngridient.setGroupAndProduct(CURRENT_GROUPNAME_CATALOGUE, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
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
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_MEAT_CATALOGUE); // 0 - MEAT_CATALOGUE
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_PORRIDGE_CATALOGUE); // 1 - PORRIDGE_CATALOGUE
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_VEGITABLES_CATALOGUE); // 2 - VEGITABLES_CATALOGUE
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_FRUITS_CATALOGUE); // 3 - FRUITS_CATALOGUE
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_READY_MEALS_CATALOGUE); // 4 - READY_MEALS_CATALOGUE
		listDataHeader.add(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_OTHER_CATALOGUE); // 5 OTHER_CATALOGUE

		// Adding child data
		List <String> meat = new ArrayList <String>();
		List <String> porridge = new ArrayList <String>();
		List <String> vegetables = new ArrayList <String>();
		List <String> fruits = new ArrayList <String>();
		List <String> other = new ArrayList <String>();

		// 5 - Column index (name)
		Cursor cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_MEAT_CATALOGUE);
		while ( cursor.moveToNext() ) {
			meat.add(cursor.getString(5));
		}

		// GET ALL PORIDGE_CATALOGUE INGRIDIENTS
		cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_PORRIDGE_CATALOGUE);
		while ( cursor.moveToNext() ) {
			porridge.add(cursor.getString(5));
		}

		// GET ALL VEGITABLES_CATALOGUE INGRIDIENTS
		cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_VEGITABLES_CATALOGUE);
		while ( cursor.moveToNext() ) {
			vegetables.add(cursor.getString(5));
		}

		// GET ALL FRUITS_CATALOGUE INGRIDIENTS
		cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_FRUITS_CATALOGUE);
		while ( cursor.moveToNext() ) {
			fruits.add(cursor.getString(5));
		}

		// GET ALL OTHER_CATALOGUE INGRIDIENTS
		cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_OTHER_CATALOGUE);
		while ( cursor.moveToNext() ) {
			other.add(cursor.getString(5));
		}
		cursor.close();

		// GET ALL READY_MEAL_CATALOGUE INGRIDIENTS
		List <ReadyMeal> meals = ReadyMeal.listAll(ReadyMeal.class);
		for ( ReadyMeal meal : meals ) {
			readyMeals.add(meal.date);
		}

		// Header, Child data
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_MEAT_CATALOGUE), meat);
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_PORRIDGE_CATALOGUE), porridge);
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_VEGITABLES_CATALOGUE), vegetables);
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_FRUITS_CATALOGUE), fruits);
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE), readyMeals);
		listDataChild.put(listDataHeader.get(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_OTHER_CATALOGUE), other);
	}
}
