package com.progym.custom.fragments;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import com.progym.R;
import com.progym.activities.ActivityFoodManagment;
import com.progym.constants.GlobalConstants;
import com.progym.model.ReadyMeal;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_food_type ) public class FoodTypeExpListViewFragment extends Fragment {
     List <String>         readyMeals = new ArrayList <String>();

     @ViewById ImageButton btnReadyMeals;
     @ViewById ImageButton btnMeat;
     @ViewById ImageButton btnFruits;
     @ViewById ImageButton btnOther;
     @ViewById ImageButton btnVegitables;
     @ViewById ImageButton btnPorridge;

     public static int     CURRENT_GROUPNAME_CATALOGUE;

     public void addToReadyMeal(String date) {
          readyMeals.add(date);
          // listAdapter.notifyDataSetChanged();
     }

     @Click void btnMeat() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_MEAT_CATALOGUE);
     }

     @Click void btnFruits() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_FRUITS_CATALOGUE);
     }

     @Click void btnVegitables() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_VEGITABLES_CATALOGUE);
     }

     @Click void btnPorridge() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_PORRIDGE_CATALOGUE);
     }

     @Click void btnOther() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_OTHER_CATALOGUE);
     }

     @Click void btnReadyMeals() {
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE);
     }

     private void setUpIngridientView(int catalogue) {
          CURRENT_GROUPNAME_CATALOGUE = catalogue;
          String catalogueName = Utils.getGroupNameByCataloguePosition(CURRENT_GROUPNAME_CATALOGUE);
          ArrayList <String> ingridientsList = (ArrayList <String>) Utils.getIngridientsListByCatalogue(CURRENT_GROUPNAME_CATALOGUE);
          // String[] stringArray = new String[ingridientsList.size()];
          // for ( int i = 0; i < ingridientsList.size(); i++ ) {
          // stringArray[i] = ingridientsList.get(i).toString();
          // }

          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(catalogueName);

          ListView listView = new ListView(getActivity());
          // ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
          MyListAdapter adapter = new MyListAdapter(getActivity(), ingridientsList);

          SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);

          // Assign the ListView to the AnimationAdapter and vice versa
          swingRightInAnimationAdapter.setAbsListView(listView);

          listView.setAdapter(swingRightInAnimationAdapter);
          builder.setView(listView);
          final Dialog dialog = builder.create();
          dialog.setCanceledOnTouchOutside(true);

          listView.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                    String ingridientName = adapter.getItemAtPosition(pos).toString();
                    ((ActivityFoodManagment.ViewPagerAdapter) ((ActivityFoodManagment) getActivity()).viewPager.getAdapter()).fragmentIngridient.setGroupAndProduct(CURRENT_GROUPNAME_CATALOGUE, ingridientName);
                    ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.SPECIFIC_FOOD_SPECIFICATION, true);
                    dialog.dismiss();
               }
          });

          dialog.show();
     }

     private class MyListAdapter extends com.nhaarman.listviewanimations.ArrayAdapter <String> {

          private final Context mContext;

          public MyListAdapter ( Context context , ArrayList <String> items ) {
               super(items);
               mContext = context;
          }

          @Override public View getView(int position, View convertView, ViewGroup parent) {
               View view = convertView;
               if ( view == null ) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.list_group, parent, false);
               }
               TextView tw = (TextView) view.findViewById(R.id.text);
               ImageView image = (ImageView) view.findViewById(R.id.image);
               image.setBackgroundResource(Utils.getImageIdByGroupPositionInExpListView(CURRENT_GROUPNAME_CATALOGUE));
               tw.setText(getItem(position));
               return view;
          }
     }

     @AfterViews void afterViews() {

          // if any of the ingridient list is empty obtain data from database
          if ( GlobalConstants.INGRIDIENTS.MEAT_LIST.isEmpty() ) {
               prepareListData();
          }

          // preparing list data

          /*
           * listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
           * // setting list adapter
           * expListView.setAdapter(listAdapter);
           * // Listview on child click listener
           * expListView.setOnChildClickListener(new OnChildClickListener() {
           * @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
           * CURRENT_GROUPNAME_CATALOGUE = groupPosition;
           * // READY MEALS GROUP HAS BEEN CLICKED
           * /*
           * if ( CURRENT_GROUPNAME_CATALOGUE == 4 ) {
           * List <Ingridient> listOfProduct =
           * DataBaseUtils.getProductsOnPlate(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
           * for ( Ingridient p : listOfProduct ) {
           * Utils.log(p.productName);
           * }
           * }
           * Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " +
           * listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
           * ((ActivityFoodManagment.ViewPagerAdapter) ((ActivityFoodManagment)
           * getActivity()).viewPager.getAdapter()).fragmentIngridient.setGroupAndProduct(CURRENT_GROUPNAME_CATALOGUE,
           * listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
           * ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.SPECIFIC_FOOD_SPECIFICATION, true);
           * return false;
           * }
           * });
           */
     }

     /*
      * Preparing the list data
      */
     @Background void prepareListData() {
          // 5 - Column index (name)
          Cursor cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_MEAT_CATALOGUE);
          while ( cursor.moveToNext() ) {
               GlobalConstants.INGRIDIENTS.MEAT_LIST.add(cursor.getString(5));
          }

          // GET ALL PORIDGE_CATALOGUE INGRIDIENTS
          cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_PORRIDGE_CATALOGUE);
          while ( cursor.moveToNext() ) {
               GlobalConstants.INGRIDIENTS.PORRIDGE_LIST.add(cursor.getString(5));
          }

          // GET ALL VEGITABLES_CATALOGUE INGRIDIENTS
          cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_VEGITABLES_CATALOGUE);
          while ( cursor.moveToNext() ) {
               GlobalConstants.INGRIDIENTS.VEGITABLES_LIST.add(cursor.getString(5));
          }

          // GET ALL FRUITS_CATALOGUE INGRIDIENTS
          cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_FRUITS_CATALOGUE);
          while ( cursor.moveToNext() ) {
               GlobalConstants.INGRIDIENTS.FRUITS_LIST.add(cursor.getString(5));
          }

          // GET ALL OTHER_CATALOGUE INGRIDIENTS
          cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_OTHER_CATALOGUE);
          while ( cursor.moveToNext() ) {
               GlobalConstants.INGRIDIENTS.OTHER_LIST.add(cursor.getString(5));
          }
          cursor.close();

          // GET ALL READY_MEAL_CATALOGUE INGRIDIENTS
          List <ReadyMeal> meals = ReadyMeal.listAll(ReadyMeal.class);
          if ( !GlobalConstants.INGRIDIENTS.READY_MEALS.isEmpty() ) {
               GlobalConstants.INGRIDIENTS.READY_MEALS.clear();
          }
          for ( ReadyMeal meal : meals ) {
               GlobalConstants.INGRIDIENTS.READY_MEALS.add(meal.date);
          }
     }
}
