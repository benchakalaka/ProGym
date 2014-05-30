package com.progym.custom.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
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
import com.progym.utils.Utils;

@EFragment ( R.layout.fragment_ingridient ) public class FragmentIngridient extends Fragment {

     @ViewById ImageView ivShowFoodTypes;
     @ViewById ImageView ivFoodImage;
     @ViewById PieGraph  pieGraph;

     @ViewById TextView  twGroupName;
     @ViewById TextView  twIngridientName;

     @ViewById TextView  twProtein;
     @ViewById TextView  twFat;
     @ViewById TextView  twKkal;
     @ViewById TextView  twCarbs;

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

               twProtein.setText("Protein : " + this.ingridient.protein);
               twFat.setText("Fat : " + this.ingridient.fat);
               twKkal.setText("Kkal : " + this.ingridient.kkal);
               twCarbs.setText("Carbohydrates : " + this.ingridient.carbohydrates);
               ivFoodImage.setImageResource(Utils.getImageIdByGroupPositionInExpListView(groupName));
               twGroupName.setText("Product type : " + Utils.getGroupNameByGroupPositionInExpListView(groupName));
               twIngridientName.setText("Product name : " + ingridient);

               // set up pie chart parameters
               sliceFat.setValue(this.ingridient.fat);
               sliceProtein.setValue(this.ingridient.protein);
               sliceCarbs.setValue(this.ingridient.carbohydrates);

               pieGraph.removeSlices();

               // PROTEIN INDEX = 0
               pieGraph.addSlice(sliceProtein);
               // CARBS INDEX = 1
               pieGraph.addSlice(sliceCarbs);
               // FAT INDEX = 2
               pieGraph.addSlice(sliceFat);

               cursor.close();
          }
     }

     @Click void ivShowFoodTypes() {
          ((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);
     }

     @AfterViews void afterViews() {
          final Resources resources = getActivity().getResources();

          sliceProtein.setColor(resources.getColor(R.color.green_light));
          sliceProtein.setSelectedColor(resources.getColor(R.color.transparent_orange));
          sliceFat.setColor(resources.getColor(R.color.orange));
          sliceCarbs.setColor(resources.getColor(R.color.purple));

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
          // SET TAG OF INGRIDIENT AS A INGRIDIENT NAME (Chicken, Banana etc...)
          v.setTag(ingridient.name);
          dragView(v);
     }

     private void dragView(View v) {
          String tag = v.getTag().toString();
          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
          ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
          v.startDrag(dragData, shadow, null, 0);
     }
}
