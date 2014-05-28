package com.progym.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.progym.custom.sql.SQLiteAssetHelper;

public class SqlAssetHelper extends SQLiteAssetHelper {
     private static final String DATABASE_NAME    = "ingridients";
     private static final int    DATABASE_VERSION = 1;

     public SqlAssetHelper ( Context context ) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

     public Cursor getByGroupName(String byGroupName) {
          SQLiteDatabase db = getReadableDatabase();
          return db.rawQuery("select * from ingridient where groupName = ?", new String[] { byGroupName });
     }

     /**
      * SqlAssetHelper db = new SqlAssetHelper(getApplicationContext());
      * Cursor d = db.getByGroupName("Meat");
      * while ( d.moveToNext() ) {
      * Utils.logw(d.getString(5));
      * }
      * d.close();
      */
}
