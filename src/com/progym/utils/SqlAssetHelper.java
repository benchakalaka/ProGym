package com.progym.utils;

import android.content.Context;

import com.progym.custom.sql.SQLiteAssetHelper;

public class SqlAssetHelper extends SQLiteAssetHelper {
     private static final String DATABASE_NAME    = "ingridients";
     private static final int    DATABASE_VERSION = 1;

     public SqlAssetHelper ( Context context ) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }
}
