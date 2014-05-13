package com.progym;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@EActivity ( R.layout.food_managment_activity )
public class FoodManagmentActivity extends FragmentActivity {
	@ViewById ViewPager	viewPager;

	@AfterViews void afterViews() {
		viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter ( FragmentManager fm ) {
			super(fm);
		}

		@Override public Fragment getItem(int position) {
			return new ScreenSlidePageFragment();
		}

		@Override public int getCount() {
			return 5;
		}
	}

	public static class ScreenSlidePageFragment extends Fragment {

		public ScreenSlidePageFragment () {
		}

		@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_select_food_type_buttons, container, false);
			return rootView;
		}
	}

}
