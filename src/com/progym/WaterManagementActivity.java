package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

import com.progym.model.User;
import com.progym.utils.GlobalConstants;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.media.MediaPlayer;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@EActivity ( R.layout.water_management_activity )
public class WaterManagementActivity extends Activity {
	@ViewById ImageView		ivGlass250ML;
	@ViewById ImageView		ivBottle500ML;
	@ViewById ImageView		ivBottle1L;
	@ViewById ImageView		ivBottle2L;
	@ViewById ImageView		ivCustomWaterVolume;
	@ViewById LinearLayout	llAlreadyConsumedWaterList;
	@ViewById LinearLayout	llRightPanelBody;
	@ViewById TextView		twPercentWatterCompletted; 

	private MediaPlayer		mediaPlayer;

	@Override protected void onDestroy() {
		if ( mediaPlayer != null ) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@AfterViews void afterViews() {
		// init player
		mediaPlayer = MediaPlayer.create(WaterManagementActivity.this, R.raw.pouring_liquid);

		ivGlass250ML.setTag(GlobalConstants.WATER_VOLUMES.GLASS_250ML);
		ivBottle500ML.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML);
		ivBottle1L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_1L);
		ivBottle2L.setTag(GlobalConstants.WATER_VOLUMES.BOTTLE_2L);
		// ivCustomWaterVolume.setTag(GlobalConstants.WATER_VOLUMES.);

		llRightPanelBody.setOnDragListener(new OnDragListener() {

			@Override public boolean onDrag(View v, final DragEvent event) {
				switch (event.getAction()) {
					case DragEvent.ACTION_DROP:
						if ( null != mediaPlayer ) mediaPlayer.start();
						final String tag = event.getClipData().getDescription().getLabel().toString();

						LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
						ImageView iv = new ImageView(getApplicationContext());
						if ( tag.equals(GlobalConstants.WATER_VOLUMES.GLASS_250ML) ) {
							iv.setBackgroundResource(R.drawable.glass);
						}

						if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_500ML) ) {
							iv.setBackgroundResource(R.drawable.bottle);
						}

						if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_1L) ) {
							iv.setBackgroundResource(R.drawable.bottle2);
						}

						if ( tag.equals(GlobalConstants.WATER_VOLUMES.BOTTLE_2L) ) {
							iv.setBackgroundResource(R.drawable.body);
						}

						iv.setLayoutParams(params);
						llAlreadyConsumedWaterList.addView(iv);
						
						User u = User.findById(User.class, Long.valueOf(1));
						double needToDrink = (u.weight / 30);
						twPercentWatterCompletted.setText(String.valueOf((needToDrink * 1000 ) / 250));
						
						break;
				}
				return true;
			}
		});

	}

	@LongClick boolean ivGlass250ML(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@LongClick boolean ivBottle500ML(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@LongClick boolean ivBottle1L(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@LongClick boolean ivBottle2L(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@LongClick boolean ivCustomWaterVolume(View v) {
		String tag = v.getTag().toString();
		String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
		ClipData dragData = new ClipData(tag, mimeTypes, new ClipData.Item(tag));
		View.DragShadowBuilder shadow = new DragShadowBuilder((ImageView) v);
		v.startDrag(dragData, shadow, null, 0);
		return true;
	}

	@Click void ivGlass250ML() {
		Toast.makeText(getApplicationContext(), "250 ml", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle500ML() {
		Toast.makeText(getApplicationContext(), "500 ml", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle1L() {
		Toast.makeText(getApplicationContext(), "1 L", Toast.LENGTH_SHORT).show();
	}

	@Click void ivBottle2L() {
		Toast.makeText(getApplicationContext(), "2 L", Toast.LENGTH_SHORT).show();
	}

	@Click void ivCustomWaterVolume() {
		Toast.makeText(getApplicationContext(), "10000 L", Toast.LENGTH_SHORT).show();
	}

}
