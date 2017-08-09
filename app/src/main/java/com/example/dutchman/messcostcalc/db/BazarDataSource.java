package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Bazar;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/13/17.
 */

public class BazarDataSource extends DatabaseDAO{

    private Context context;
    private static BazarDataSource bazarDataSource;

    private BazarDataSource(Context context){
        super(context);
        this.context = context;
    }


    public static BazarDataSource getInstance(Context context){
        if(bazarDataSource == null)
            bazarDataSource = new BazarDataSource(context);

        return bazarDataSource;
    }

    public List<String> getMembersName(int isAvailable){

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        List<String> lists = memberDataSource.getMembersName(isAvailable);
        return lists;
    }

    public List<String> getMembersName(String month, String year){
        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        List<String> lists = memberDataSource.getMembersName(month, year);
        return lists;
    }

    public boolean isMemberExists(int isAvailable){
        return getMembersName(isAvailable).size() > 0;
    }


    public boolean insertBazar(Bazar bazar){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.BazarTB.COL.KEY_DATE, bazar.getDate());
        values.put(DatabaseConstant.BazarTB.COL.KEY_MONTH, bazar.getMonth());
        values.put(DatabaseConstant.BazarTB.COL.KEY_YEAR, bazar.getYear());
        values.put(DatabaseConstant.BazarTB.COL.KEY_MEMBER_NAME, bazar.getmName());
        values.put(DatabaseConstant.BazarTB.COL.KEY_TK, bazar.getTk());

        // Inserting Row
        long res = database.insert(DatabaseConstant.BazarTB.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;
    }



    public Bazar getBazar(int id){
        String sql = "Select * from "+ DatabaseConstant.BazarTB.NAME + " where "+ DatabaseConstant.MemberTB.COL.KEY_ID + " = ?;";
        Cursor cursor = null;
        Bazar bazar = null;
        try {
            cursor = database.rawQuery(sql, new String[]{id + ""});
            if (cursor != null) {
                cursor.moveToFirst();
                bazar = convertToBazar(cursor);
            }
        } catch (Exception e){
            if(cursor != null)
                cursor.close();
        }

        return bazar;
    }

    public List<Bazar> getBazars(String month, String year){

        List<Bazar> bazars = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.BazarTB.NAME + " where " + DatabaseConstant.BazarTB.COL.KEY_MONTH + " = ? and " +
                    DatabaseConstant.BazarTB.COL.KEY_YEAR + " = ?;", new String[]{month, year});

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    Bazar bazar = convertToBazar(cursor);
                    bazars.add(bazar);

                } while (cursor.moveToNext());

            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return bazars;

    }


    // view last date for homeFragment
    public Bazar getLastDateAndName(String month, String year){

        Bazar bazar = new Bazar();

        String sql = "SELECT "+DatabaseConstant.BazarTB.COL.KEY_DATE+","+DatabaseConstant.BazarTB.COL.KEY_MEMBER_NAME+","+ DatabaseConstant.BazarTB.COL.KEY_TK+" FROM "+ DatabaseConstant.BazarTB.NAME +" WHERE "
                + DatabaseConstant.BazarTB.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.BazarTB.COL.KEY_YEAR +" = ? ORDER BY "+ DatabaseConstant.BazarTB.COL.KEY_ID +" DESC LIMIT 1";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});
            if (cursor.moveToFirst()) {

                //bazar = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));

                bazar.setDate(cursor.getString(0));
                bazar.setmName(cursor.getString(1));
                bazar.setTk(cursor.getDouble(2));

            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return bazar;
    }


    public double getTotalBazar(String month,String year){

        double totalCost = 0.0;

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        int persons = memberDataSource.getMembers(1).size();

        if(persons > 0) {

            String sql = "SELECT SUM(" + DatabaseConstant.BazarTB.COL.KEY_TK + ") FROM " + DatabaseConstant.BazarTB.NAME + " WHERE " +
                    DatabaseConstant.BazarTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.BazarTB.COL.KEY_YEAR + " = ?";

            Log.d("BazarDataSource", "getTotalBazar: "+sql);

            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{month, year});

                if (cursor.moveToFirst()) {

                    totalCost = cursor.getDouble(0);

                }

                if (cursor != null)
                    cursor.close();


            } catch (Exception e){

            } finally {
                if (cursor != null)
                    cursor.close();
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
