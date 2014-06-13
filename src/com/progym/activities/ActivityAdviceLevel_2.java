package com.progym.activities;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;
import com.progym.R;
import com.progym.constants.GlobalConstants;
import com.progym.utils.Utils;

@EActivity ( R.layout.activity_advice_level_2 ) public class ActivityAdviceLevel_2 extends Activity {

     @ViewById RelativeLayout                rl1;
     @ViewById RelativeLayout                rl2;
     @ViewById RelativeLayout                rl3;
     @ViewById RelativeLayout                rl4;

     @ViewById ImageView                     iv1;
     @ViewById ImageView                     iv2;
     @ViewById ImageView                     iv3;
     @ViewById ImageView                     iv4;

     @ViewById TextView                      tw1;
     @ViewById TextView                      tw2;
     @ViewById TextView                      tw3;
     @ViewById TextView                      tw4;

     @ViewById TextView                      tw1Description;
     @ViewById TextView                      tw2Description;
     @ViewById TextView                      tw3Description;
     @ViewById TextView                      tw4Description;

     @AnimationRes ( R.anim.fade ) Animation fade;

     String[]                                array1;
     String[]                                array2;
     String[]                                array3;
     String[]                                array4;

     Dialog                                  dialog;

     public static int                       CURRENT_ACTIVITY_TYPE;

     @AfterViews void afterViews() {
          dialog = new Dialog(ActivityAdviceLevel_2.this);
          dialog.setTitle("List of products");
          dialog.setCanceledOnTouchOutside(true);

          int resId = Utils.getImageIdByActiveAdvice(CURRENT_ACTIVITY_TYPE);
          iv1.setBackgroundResource(resId);
          iv2.setBackgroundResource(resId);
          iv3.setBackgroundResource(resId);
          iv4.setBackgroundResource(resId);
          setUpFourTextsAndDialogsArrays();
     }

     private void showDialog(String[] array) {
          ArrayList <String> ingridientsList = new ArrayList <String>();

          for ( String item : array ) {
               ingridientsList.add(item);
          }

          ListView listView = new ListView(ActivityAdviceLevel_2.this);
          IngridientsAdvicedAdapter adapter = new IngridientsAdvicedAdapter(ActivityAdviceLevel_2.this, ingridientsList);
          SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);
          // Assign the ListView to the AnimationAdapter and vice versa
          swingRightInAnimationAdapter.setAbsListView(listView);
          listView.setAdapter(swingRightInAnimationAdapter);
          dialog.setContentView(listView);
          dialog.setCanceledOnTouchOutside(true);

          listView.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                    String ingridientName = adapter.getItemAtPosition(pos).toString();
                    dialog.dismiss();
               }
          });
          dialog.show();
     }

     @Click void rl1() {
          rl1.startAnimation(fade);
          showDialog(array1);
     }

     @Click void rl2() {
          rl2.startAnimation(fade);
          showDialog(array2);
     }

     @Click void rl3() {
          rl3.startAnimation(fade);
          showDialog(array3);
     }

     @Click void rl4() {
          rl4.startAnimation(fade);
          showDialog(array4);
     }

     public void setUpFourTextsAndDialogsArrays() {
          switch (CURRENT_ACTIVITY_TYPE) {
               case GlobalConstants.ADVICE_TYPE.INT_BRAIN_WORK:
                    array1 = getResources().getStringArray(R.array.brain_ingridients_list1);
                    array2 = getResources().getStringArray(R.array.brain_ingridients_list2);
                    array3 = getResources().getStringArray(R.array.brain_ingridients_list3);
                    array4 = getResources().getStringArray(R.array.brain_ingridients_list4);

                    tw1.setText(R.string.brain1);
                    tw2.setText(R.string.brain2);
                    tw3.setText(R.string.brain3);
                    tw4.setText(R.string.brain4);

                    tw1Description.setText(R.string.brain_descr1);
                    tw2Description.setText(R.string.brain_descr2);
                    tw3Description.setText(R.string.brain_descr3);
                    tw4Description.setText(R.string.brain_descr4);
                    break;

               case GlobalConstants.ADVICE_TYPE.INT_ENERGY:
                    array1 = getResources().getStringArray(R.array.energy_ingridients_list1);
                    array2 = getResources().getStringArray(R.array.energy_ingridients_list2);
                    array3 = getResources().getStringArray(R.array.energy_ingridients_list3);
                    array4 = getResources().getStringArray(R.array.energy_ingridients_list4);

                    tw1.setText(R.string.energy1);
                    tw2.setText(R.string.energy2);
                    tw3.setText(R.string.energy3);
                    tw4.setText(R.string.energy4);

                    tw1Description.setText(R.string.energy_descr1);
                    tw2Description.setText(R.string.energy_descr2);
                    tw3Description.setText(R.string.energy_descr3);
                    tw4Description.setText(R.string.energy_descr4);
                    break;

               case GlobalConstants.ADVICE_TYPE.INT_FAT_BURNING:
                    array1 = getResources().getStringArray(R.array.fat_ingridients_list1);
                    array2 = getResources().getStringArray(R.array.fat_ingridients_list2);
                    array3 = getResources().getStringArray(R.array.fat_ingridients_list3);
                    array4 = getResources().getStringArray(R.array.fat_ingridients_list4);

                    tw1.setText(R.string.fat1);
                    tw2.setText(R.string.fat2);
                    tw3.setText(R.string.fat3);
                    tw4.setText(R.string.fat4);

                    tw1Description.setText(R.string.fat_descr1);
                    tw2Description.setText(R.string.fat_descr2);
                    tw3Description.setText(R.string.fat_descr3);
                    tw4Description.setText(R.string.fat_descr4);
                    break;

               case GlobalConstants.ADVICE_TYPE.INT_STRESS_REDUCTION:
                    array1 = getResources().getStringArray(R.array.stress_ingridients_list1);
                    array2 = getResources().getStringArray(R.array.stress_ingridients_list2);
                    array3 = getResources().getStringArray(R.array.stress_ingridients_list3);
                    array4 = getResources().getStringArray(R.array.stress_ingridients_list4);

                    tw1.setText(R.string.stress1);
                    tw2.setText(R.string.stress2);
                    tw3.setText(R.string.stress3);
                    tw4.setText(R.string.stress4);

                    tw1Description.setText(R.string.stress_descr1);
                    tw2Description.setText(R.string.stress_descr2);
                    tw3Description.setText(R.string.stress_descr3);
                    tw4Description.setText(R.string.stress_descr4);
                    break;
          }
     }

     private class IngridientsAdvicedAdapter extends com.nhaarman.listviewanimations.ArrayAdapter <String> {

          private final Context mContext;

          public IngridientsAdvicedAdapter ( Context context , ArrayList <String> items ) {
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
               image.setBackgroundResource(R.drawable.pfc);
               tw.setText(getItem(position));
               return view;
          }
     }

}
