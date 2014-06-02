package com.progym.custom.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.progym.R;
import com.progym.activities.ActivityFoodManagment;
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.FoodCalculator;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_ingridient ) public class FragmentIngridient extends Fragment {

     @ViewById ImageView ivShowFoodTypes;
     @ViewById ImageView ivFoodImage;
     @ViewById PieGraph  pieGraph;
     @ViewById EditText  etWeight;

     @ViewById TextView  twGroupNameAndIngridientName;

     @ViewById EditText  etProtein;
     @ViewById EditText  etFat;
     @ViewById EditText  etKkal;
     @ViewById EditText  etCarbs;

     Ingridient          ingridient;

     // protein, carbs, fat pie slices
     PieSlice            sliceProtein = new PieSlice();
     PieSlice            sliceFat     = new PieSlice();
     PieSlice            sliceCarbs   = new PieSlice();

     public void setGroupAndProduct(int groupName, String ingridient) {
          this.ingridient = new Ingridient(getActivity());

          Cursor cursor = DataBaseUtils.getByGroupNameAndIngridientName(ingridient);
          if ( null != cursor ) {
               cursor.moveToNext();

               this.ingridient.protein = cursor.getInt(1);
               this.ingridient.fat = cursor.getInt(2);
               this.ingridient.kkal = cursor.getInt(3);
               this.ingridient.carbohydrates = cursor.getInt(4);
               this.ingridient.groupName = Utils.getGroupNameByGroupPositionInExpListView(groupName);
               this.ingridient.name = ingridient;

               etProtein.setText(String.format("%s", this.ingridient.protein));
               etFat.setText(String.format("%s", this.ingridient.fat));
               etKkal.setText(String.format("%s", this.ingridient.kkal));
               etCarbs.setText(String.format("%s", this.ingridient.carbohydrates));
               ivFoodImage.setImageResource(Utils.getImageIdByGroupPositionInExpListView(groupName));
               twGroupNameAndIngridientName.setText(Utils.getGroupNameByGroupPositionInExpListView(groupName) + "  " + ingridient);

               setUpPieChart(this.ingridient.protein, this.ingridient.fat, this.ingridient.carbohydrates);

               cursor.close();
          }
     }

     @Override public void onResume() {
          super.onResume();
          // Set default weight as 100 g
          etWeight.setText("100");
     }

     private void setUpPieChart(int protein, int fat, int carbs) {
          // set up pie chart parameters
          sliceFat.setValue(fat);
          sliceProtein.setValue(protein);
          sliceCarbs.setValue(carbs);

          // remove existing slices (if exists)
          pieGraph.removeSlices();
          // PROTEIN INDEX = 0
          if ( 0 != protein ) {
               pieGraph.addSlice(sliceProtein);
          }
          // CARBS INDEX = 1
          if ( 0 != carbs ) {
               pieGraph.addSlice(sliceCarbs);
          }
          // FAT INDEX = 2
          if ( 0 != fat ) {
               pieGraph.addSlice(sliceFat);
          }
     }

     /*
      * @AfterTextChange void etProtein(Editable text) {
      * checkProtCarbsFatValuesAndSetUpChart();
      * }
      * @AfterTextChange void etFat(Editable text) {
      * checkProtCarbsFatValuesAndSetUpChart();
      * }
      * @AfterTextChange void etCarbs(Editable text) {
      * checkProtCarbsFatValuesAndSetUpChart();
      * }
      */

     private void checkProtCarbsFatValuesAndSetUpChart() {
          try {
               ingridient.fat = StringUtils.isEmpty(etFat.getText().toString()) ? 0 : Integer.valueOf(etFat.getText().toString());
               ingridient.protein = StringUtils.isEmpty(etProtein.getText().toString()) ? 0 : Integer.valueOf(etProtein.getText().toString());
               ingridient.carbohydrates = StringUtils.isEmpty(etCarbs.getText().toString()) ? 0 : Integer.valueOf(etCarbs.getText().toString());
               setUpPieChart(ingridient.protein, ingridient.fat, ingridient.carbohydrates);
          } catch (Exception ex) {
               ex.printStackTrace();
               Utils.showCustomToast(getActivity(), "Set proper fat value (0-100)g", R.drawable.facebook);
          }
     }

     @Click void ivShowFoodTypes() {
          ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);
     }

     @AfterViews void afterViews() {
          final Resources resources = getActivity().getResources();

          sliceProtein.setColor(resources.getColor(R.color.green));
          sliceFat.setColor(resources.getColor(R.color.orange));
          sliceCarbs.setColor(resources.getColor(R.color.purple));

          sliceProtein.setSelectedColor(resources.getColor(R.color.caldroid_white));
          sliceFat.setSelectedColor(resources.getColor(R.color.caldroid_white));
          sliceCarbs.setSelectedColor(resources.getColor(R.color.caldroid_white));

          pieGraph.setOnSliceClickedListener(new OnSliceClickedListener() {
               @Override public void onClick(int index) {
                    // pieGraph.getSlice(index).setValue(pieGraph.getSlice(index).getValue() + 1);

                    // 0 -> prot | 1 -> carbs | 2 -> fat
                    switch (index) {
                         case 0:
                              Toast.makeText(getActivity(), ingridient.name + " contains " + pieGraph.getSlice(index).getValue() + " g of protein", Toast.LENGTH_SHORT).show();
                              break;

                         case 1:
                              Toast.makeText(getActivity(), ingridient.name + " contains " + pieGraph.getSlice(index).getValue() + " g of carbohydrates", Toast.LENGTH_SHORT).show();
                              break;

                         case 2:
                              Toast.makeText(getActivity(), ingridient.name + " contains " + pieGraph.getSlice(index).getValue() + " g of fat", Toast.LENGTH_SHORT).show();
                              break;
                    }
               }
          });
     }

     @Touch void ivFoodImage(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               try {
                    ingridient.weight = Integer.valueOf(etWeight.getText().toString());
                    ingridient.protein = Integer.valueOf(etProtein.getText().toString());
                    ingridient.carbohydrates = Integer.valueOf(etCarbs.getText().toString());
                    ingridient.fat = Integer.valueOf(etFat.getText().toString());
                    ingridient.kkal = Integer.valueOf(etKkal.getText().toString());
               } catch (Exception ex) {
                    ex.printStackTrace();
                    // TODO: replace strings to resources!!!
                    // TODO: replace with separate message for each fields
                    Utils.showCustomToast(getActivity(), "Set proper weight,protein,carbs,fat,kkal", R.drawable.facebook);
                    return;
               }

               // for debug only, create appropriate int value
               int protein , fat , carbs , kkal;

               protein = FoodCalculator.getProtein(ingridient.protein, ingridient.weight);
               fat = FoodCalculator.getFat(ingridient.fat, ingridient.weight);
               carbs = FoodCalculator.getCarbs(ingridient.carbohydrates, ingridient.weight);
               kkal = FoodCalculator.getKkal(ingridient.kkal, ingridient.weight);

               // 0 - element name of product
               ClipData dragData = new ClipData(ingridient.name, new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, new ClipData.Item(ingridient.name));
               // 1 - element weight of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(ingridient.weight)));
               // 2 - element groupName of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(ingridient.groupName)));
               // 3 - element protein of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(protein)));
               // 4 - element carbs of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(carbs)));
               // 5 - element fat of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(fat)));
               // 6 - element kkal of prodcut
               dragData.addItem(new ClipData.Item(String.valueOf(kkal)));

               View.DragShadowBuilder shadow = new DragShadowBuilder(v);
               v.startDrag(dragData, shadow, null, 0);
          }
     }

}
