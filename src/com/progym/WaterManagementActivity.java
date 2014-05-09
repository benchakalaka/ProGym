package com.progym;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.ImageView;

@EActivity ( R.layout.water_management_activity )
public class WaterManagementActivity extends Activity {
	@ViewById ImageView	ivBottl;
	@ViewById ImageView	ivBottl2;

	@LongClick boolean ivBottl(View v) {
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData("slywaju", mimeTypes, new ClipData.Item("slywaju"));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@LongClick boolean ivBottl2(View v) {
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData("slywaju", mimeTypes, new ClipData.Item("slywaju"));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

}
