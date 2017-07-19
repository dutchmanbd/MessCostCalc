package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/14/17.
 */

public class MealDataSource {

    private DBHandler handler;
    private SQLiteDatabase database;
    private Context context;

    public MealDataSource(Context context){

        this.context = context;

        handler = new DBHandler(this.context);
    }


    public boolean insertMember(Meal meal){

        database = handler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MealTB.COL.KEY_DATE, meal.getDate());
        values.put(DatabaseConstant.MealTB.COL.KEY_MONTH, meal.getMonth());
        values.put(DatabaseConstant.MealTB.COL.KEY_YEAR, meal.getYear());
        values.put(DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME, meal.getName());
        values.put(DatabaseConstant.MealTB.COL.KEY_MEAL, meal.getMeal());

        // Inserting Row
        long res = database.insert(DatabaseConstant.MealTB.NAME, null, values);

        database.close();

        if(res == -1)
            return false;
        else
            return true;
    }


    public Meal getMeal(int id){

        database = handler.getReadableDatabase();

        Cursor cursor = database.query(DatabaseConstant.MealTB.NAME,
                new String[] {
                        DatabaseConstant.MealTB.COL.KEY_ID,
                        DatabaseConstant.MealTB.COL.KEY_DATE,
                        DatabaseConstant.MealTB.COL.KEY_MONTH,
                        DatabaseConstant.MealTB.COL.KEY_YEAR,
                        DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME,
                        DatabaseConstant.MealTB.COL.KEY_MEAL},
                DatabaseConstant.MealTB.COL.KEY_ID + " = " + id,
                null, null, null, null);

        cursor.moveToFirst();
        Meal meal = convertToMeal(cursor);

        cursor.close();
        database.close();

        return meal;
    }


    public Meal getMeal(String month, String year){

        database = handler.getReadableDatabase();

        Cursor cursor = database.query(DatabaseConstant.MealTB.NAME,
                new String[] {
                        DatabaseConstant.MealTB.COL.KEY_ID,
                        DatabaseConstant.MealTB.COL.KEY_DATE,
                        DatabaseConstant.MealTB.COL.KEY_MONTH,
                        DatabaseConstant.MealTB.COL.KEY_YEAR,
                        DatabaseConstant.MealTB.COL.KEY_MEMBER_NAME,
                        DatabaseConstant.MealTB.COL.KEY_MEAL},
                DatabaseConstant.MealTB.COL.KEY_MONTH + " = " + month + " and "+
                        DatabaseConstant.MealTB.COL.KEY_YEAR + " = " + year,
                null, null, null, null);

        cursor.moveToFirst();
        Meal meal = convertToMeal(cursor);

        cursor.close();
        database.close();
        
        return meal;
    }


    public List<Meal> getMeals(String month, String year){

        List<Meal> meals = new ArrayList<>();

        database = handler.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MealTB.NAME + " where " + DatabaseConstant.MealTB.COL.KEY_MONTH + " = ? and "+
                DatabaseConstant.MealTB.COL.KEY_YEAR + " = ?;" , new String[]{month, year});

        if (cursor != null && cursor.moveToFirst()) {

            do{

                Meal meal = convertToMeal(cursor);
                meals.add(meal);

            } while (cursor.moveToNext());

        }
        cursor.close();
        database.close();

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
