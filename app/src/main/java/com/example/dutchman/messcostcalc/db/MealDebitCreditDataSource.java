package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Credit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/20/17.
 */

public class MealDebitCreditDataSource {


    private DBHandler handler;
    private SQLiteDatabase database;
    private Context context;

    public MealDebitCreditDataSource(Context context){

        this.context = context;

        handler = new DBHandler(this.context);

    }


    public List<String> getMembersName(int isAvailable){

        MemberDataSource memberDataSource = new MemberDataSource(context);

        return memberDataSource.getMembersName(isAvailable);
    }


    public boolean insertCredit(Credit credit){

        database = handler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE, credit.getDate());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH, credit.getMonth());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR, credit.getYear());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME, credit.getName());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_TK, credit.getTk());

        // Inserting Row
        long res = database.insert(DatabaseConstant.MealDebitCreditTB.NAME, null, values);

        database.close();

        if(res == -1)
            return false;
        else
            return true;
    }


    public Credit getCredit(int id){

        database = handler.getReadableDatabase();

        String sql = "Select * from "+ DatabaseConstant.MealDebitCreditTB.NAME + " where "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_ID + " = ?;";

        Cursor cursor = database.rawQuery(sql, new String[]{id+""});

        Credit credit = null;
        if(cursor != null) {
            cursor.moveToFirst();
            credit = convertToCredit(cursor);
        }

        cursor.close();
        database.close();


        return credit;
    }


    public List<Credit> getCredits(String month, String year){

        List<Credit> credits = new ArrayList<>();

        database = handler.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " where " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? and "+
                DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ?;" , new String[]{month, year});

        if (cursor != null && cursor.moveToFirst()) {

            do{

                Credit credit = convertToCredit(cursor);
                credits.add(credit);

            } while (cursor.moveToNext());

        }
        cursor.close();
        database.close();

        return credits;

    }


    // view last date for homeFragment
    public Credit getLastDateAndName(String month, String year){


        Credit credit = new Credit();

        database = handler.getReadableDatabase();

        //SELECT ROWID from MYTABLE order by ROWID DESC limit 1

        String sql = "SELECT "+DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE+","+DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME+","+ DatabaseConstant.MealDebitCreditTB.COL.KEY_TK+" FROM "+ DatabaseConstant.MealDebitCreditTB.NAME +" WHERE "
                + DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR +" = ? ORDER BY "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_ID +" DESC LIMIT 1";

        Cursor cursor = database.rawQuery(sql,new String[]{month,year});

        if(cursor == null)
            return null;


        if(cursor.moveToFirst()){

            //bazar = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));

            credit.setDate(cursor.getString(0));
            credit.setName(cursor.getString(1));
            credit.setTk(cursor.getDouble(2));

        }
        return credit;
    }


    public double getTotalCredit(String month,String year){

        double totalCost = 0.0;

        int persons = MemberDataSource.getNumberOfMembers(context, 1);

        if(persons > 0) {

            database = handler.getReadableDatabase();

            String sql = "SELECT SUM(" + DatabaseConstant.MealDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " WHERE " +
                    DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ?";

            Cursor cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                totalCost = cursor.getDouble(0);

            }
            return totalCost;

        } else{
            return totalCost;
        }
    }

    public Credit convertToCredit(Cursor cursor){

        Credit credit = new Credit();

        credit.setId(cursor.getInt(0));
        credit.setDate(cursor.getString(1));
        credit.setMonth(cursor.getString(2));
        credit.setYear(cursor.getString(3));
        credit.setName(cursor.getString(4));
        credit.setTk(cursor.getDouble(5));

        return credit;
    }






}
