package com.progym.custom.fragments;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.text.Editable;
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
import com.progym.model.Ingridient;
import com.progym.utils.DataBaseUtils;
import com.progym.utils.FoodCalculator;
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_ingridient ) public class FragmentIngridient extends Fragment {

     @ViewById ImageView ivFoodImage;
     @ViewById ImageView ivEditIngridient;
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

     // ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);

     public void setGroupAndProduct(int groupName, String ingridient) {
          try {
               this.ingridient = new Ingridient(getActivity());
          } catch (Exception ex) {
               ex.printStackTrace();
               try {
                    Utils.showCustomToast(getActivity(), "Error occured :(", R.drawable.unhappy);
                    getActivity().finish();
               } catch (Exception ee) {
                    ee.printStackTrace();
                    return;
               }
               return;
          }
          Cursor cursor = DataBaseUtils.getByGroupNameAndIngridientName(ingridient);
          if ( null != cursor ) {
               cursor.moveToNext();

               this.ingridient.protein = cursor.getDouble(1);
               this.ingridient.carbohydrates = cursor.getDouble(2);
               this.ingridient.fat = cursor.getDouble(3);
               this.ingridient.kkal = cursor.getInt(4);

               this.ingridient.groupName = Utils.getGroupNameByCataloguePosition(groupName);
               this.ingridient.name = ingridient;

               etProtein.setText(String.format("%s", this.ingridient.protein));
               etFat.setText(String.format("%s", this.ingridient.fat));
               etKkal.setText(String.format("%s", this.ingridient.kkal));
               etCarbs.setText(String.format("%s", this.ingridient.carbohydrates));
               ivFoodImage.setImageResource(Utils.getImageIdByGroupPositionInExpListView(groupName));
               twGroupNameAndIngridientName.setText(Utils.getGroupNameByCataloguePosition(groupName) + "  " + ingridient);

               setUpPieChart((int) this.ingridient.protein, (int) this.ingridient.fat, (int) this.ingridient.carbohydrates);

               cursor.close();
          }
     }

     @Click void ivEditIngridient() {
          AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

          alert.setTitle("Custom ingridient name");
          alert.setMessage("name");

          // Set an EditText view to get user input
          final EditText input = new EditText(getActivity());
          input.setText(ingridient.name);
          alert.setView(input);

          alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int whichButton) {

                    String value = input.getText().toString();
                    if ( !StringUtils.isEmpty(value) ) {
                         ingridient.name = value;
                         twGroupNameAndIngridientName.setText(ingridient.groupName + " " + ingridient.name);
                    } else {
                         Utils.showCustomToast(getActivity(), "Canot be empty", R.drawable.food);
                    }
               }
          });

          alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
               }
          });
          alert.show();
     }

     @Override public void onResume() {
          super.onResume();
          // Set default weight as 100 g
          etWeight.setText("100");
     }

     private void setUpPieChart(int protein, int fat, int carbs) {
          // set up pie chart parameters
          fat = (fat == 0) ? 1 : fat;
          protein = (protein == 0) ? 1 : protein;
          carbs = (carbs == 0) ? 1 : carbs;

          sliceFat.setValue(fat);
          sliceProtein.setValue(protein);
          sliceCarbs.setValue(carbs);

          // remove existing slices (if exists)
          pieGraph.removeSlices();
          // PROTEIN INDEX = 0
          pieGraph.addSlice(sliceProtein);
          // CARBS INDEX = 1
          pieGraph.addSlice(sliceCarbs);
          // FAT INDEX = 2
          pieGraph.addSlice(sliceFat);
     }

     @AfterTextChange void etProtein(Editable text) {
          checkProtCarbsFatValuesAndSetUpChart();
     }

     @AfterTextChange void etFat(Editable text) {
          checkProtCarbsFatValuesAndSetUpChart();
     }

     @AfterTextChange void etCarbs(Editable text) {
          checkProtCarbsFatValuesAndSetUpChart();
     }

     private void checkProtCarbsFatValuesAndSetUpChart() {
          try {
               ingridient.fat = StringUtils.isEmpty(etFat.getText().toString()) ? 0 : Double.valueOf(etFat.getText().toString());
               ingridient.protein = StringUtils.isEmpty(etProtein.getText().toString()) ? 0 : Double.valueOf(etProtein.getText().toString());
               ingridient.carbohydrates = StringUtils.isEmpty(etCarbs.getText().toString()) ? 0 : Double.valueOf(etCarbs.getText().toString());
               setUpPieChart((int) ingridient.protein, (int) ingridient.fat, (int) ingridient.carbohydrates);
          } catch (Exception ex) {
               ex.printStackTrace();
               // Utils.showCustomToast(getActivity(), "Set proper fat value (0-100)g", R.drawable.facebook);
          }
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
                              Toast.makeText(getActivity(), ingridient.name + " contains " + ingridient.protein + " g of protein", Toast.LENGTH_SHORT).show();
                              break;

                         case 1:
                              Toast.makeText(getActivity(), ingridient.name + " contains " + ingridient.carbohydrates + " g of carbohydrates", Toast.LENGTH_SHORT).show();
                              break;

                         case 2:
                              Toast.makeText(getActivity(), ingridient.name + " contains " + ingridient.fat + " g of fat", Toast.LENGTH_SHORT).show();
                              break;
                    }
               }
          });
     }

     @Touch void ivFoodImage(MotionEvent event, View v) {
          if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
               try {
                    ingridient.weight = Double.valueOf(etWeight.getText().toString());
                    ingridient.protein = Double.valueOf(etProtein.getText().toString());
                    ingridient.carbohydrates = Double.valueOf(etCarbs.getText().toString());
                    ingridient.fat = Double.valueOf(etFat.getText().toString());
                    ingridient.kkal = Integer.valueOf(etKkal.getText().toString());
                    
                    // control checking
                    if (ingridient.protein+ingridient.carbohydrates+ingridient.fat > 100){
                    	Utils.showCustomToast(getActivity(), "Protein+Carbs+Fat should be less than 100", R.drawable.warning);
                    	return;
                    }
                    
               } catch (Exception ex) {
                    ex.printStackTrace();
                    // TODO: replace strings to resources!!!
                    // TODO: replace with separate message for each fields
                    Utils.showCustomToast(getActivity(), "Set proper weight,protein,carbs,fat,kkal", R.drawable.warning);
                    return;
               }

               // for debug only, create appropriate int value
               double protein , fat , carbs;
               int kkal;

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
