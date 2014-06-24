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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import com.progym.R;
import com.progym.activities.ActivityFoodManagment;
import com.progym.constants.GlobalConstants;
import com.progym.custom.OneProductOnPlateView;
import com.progym.custom.OneProductOnPlateView_;
import com.progym.model.ReadyIngridient;
import com.progym.model.ReadyMeal;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_food_type ) public class FoodTypeFragment extends Fragment {
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
          setUpIngridientView(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_SWEETS_MILK_CATALOGUE);
     }

     @Click void btnReadyMeals() {
          showReadyMealIngridients(GlobalConstants.GROUP_INGRIDIENT_NAME.INT_READY_MEALS_CATALOGUE);
     }

     private void showReadyMealIngridients(int catalogue) {
          CURRENT_GROUPNAME_CATALOGUE = catalogue;
          String catalogueName = Utils.getGroupNameByCataloguePosition(CURRENT_GROUPNAME_CATALOGUE);
          ArrayList <String> ingridientsList = (ArrayList <String>) Utils.getIngridientsListByCatalogue(CURRENT_GROUPNAME_CATALOGUE);

          if ( ingridientsList.isEmpty() ) {
               Utils.showCustomToast(getActivity(), R.string.there_is_no_ready_meal_found, R.drawable.unhappy);
               return;
          }

          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(catalogueName);
          ListView listView = new ListView(getActivity());
          MyListAdapter adapter = new MyListAdapter(getActivity(), ingridientsList);
          SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);
          // Assign the ListView to the AnimationAdapter and vice versa
          swingRightInAnimationAdapter.setAbsListView(listView);
          listView.setAdapter(swingRightInAnimationAdapter);
          builder.setView(listView);
          final Dialog dialog = builder.create();

          dialog.setCanceledOnTouchOutside(true);

          listView.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(final AdapterView <?> adapter, View view, final int pos, long arg3) {

                    dialog.setContentView(R.layout.dialog_list_of_meals);
                    dialog.setTitle(R.string.ingridients_list);
                    LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.llIngridientsListToAdd);

                    final String plateDate = adapter.getItemAtPosition(pos).toString();

                    final List <ReadyIngridient> ingridients = DataBaseUtils.getReadyIngridients(plateDate);

                    for ( ReadyIngridient i : ingridients ) {
                         OneProductOnPlateView v = OneProductOnPlateView_.build(getActivity());
                         v.ivProduct.setBackgroundResource(Utils.getImageIdByGroupName(i.groupName));
                         v.twProductDescription.setText(i.name + " : " + String.format("%.2f", i.weight) + "g (" + i.kkal + "kkal)");
                         ll.addView(v);
                    }

                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                    Button btnAddToReadyMeal = (Button) dialog.findViewById(R.id.btnAddReadyMealToCurrPlate);

                    btnAddToReadyMeal.setOnClickListener(new OnClickListener() {

                         @Override public void onClick(View v) {
                              if ( !ingridients.isEmpty() ) {
                                   ((com.progym.activities.ActivityFoodManagment) getActivity()).addReadyMealToCurrentPlate(ingridients);
                              }

                              dialog.dismiss();
                         }
                    });

                    btnCancel.setOnClickListener(new OnClickListener() {

                         @Override public void onClick(View v) {
                              dialog.dismiss();
                         }
                    });

                    dialog.show();

               }
          });
          dialog.show();
     }

     private void setUpIngridientView(int catalogue) {
          CURRENT_GROUPNAME_CATALOGUE = catalogue;
          String catalogueName = Utils.getGroupNameByCataloguePosition(CURRENT_GROUPNAME_CATALOGUE);
          ArrayList <String> ingridientsList = (ArrayList <String>) Utils.getIngridientsListByCatalogue(CURRENT_GROUPNAME_CATALOGUE);
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(catalogueName);
          ListView listView = new ListView(getActivity());
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
          cursor = DataBaseUtils.getByGroupName(GlobalConstants.GROUP_INGRIDIENT_NAME.STR_SWEETS_MILK_CATALOGUE);
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
