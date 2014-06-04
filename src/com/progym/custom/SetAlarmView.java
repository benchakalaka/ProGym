package com.progym.custom;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;

import com.progym.R;
import com.progym.utils.AppSharedPreferences_;

@EViewGroup ( R.layout.dialog_alarm )
public class SetAlarmView extends ScrollView {

	@Pref AppSharedPreferences_	appPreff;

	@ViewById CheckBox			cb630;
	@ViewById CheckBox			cb7;
	@ViewById CheckBox			cb730;
	@ViewById CheckBox			cb8;
	@ViewById CheckBox			cb830;
	@ViewById CheckBox			cb9;
	@ViewById CheckBox			cb930;
	@ViewById CheckBox			cb10;
	@ViewById CheckBox			cb1030;
	@ViewById CheckBox			cb11;
	@ViewById CheckBox			cb1130;
	@ViewById CheckBox			cb12;
	@ViewById CheckBox			cb1230;
	@ViewById CheckBox			cb13;
	@ViewById CheckBox			cb1330;
	@ViewById CheckBox			cb14;
	@ViewById CheckBox			cb1430;
	@ViewById CheckBox			cb15;
	@ViewById CheckBox			cb1530;
	@ViewById CheckBox			cb16;
	@ViewById CheckBox			cb1630;
	@ViewById CheckBox			cb17;
	@ViewById CheckBox			cb1730;
	@ViewById CheckBox			cb18;
	@ViewById CheckBox			cb1830;
	@ViewById CheckBox			cb19;
	@ViewById CheckBox			cb1930;
	@ViewById CheckBox			cb20;
	@ViewById CheckBox			cb2030;
	@ViewById CheckBox			cb21;
	@ViewById CheckBox			cb2130;
	@ViewById CheckBox			cb22;
	@ViewById CheckBox			cb2230;
	@ViewById CheckBox			cb23;
	@ViewById CheckBox			cb2330;

	@AfterViews void afterViews() {
		cb630.setChecked(appPreff.cb630().getOr("").equals("") ? false : true);
		cb7.setChecked(appPreff.cb7().getOr("").equals("") ? false : true);
		cb730.setChecked(appPreff.cb730().getOr("").equals("") ? false : true);
		cb8.setChecked(appPreff.cb8().getOr("").equals("") ? false : true);
		cb830.setChecked(appPreff.cb830().getOr("").equals("") ? false : true);
		cb9.setChecked(appPreff.cb9().getOr("").equals("") ? false : true);
		cb930.setChecked(appPreff.cb930().getOr("").equals("") ? false : true);
		cb10.setChecked(appPreff.cb10().getOr("").equals("") ? false : true);
		cb1030.setChecked(appPreff.cb1030().getOr("").equals("") ? false : true);
		cb11.setChecked(appPreff.cb11().getOr("").equals("") ? false : true);
		cb1130.setChecked(appPreff.cb1130().getOr("").equals("") ? false : true);
		cb12.setChecked(appPreff.cb12().getOr("").equals("") ? false : true);
		cb1230.setChecked(appPreff.cb1230().getOr("").equals("") ? false : true);
		cb13.setChecked(appPreff.cb13().getOr("").equals("") ? false : true);
		cb1330.setChecked(appPreff.cb1330().getOr("").equals("") ? false : true);
		cb14.setChecked(appPreff.cb14().getOr("").equals("") ? false : true);
		cb1430.setChecked(appPreff.cb1430().getOr("").equals("") ? false : true);
		cb15.setChecked(appPreff.cb15().getOr("").equals("") ? false : true);
		cb1530.setChecked(appPreff.cb1530().getOr("").equals("") ? false : true);
		cb16.setChecked(appPreff.cb16().getOr("").equals("") ? false : true);
		cb1630.setChecked(appPreff.cb1630().getOr("").equals("") ? false : true);
		cb17.setChecked(appPreff.cb17().getOr("").equals("") ? false : true);
		cb1730.setChecked(appPreff.cb1730().getOr("").equals("") ? false : true);
		cb18.setChecked(appPreff.cb18().getOr("").equals("") ? false : true);
		cb1830.setChecked(appPreff.cb1830().getOr("").equals("") ? false : true);
		cb19.setChecked(appPreff.cb19().getOr("").equals("") ? false : true);
		cb1930.setChecked(appPreff.cb1930().getOr("").equals("") ? false : true);
		cb20.setChecked(appPreff.cb20().getOr("").equals("") ? false : true);
		cb2030.setChecked(appPreff.cb2030().getOr("").equals("") ? false : true);
		cb21.setChecked(appPreff.cb21().getOr("").equals("") ? false : true);
		cb2130.setChecked(appPreff.cb2130().getOr("").equals("") ? false : true);
		cb22.setChecked(appPreff.cb22().getOr("").equals("") ? false : true);
		cb2230.setChecked(appPreff.cb2230().getOr("").equals("") ? false : true);
		cb23.setChecked(appPreff.cb23().getOr("").equals("") ? false : true);
		cb2330.setChecked(appPreff.cb2330().getOr("").equals("") ? false : true);
	}

	@CheckedChange void cb630(CompoundButton cb, boolean isChecked) {
		appPreff.cb630().put(isChecked ? "0630" : "");
	}

