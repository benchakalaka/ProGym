package com.progym.utils;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.progym.model.Ingridient;
import com.progym.model.Meal;
import com.progym.model.ReadyIngridient;
import com.progym.model.User;
import com.progym.model.WaterConsumed;

/**
 * Class helps to do CRUD on database
 * 
 * @author Karpachev Ihor
 */
public class DataBaseUtils {

     // Sugar basic usage - List<Book> books = Book.find(Book.class, "author = ?", new String{author.getId()});
     // Book.find(Note.class, "name = ? and title = ?", "satya", "title1");
     // http://satyan.github.io/sugar/query.html - query for sugar
     // List<Note> notes = Note.findWithQuery(Note.class, "Select * from Note where name = ?", "satya");
     // Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"),Condition.prop("prop").eq(2)).list();

     public static final String    DATE_PATTERN_YYYY_MM_DD_HH_MM_SS        = "yyyy/MM/dd HH:mm:ss";
     public static final String    DATE_PATTERN_YYYY_MM_DD_HH_MM_SS_MILLIS = "yyyy/MM/dd HH:mm:ss:SSS";
     public static final String    DATE_PATTERN_HH_MM_SS                   = "HH:mm:ss";
     public static final String    DATE_PATTERN_HH_MM_SS_MILLIS            = "HH:mm:ss:SSS";
     public static final String    DATE_PATTERN_YYYY_MM_DD                 = "yyyy/MM/dd";
     public static final String    DATE_PATTERN_YYYY_MM                    = "yyyy/MM";
     public static final String    DATE_PATTERN_YYYY                       = "yyyy";

     private static User           CURRENT_USER;
     private static SqlAssetHelper DB;

     // ///////////////////////////////////////////////////////// INGRIDIENT DATABASE METHODS /////////////////////////////////////////////////////
     public static void setUpIngridientsDatabase(Context ctx) {
          if ( null == DB ) {
               DB = new SqlAssetHelper(ctx);
          }
     }

     public static Cursor getByGroupName(String byGroupName) {
          return DB.getReadableDatabase().rawQuery("select * from ingridient where groupName = ?", new String[] { byGroupName });
     }

     public static Cursor getByGroupNameAndIngridientName(String ingridientName) {
          return DB.getReadableDatabase().rawQuery("select * from ingridient where name = ?", new String[] { ingridientName });
     }

     // /////////////////////////////////////////////////////////// USER DATABASE ///////////////////////////////////////////////////////////////////
     /**
      * Select all records, where date starts from yearMonth_YYYY_MM
      * example yearMonth_YYYY_MM = 2014/05, date = 2014/05/23 12:23:13,
      * query = SELECT * FROM WATER_CONSUMED WHERE date like='2013/05%'
      */
     public static List <WaterConsumed> getAllWaterConsumedInMonth(String yearMonth_YYYY_MM) {
          return Select.from(WaterConsumed.class).where(Condition.prop("date").like(yearMonth_YYYY_MM + "%")).list();
     }

     // /////////////////////////////////////////////////////////// USER DATABASE ///////////////////////////////////////////////////////////////////
     /**
      * Select all records, where date starts from yearMonth_YYYY_MM
      * example yearMonth_YYYY_MM = 2014/05, date = 2014/05/23 12:23:13,
      * query = SELECT * FROM WATER_CONSUMED WHERE date like='2013/05%'
      */
     public static List <Ingridient> getAllFoodConsumedInMonth(String yearMonth_YYYY_MM) {
          return Select.from(Ingridient.class).where(Condition.prop("date").like(yearMonth_YYYY_MM + "%")).list();
     }

     public static double getWaterUserShouldConsumePerDay() {
          double retValue = 0;
          if ( null != CURRENT_USER && CURRENT_USER.weight != 0 ) {
               // in ml
               retValue = (CURRENT_USER.weight / 30) * 1000;
          }
          return retValue;
     }

     public static void setCurrentUser(User u) {
          CURRENT_USER = u;
     }

     public static User getCurrentUser() {
          return CURRENT_USER;
     }

     // Get all water record
     public static List <Meal> getAllPlates() {
          return Meal.listAll(Meal.class);
     }

     // Get all water record
     public static List <WaterConsumed> getAllWaterConsumed() {
          return WaterConsumed.listAll(WaterConsumed.class);
     }

     // Display volumes consumed only in this day
     public static List <WaterConsumed> getWaterConsumedByDate(String date) {
          return Select.from(WaterConsumed.class).where(Condition.prop("date").like(date + "%")).list();
     }

     // Display volumes consumed only in this day
     public static List <Meal> getPlatesConsumedByDate(String date) {
          return Select.from(Meal.class).where(Condition.prop("date").like(date + "%")).list();
     }

     // Display volumes consumed only in this day
     public static List <Ingridient> getProductsOnPlate(Meal meal) {
          return Select.from(Ingridient.class).where(Condition.prop("date").like(meal.date + "%")).list();
     }

     // Display volumes consumed only in this day
     public static List <Ingridient> getProductsOnPlate(String date) {
          return Select.from(Ingridient.class).where(Condition.prop("date").like(date + "%")).list();
     }

     // Display volumes consumed only in this day
     public static List <ReadyIngridient> getReadyIngridients(String date) {
          return Select.from(ReadyIngridient.class).where(Condition.prop("date").like(date + "%")).list();
     }

     // return user object by name
     public static User getUserByName(String name) {
          List <User> users = User.find(User.class, "name = ?", name);
          return users.isEmpty() ? null : users.get(0);
     }

     // return user object by name
     public static List <User> getUsers() {
          return User.listAll(User.class);
     }

     // return amount of water in ml consumed per day
     public static int getConsumedPerDay(String day) {
          int alreadyDrinked = 0;
          List <WaterConsumed> list = DataBaseUtils.getWaterConsumedByDate(day);
          for ( WaterConsumed w : list ) {
               alreadyDrinked += w.volumeConsumed;
          }
          return alreadyDrinked;
     }

     public static User findUserById(long id) {
          return User.findById(User.class, id);
     }
}
