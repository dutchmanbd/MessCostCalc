package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Bazar;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/13/17.
 */

public class BazarDataSource {

    private DBHandler handler;
    private SQLiteDatabase database;
    private Context context;

    public BazarDataSource(Context context){

        this.context = context;

        handler = new DBHandler(this.context);

    }


    public List<String> getMembersName(int isAvailable){

        MemberDataSource memberDataSource = new MemberDataSource(context);

        return memberDataSource.getMembersName(isAvailable);
    }


    public boolean insertBazar(Bazar bazar){

        database = handler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.BazarTB.COL.KEY_DATE, bazar.getDate());
        values.put(DatabaseConstant.BazarTB.COL.KEY_MONTH, bazar.getMonth());
        values.put(DatabaseConstant.BazarTB.COL.KEY_YEAR, bazar.getYear());
        values.put(DatabaseConstant.BazarTB.COL.KEY_MEMBER_NAME, bazar.getmName());
        values.put(DatabaseConstant.BazarTB.COL.KEY_TK, bazar.getTk());

        // Inserting Row
        long res = database.insert(DatabaseConstant.BazarTB.NAME, null, values);

        database.close();

        if(res == -1)
            return false;
        else
            return true;
    }


    public Bazar getBazar(int id){

        database = handler.getReadableDatabase();

        String sql = "Select * from "+ DatabaseConstant.BazarTB.NAME + " where "+ DatabaseConstant.MemberTB.COL.KEY_ID + " = ?;";

        Cursor cursor = database.rawQuery(sql, new String[]{id+""});

        Bazar bazar = null;
        if(cursor != null) {
            cursor.moveToFirst();
            bazar = convertToBazar(cursor);
        }

        cursor.close();
        database.close();


        return bazar;
    }


    public List<Bazar> getBazars(String month, String year){

        List<Bazar> bazars = new ArrayList<>();

        database = handler.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.BazarTB.NAME + " where " + DatabaseConstant.BazarTB.COL.KEY_MONTH + " = ? and "+
                DatabaseConstant.BazarTB.COL.KEY_YEAR + " = ?;" , new String[]{month, year});

        if (cursor != null && cursor.moveToFirst()) {

            do{

                Bazar bazar = convertToBazar(cursor);
                bazars.add(bazar);

            } while (cursor.moveToNext());

        }
        cursor.close();
        database.close();

        return bazars;

    }


    // view last date for homeFragment
    public Bazar getLastDateAndName(String month, String year){


        Bazar bazar = new Bazar();

        database = handler.getReadableDatabase();

        //SELECT ROWID from MYTABLE order by ROWID DESC limit 1

        String sql = "SELECT "+DatabaseConstant.BazarTB.COL.KEY_DATE+","+DatabaseConstant.BazarTB.COL.KEY_MEMBER_NAME+","+ DatabaseConstant.BazarTB.COL.KEY_TK+" FROM "+ DatabaseConstant.BazarTB.NAME +" WHERE "
                + DatabaseConstant.BazarTB.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.BazarTB.COL.KEY_YEAR +" = ? ORDER BY "+ DatabaseConstant.BazarTB.COL.KEY_ID +" DESC LIMIT 1";

        Cursor cursor = database.rawQuery(sql,new String[]{month,year});

        if(cursor == null)
            return null;


        if(cursor.moveToFirst()){

            //bazar = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));

            bazar.setDate(cursor.getString(0));
            bazar.setmName(cursor.getString(1));
            bazar.setTk(cursor.getDouble(2));

        }
        return bazar;
    }


    public double getTotalBazar(String month,String year){

        double totalCost = 0.0;

        int persons = MemberDataSource.getNumberOfMembers(context, 1);

        if(persons > 0) {

            database = handler.getReadableDatabase();

            String sql = "SELECT SUM(" + DatabaseConstant.BazarTB.COL.KEY_TK + ") FROM " + DatabaseConstant.BazarTB.NAME + " WHERE " +
                    DatabaseConstant.BazarTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.BazarTB.COL.KEY_YEAR + " = ?";

            Cursor cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                totalCost = cursor.getDouble(0);

            }
            return totalCost;

        } else{
            return totalCost;
        }
    }

    public Bazar convertToBazar(Cursor cursor){

        Bazar bazar = new Bazar();

        bazar.setId(cursor.getInt(0));
        bazar.setDate(cursor.getString(1));
        bazar.setMonth(cursor.getString(2));
        bazar.setYear(cursor.getString(3));
        bazar.setmName(cursor.getString(4));
        bazar.setTk(cursor.getDouble(5));

        return bazar;
    }








}
