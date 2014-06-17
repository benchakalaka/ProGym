package com.progym.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref ( value = SharedPref.Scope.UNIQUE ) public interface AppSharedPreferences {
     @DefaultBoolean ( false ) boolean isAlarmSet();

     // 6:30 in the morning and so on ....
     @DefaultString ( "" ) String cb630();

     @DefaultString ( "" ) String cb7();

     @DefaultString ( "" ) String cb730();

     @DefaultString ( "" ) String cb8();

     @DefaultString ( "" ) String cb830();

     @DefaultString ( "" ) String cb9();

     @DefaultString ( "" ) String cb930();

     @DefaultString ( "" ) String cb10();

     @DefaultString ( "" ) String cb1030();

     @DefaultString ( "" ) String cb11();

     @DefaultString ( "" ) String cb1130();

     @DefaultString ( "" ) String cb12();

     @DefaultString ( "" ) String cb1230();

     @DefaultString ( "" ) String cb13();

     @DefaultString ( "" ) String cb1330();

     @DefaultString ( "" ) String cb14();

     @DefaultString ( "" ) String cb1430();

     @DefaultString ( "" ) String cb15();

     @DefaultString ( "" ) String cb1530();

     @DefaultString ( "" ) String cb16();

     @DefaultString ( "" ) String cb1630();

     @DefaultString ( "" ) String cb17();

     @DefaultString ( "" ) String cb1730();

     @DefaultString ( "" ) String cb18();

     @DefaultString ( "" ) String cb1830();

     @DefaultString ( "" ) String cb19();

     @DefaultString ( "" ) String cb1930();

     @DefaultString ( "" ) String cb20();

     @DefaultString ( "" ) String cb2030();

     @DefaultString ( "" ) String cb21();

     @DefaultString ( "" ) String cb2130();

     @DefaultString ( "" ) String cb22();

     @DefaultString ( "" ) String cb2230();

     @DefaultString ( "" ) String cb23();

     @DefaultString ( "" ) String cb2330();
}
