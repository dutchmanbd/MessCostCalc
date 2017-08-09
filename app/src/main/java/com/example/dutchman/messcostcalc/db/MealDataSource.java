package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/14/17.
 */

public class MealDataSource extends DatabaseDAO{

    private Context context;
    private static MealDataSource mealDataSource;

    private MealDataSource(Context context){

        super(context);
        this.context = context;
    }

    public static MealDataSource getInstance(Context context){

        if(mealDataSource == null)
            mealDataSource = new MealDataSource(context);

        return mealDataSource;
    }

    public boolean insertMember(Meal meal){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MealTB.COL.KEY_DATE, meal.getDate());
        values.put(DatabaseConstant.MealTB.COL.KEY_MONTH, meal.getMonth());
        values.put(DatabaseConstant.MealTB.COL.KEY_YEAR, meal.getYear());
        values.put(DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME, meal.getName());
        values.put(DatabaseConstant.MealTB.COL.KEY_MEAL, meal.getMeal());

        // Inserting Row
        long res = database.insert(DatabaseConstant.MealTB.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;
    }


    public Meal getMeal(int id){

        Cursor cursor = null;
        Meal meal = null;
        try {
            cursor = database.query(DatabaseConstant.MealTB.NAME,
                    new String[]{
                            DatabaseConstant.MealTB.COL.KEY_ID,
                            DatabaseConstant.MealTB.COL.KEY_DATE,
                            DatabaseConstant.MealTB.COL.KEY_MONTH,
                            DatabaseConstant.MealTB.COL.KEY_YEAR,
                            DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME,
                            DatabaseConstant.MealTB.COL.KEY_MEAL},
                    DatabaseConstant.MealTB.COL.KEY_ID + " = " + id,
                    null, null, null, null);

            cursor.moveToFirst();
            meal = convertToMeal(cursor);
        } catch (Exception e){


        } finally {
            if(cursor != null)
                cursor.close();
        }

        return meal;
    }


    public Meal getMeal(String month, String year){

        Cursor cursor = null;
        Meal meal = null;

        try {
            cursor = database.query(DatabaseConstant.MealTB.NAME,
                    new String[]{
                            DatabaseConstant.MealTB.COL.KEY_ID,
                            DatabaseConstant.MealTB.COL.KEY_DATE,
                            DatabaseConstant.MealTB.COL.KEY_MONTH,
                            DatabaseConstant.MealTB.COL.KEY_YEAR,
                            DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME,
                            DatabaseConstant.MealTB.COL.KEY_MEAL},
                    DatabaseConstant.MealTB.COL.KEY_MONTH + " = " + month + " and " +
                            DatabaseConstant.MealTB.COL.KEY_YEAR + " = " + year,
                    null, null, null, null);

            cursor.moveToFirst();
            meal = convertToMeal(cursor);
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }
        
        return meal;
    }


    public List<Meal> getMeals(String month, String year){

        List<Meal> meals = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MealTB.NAME + " where " + DatabaseConstant.MealTB.COL.KEY_MONTH + " = ? and " +
                    DatabaseConstant.MealTB.COL.KEY_YEAR + " = ?;", new String[]{month, year});

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    Meal meal = convertToMeal(cursor);
                    meals.add(meal);

                } while (cursor.moveToNext());

            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return meals;

    }


    public List<Meal> getMealInfo(String month, String year){
        List<Meal> dateList = new ArrayList<>();

        String sql = "SELECT DISTINCT "+DatabaseConstant.MealTB.COL.KEY_DATE+" FROM " + DatabaseConstant.MealTB.NAME + " WHERE " +
                DatabaseConstant.MealTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealTB.COL.KEY_YEAR + " = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {
                do {

                    String date = cursor.getString(0);
                    double mealNo = getTotalMealOnDate(date);

                    Meal meal = new Meal();
                    meal.setDate(date);
                    meal.setMeal(mealNo);

                    dateList.add(meal);

                } while (cursor.moveToNext());
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return dateList;
    }

    public double getTotalMealOnDate(String date){

        double mealNo = 0;

        String sql = "SELECT SUM("+DatabaseConstant.MealTB.COL.KEY_MEAL+") FROM " + DatabaseConstant.MealTB.NAME + " WHERE " +
                DatabaseConstant.MealTB.COL.KEY_DATE + " = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{date});

            if (cursor.moveToFirst()) {
                do {

                    mealNo = cursor.getDouble(0);

                } while (cursor.moveToNext());
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return mealNo;
    }


    public List<Meal> getMealOnDate(String date){

        List<Meal> meals = new ArrayList<>();
        Meal meal;

        String sql = "SELECT * FROM " + DatabaseConstant.MealTB.NAME + " WHERE " +
                DatabaseConstant.MealTB.COL.KEY_DATE + " = ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(sql, new String[]{date});

            if (cursor.moveToFirst()) {
                do {

                    meal = convertToMeal(cursor);

                    meals.add(meal);

                } while (cursor.moveToNext());
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return meals;
    }





    private Meal convertToMeal(Cursor cursor){
        Meal meal = new Meal();

        meal.setId(cursor.getInt(0));
        meal.setDate(cursor.getString(1));
        meal.setMonth(cursor.getString(2));
        meal.setYear(cursor.getString(3));
        meal.setName(cursor.getString(4));
        meal.setMeal(cursor.getDouble(5));

        return meal;
    }




}
