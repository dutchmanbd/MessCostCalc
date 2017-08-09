package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Calculator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dutchman on 8/8/17.
 */

public class RentInfoDatatSource extends DatabaseDAO{
    private Context context;
    private static RentInfoDatatSource rentInfoDatatSource;

    private RentInfoDatatSource(Context context){

        super(context);
        this.context = context;

    }

    public static RentInfoDatatSource getInstance(Context context){

        if(rentInfoDatatSource == null)
            rentInfoDatatSource = new RentInfoDatatSource(context);

        return rentInfoDatatSource;
    }


    public boolean insertIntoRentInfo(Calculator calculator){

        ContentValues values = new ContentValues();

        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        values.put(DatabaseConstant.RentInfo.COL.KEY_MONTH,month);
        values.put(DatabaseConstant.RentInfo.COL.KEY_YEAR,year);

        values.put(DatabaseConstant.RentInfo.COL.KEY_H_RENT,calculator.getHouseRent());
        values.put(DatabaseConstant.RentInfo.COL.KEY_GUS_CURRENT,calculator.getGusCurrent());
        values.put(DatabaseConstant.RentInfo.COL.KEY_SERVENT,calculator.getServent());
        values.put(DatabaseConstant.RentInfo.COL.KEY_NET_BILL,calculator.getInternet());
        values.put(DatabaseConstant.RentInfo.COL.KEY_PAPER,calculator.getPaper());
        values.put(DatabaseConstant.RentInfo.COL.KEY_DIRST,calculator.getDirst());
        values.put(DatabaseConstant.RentInfo.COL.KEY_OTHERS,calculator.getOthers());
        values.put(DatabaseConstant.RentInfo.COL.KEY_MEMBERS,calculator.getMembers());
        values.put(DatabaseConstant.RentInfo.COL.KEY_TOTAL_RENT,calculator.getTotal());
        values.put(DatabaseConstant.RentInfo.COL.KEY_PERHEAD,calculator.getPerhead());

        // Inserting Row
        long res = database.insert(DatabaseConstant.RentInfo.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;

    }

    public int getNumberOfMembers(){

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);

        return memberDataSource.getMembersName(1).size();

    }

    public List<Calculator> getRentCostInfo(String month, String year){

        List<Calculator> calculators = new ArrayList<>();
        Calculator calculator;

        String sql = "SELECT * FROM "+ DatabaseConstant.RentInfo.NAME +" WHERE "+ DatabaseConstant.RentInfo.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.RentInfo.COL.KEY_YEAR +" = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {
                do {
                    calculator = new Calculator(cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                            cursor.getString(10), cursor.getString(11), cursor.getString(12)
                    );

                    calculators.add(calculator);

                } while (cursor.moveToNext());
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }
        return calculators;
    }

    public int getTotalCostForRent(String month, String year){
        int total = 0;

        String sql = "SELECT "+ DatabaseConstant.RentInfo.COL.KEY_TOTAL_RENT +" FROM "+ DatabaseConstant.RentInfo.NAME  +" WHERE "+ DatabaseConstant.RentInfo.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.RentInfo.COL.KEY_YEAR +" = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                total = cursor.getInt(0);

            }

            if (cursor != null)
                cursor.close();

        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return total;
    }

    public int getPerheadCostFromRent(String month,String year){

        int perhead = 0;

        String sql = "SELECT "+ DatabaseConstant.RentInfo.COL.KEY_PERHEAD +" FROM "+ DatabaseConstant.RentInfo.NAME +" WHERE "+ DatabaseConstant.RentInfo.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.RentInfo.COL.KEY_YEAR +" = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                perhead = cursor.getInt(0);

            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return perhead;
    }

}