	@CheckedChange void cb7(CompoundButton cb, boolean isChecked) {
		appPreff.cb7().put(isChecked ? "0700" : "");
	}

	@CheckedChange void cb730(CompoundButton cb, boolean isChecked) {
		appPreff.cb730().put(isChecked ? "0730" : "");
	}

	@CheckedChange void cb8(CompoundButton cb, boolean isChecked) {
		appPreff.cb8().put(isChecked ? "0800" : "");
	}

	@CheckedChange void cb830(CompoundButton cb, boolean isChecked) {
		appPreff.cb830().put(isChecked ? "0830" : "");
	}

	@CheckedChange void cb9(CompoundButton cb, boolean isChecked) {
		appPreff.cb9().put(isChecked ? "0900" : "");
	}

	@CheckedChange void cb930(CompoundButton cb, boolean isChecked) {
		appPreff.cb930().put(isChecked ? "0930" : "");
	}

	@CheckedChange void cb10(CompoundButton cb, boolean isChecked) {
		appPreff.cb10().put(isChecked ? "1000" : "");
	}

	@CheckedChange void cb1030(CompoundButton cb, boolean isChecked) {
		appPreff.cb1030().put(isChecked ? "1030" : "");
	}

	@CheckedChange void cb11(CompoundButton cb, boolean isChecked) {
		appPreff.cb11().put(isChecked ? "1100" : "");
	}

	@CheckedChange void cb1130(CompoundButton cb, boolean isChecked) {
		appPreff.cb1130().put(isChecked ? "1130" : "");
	}

	@CheckedChange void cb12(CompoundButton cb, boolean isChecked) {
		appPreff.cb12().put(isChecked ? "1200" : "");
	}

	@CheckedChange void cb1230(CompoundButton cb, boolean isChecked) {
		appPreff.cb1230().put(isChecked ? "1230" : "");
	}

	@CheckedChange void cb13(CompoundButton cb, boolean isChecked) {
		appPreff.cb13().put(isChecked ? "1300" : "");
	}

	@CheckedChange void cb1330(CompoundButton cb, boolean isChecked) {
		appPreff.cb1330().put(isChecked ? "1330" : "");
	}

	@CheckedChange void cb14(CompoundButton cb, boolean isChecked) {
		appPreff.cb14().put(isChecked ? "1400" : "");
	}

	@CheckedChange void cb1430(CompoundButton cb, boolean isChecked) {
		appPreff.cb1430().put(isChecked ? "1430" : "");
	}

	@CheckedChange void cb15(CompoundButton cb, boolean isChecked) {
		appPreff.cb15().put(isChecked ? "1500" : "");
	}

	@CheckedChange void cb1530(CompoundButton cb, boolean isChecked) {
		appPreff.cb1530().put(isChecked ? "1530" : "");
	}

	@CheckedChange void cb16(CompoundButton cb, boolean isChecked) {
		appPreff.cb16().put(isChecked ? "1600" : "");
	}

	@CheckedChange void cb1630(CompoundButton cb, boolean isChecked) {
		appPreff.cb1630().put(isChecked ? "1630" : "");
	}

	@CheckedChange void cb17(CompoundButton cb, boolean isChecked) {
		appPreff.cb17().put(isChecked ? "1700" : "");
	}

	@CheckedChange void cb1730(CompoundButton cb, boolean isChecked) {
		appPreff.cb1730().put(isChecked ? "1730" : "");
	}

	@CheckedChange void cb18(CompoundButton cb, boolean isChecked) {
		appPreff.cb18().put(isChecked ? "1800" : "");
	}

	@CheckedChange void cb1830(CompoundButton cb, boolean isChecked) {
		appPreff.cb1830().put(isChecked ? "1830" : "");
	}

	@CheckedChange void cb19(CompoundButton cb, boolean isChecked) {
		appPreff.cb19().put(isChecked ? "1900" : "");
	}

	@CheckedChange void cb1930(CompoundButton cb, boolean isChecked) {
		appPreff.cb1930().put(isChecked ? "1930" : "");
	}

	@CheckedChange void cb20(CompoundButton cb, boolean isChecked) {
		appPreff.cb20().put(isChecked ? "2000" : "");
	}

	@CheckedChange void cb2030(CompoundButton cb, boolean isChecked) {
		appPreff.cb2030().put(isChecked ? "2030" : "");
	}

	@CheckedChange void cb21(CompoundButton cb, boolean isChecked) {
		appPreff.cb21().put(isChecked ? "2100" : "");
	}

	@CheckedChange void cb2130(CompoundButton cb, boolean isChecked) {
		appPreff.cb2130().put(isChecked ? "2130" : "");
	}

	@CheckedChange void cb22(CompoundButton cb, boolean isChecked) {
		appPreff.cb22().put(isChecked ? "2200" : "");
	}

	@CheckedChange void cb2230(CompoundButton cb, boolean isChecked) {
		appPreff.cb2230().put(isChecked ? "2230" : "");
	}

	@CheckedChange void cb23(CompoundButton cb, boolean isChecked) {
		appPreff.cb23().put(isChecked ? "2300" : "");
	}

	@CheckedChange void cb2330(CompoundButton cb, boolean isChecked) {
		appPreff.cb2330().put(isChecked ? "2330" : "");
	}

	public SetAlarmView ( Context context ) {
		super(context);
	}
}
