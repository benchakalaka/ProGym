package com.progym.utils;

import java.util.List;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.progym.model.Ingridient;
import com.progym.model.Meal;
import com.progym.model.User;
import com.progym.model.WaterConsumed;

/**
 * Class helps to do CRUD on database
 * 
 * @author Karpachev Ihor
 */
public class DataBaseUtils {
	private static User	CURRENT_USER;

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

	// return user object by name
	public static User getUserByName(String name) {
		List <User> users = User.find(User.class, "name = ?", name);
		return users.isEmpty() ? null : users.get(0);
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
}
