package com.progym.custom.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.progym.R;
import com.progym.activities.ActivityFoodManagment;

@EFragment ( R.layout.fragment_ingridient )
public class FragmentIngridient extends Fragment {

	@ViewById ImageView	ivShowFoodTypes;
	@ViewById ImageView	ivFoodImage;
	@ViewById PieGraph	pieGraph;

	@Click void ivShowFoodTypes() {
		((ActivityFoodManagment) getActivity()).viewPager.setCurrentItem(ActivityFoodManagment.EXPANDABLE_LISTVIEW_FOOD_TYPES, true);
	}

	@AfterViews void afterViews() {
		final Resources resources = getActivity().getResources();
		PieSlice slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.green_light));
		slice.setSelectedColor(resources.getColor(R.color.transparent_orange));
		slice.setValue(2);
		pieGraph.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.orange));
		slice.setValue(3);
		pieGraph.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.purple));
		slice.setValue(8);
		pieGraph.addSlice(slice);
		pieGraph.setOnSliceClickedListener(new OnSliceClickedListener() {
			@Override public void onClick(int index) {
				pieGraph.getSlice(index).setValue(pieGraph.getSlice(index).getValue() + 1);
				Toast.makeText(getActivity(), "Slice " + index + " clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Touch void ivFoodImage(MotionEvent event, View v) {
		v.setTag("tag");
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
