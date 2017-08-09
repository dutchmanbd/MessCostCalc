package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Credit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dutchman on 8/8/17.
 */

public class RentDebitCreditDataSource extends DatabaseDAO{

    private Context context;
    private static RentDebitCreditDataSource rentDebitCreditDataSource;

    private RentDebitCreditDataSource(Context context){
        super(context);
        this.context = context;
    }

    public static RentDebitCreditDataSource getInstance(Context context){

        if(rentDebitCreditDataSource == null)
            rentDebitCreditDataSource = new RentDebitCreditDataSource(context);

        return rentDebitCreditDataSource;
    }

    public List<String> getMembersName(int isAvailable){

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        List<String> members =  memberDataSource.getMembersName(isAvailable);

        return members;
    }

    public boolean insertCredit(Credit credit){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.RentDebitCreditTB.COL.KEY_DATE, credit.getDate());
        values.put(DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH, credit.getMonth());
        values.put(DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR, credit.getYear());
        values.put(DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME, credit.getName());
        values.put(DatabaseConstant.RentDebitCreditTB.COL.KEY_TK, credit.getTk());

        // Inserting Row
        long res = database.insert(DatabaseConstant.RentDebitCreditTB.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Credit getCredit(int id){

        String sql = "Select * from "+ DatabaseConstant.RentDebitCreditTB.NAME + " where "+ DatabaseConstant.RentDebitCreditTB.COL.KEY_ID + " = ?;";

        Cursor cursor = null;
        Credit credit = null;
        try {
            cursor = database.rawQuery(sql, new String[]{id + ""});

            if (cursor != null) {
                cursor.moveToFirst();
                credit = convertToCredit(cursor);
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return credit;
    }

    public List<Credit> getCredits(String month, String year){

        List<Credit> credits = new ArrayList<>();

        List<String> names = getMembersName(1);
        Cursor cursor = null;

        try {

            for (String name : names) {

                Log.d("RentDebitCredtDataSource", "getCredits: "+name);

                cursor = database.rawQuery("SELECT SUM(" + DatabaseConstant.RentDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.RentDebitCreditTB.NAME + " where " + DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH + " = ? and " +
                        DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR + " = ? and " + DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME + " = ?;", new String[]{month, year, name});

                if (cursor.moveToFirst()) {

                    do {

                        Credit credit = new Credit();  //convertToCredit(cursor);

                        credit.setName(name);
                        credit.setTk(cursor.getDouble(0));

                        credits.add(credit);

                    } while (cursor.moveToNext());

                }

                if(cursor != null)
                    cursor.close();
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return credits;

    }

    // view last date for homeFragment
    public Credit getLastDateAndName(String month, String year){


        Credit credit = new Credit();

        String sql = "SELECT "+DatabaseConstant.RentDebitCreditTB.COL.KEY_DATE+","+DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME+","+ DatabaseConstant.RentDebitCreditTB.COL.KEY_TK+" FROM "+ DatabaseConstant.RentDebitCreditTB.NAME +" WHERE "
                + DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR +" = ? ORDER BY "+ DatabaseConstant.RentDebitCreditTB.COL.KEY_ID +" DESC LIMIT 1";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                //bazar = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));

                credit.setDate(cursor.getString(0));
                credit.setName(cursor.getString(1));
                credit.setTk(cursor.getDouble(2));

            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return credit;
    }

    public double getTotalCredit(String month,String year){

        double totalCost = 0.0;


        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        int persons = memberDataSource.getMembers(1).size();

        if(persons > 0) {

            String sql = "SELECT SUM(" + DatabaseConstant.RentDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.RentDebitCreditTB.NAME + " WHERE " +
                    DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR + " = ?";

            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{month, year});

                if (cursor.moveToFirst()) {

                    totalCost = cursor.getDouble(0);

                }
            } catch (Exception e){

            } finally {
                if(cursor != null)
                    cursor.close();
            }

            return totalCost / persons;

        } else{
            return totalCost;
        }
    }

    public List<String> getMembersName(String month,String year){

        List<String> members = new ArrayList<>();

        String sql = "SELECT " + DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME + " FROM " + DatabaseConstant.RentDebitCreditTB.NAME + " WHERE " +
                DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR + " = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                String name = cursor.getString(0);
                members.add(name);

            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return members;

    }


    public List<Credit> getCreditForMHistory(String month, String year){

        List<Credit> list = new ArrayList<>();
        List<String> nameList = null;

        String currentMonth = new SimpleDateFormat("MMMM").format(new Date());
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);

        if(currentMonth.equals(month) && currentYear.equals(year))
            nameList = memberDataSource.getMembersName(1);
        else
            nameList = getMembersName(month, year);

        Cursor cursor = null;

        try {

            for (String name : nameList) {

                String sql = "SELECT SUM(" + DatabaseConstant.RentDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.RentDebitCreditTB.NAME + " WHERE " +
                        DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR + " = ? AND " +
                        DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME + " = ?;";
                cursor = database.rawQuery(sql, new String[]{month, year, name});

                if (cursor.moveToFirst()) {
                    do {
                        Credit credit = new Credit();

                        credit.setName(name);
                        credit.setTk(cursor.getDouble(0));

                        list.add(credit);


                    } while (cursor.moveToNext());
                }

                if(cursor != null)
                    cursor.close();
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return list;
    }


    public int getNumberOfMembersForRent(String month, String year){

        int members = 0;

        String currentMonth = new SimpleDateFormat("MMMM").format(new Date());
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());

        if(month.equals(currentMonth) && year.equals(currentYear)){
            members = getMembersName(1).size();
        } else{
            members = getNumberOfMembers(month, year);
        }

        return members;
    }
    public int getNumberOfMembers(String month,String year){


        String selectQuery = "SELECT DISTINCT "+DatabaseConstant.RentDebitCreditTB.COL.KEY_MEMBER_NAME+" FROM " + DatabaseConstant.RentDebitCreditTB.NAME+" WHERE "+DatabaseConstant.RentDebitCreditTB.COL.KEY_MONTH +" = ? AND "+DatabaseConstant.RentDebitCreditTB.COL.KEY_YEAR+" = ?;";

        Cursor cursor = null;
        int persons = 0;
        try {
            cursor = database.rawQuery(selectQuery, new String[]{month, year});

            if (cursor.moveToFirst())
                persons = cursor.getCount();
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }


        return persons;
    }


    public Credit convertToCredit(Cursor cursor){

        Credit credit = new Credit();

        credit.setId(cursor.getInt(0));
        credit.setDate(cursor.getString(1));
        credit.setMonth(cursor.getString(2));
        credit.setYear(cursor.getString(3));
        credit.setName(cursor.getString(4));
        credit.setTk(cursor.getDouble(5));

        String data = credit.getId()+" "+credit.getDate()+" "+credit.getName()+" "+credit.getTk();
        Log.d("MealCreditFragment", "convertToCredit: "+data);

        return credit;
    }






}
