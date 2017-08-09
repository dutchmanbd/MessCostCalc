package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Credit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dutchman on 7/20/17.
 */

public class MealDebitCreditDataSource extends DatabaseDAO{

    private Context context;

    private static MealDebitCreditDataSource mealDebitCreditDataSource;

    private MealDebitCreditDataSource(Context context){

        super(context);
        this.context = context;
        open();
    }

    public static MealDebitCreditDataSource getInstance(Context context){

        if(mealDebitCreditDataSource == null)
            mealDebitCreditDataSource = new MealDebitCreditDataSource(context);

        return mealDebitCreditDataSource;
    }

    public List<String> getMembersName(int isAvailable){

        MemberDataSource memberDataSource = MemberDataSource.getInstance(context);
        List<String> members =  memberDataSource.getMembersName(isAvailable);

        return members;
    }

    public boolean insertCredit(Credit credit){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE, credit.getDate());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH, credit.getMonth());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR, credit.getYear());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME, credit.getName());
        values.put(DatabaseConstant.MealDebitCreditTB.COL.KEY_TK, credit.getTk());

        Log.d("MealDebitCreditDataSource", "insertCredit: "+credit.getName()+" "+credit.getTk());

        // Inserting Row
        long res = database.insert(DatabaseConstant.MealDebitCreditTB.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Credit getCredit(int id){

        String sql = "Select * from "+ DatabaseConstant.MealDebitCreditTB.NAME + " where "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_ID + " = ?;";

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

                cursor = database.rawQuery("SELECT SUM(" + DatabaseConstant.MealDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " where " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? and " +
                        DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ? and " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME + " = ?;", new String[]{month, year, name});

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

    public List<Credit> getPersonCredits(String month, String year, String name){

        List<Credit> credits = new ArrayList<>();

        Cursor cursor = null;

        String sql = "SELECT "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_ID +","+ DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE + "," + DatabaseConstant.MealDebitCreditTB.COL.KEY_TK + " FROM "
                + DatabaseConstant.MealDebitCreditTB.NAME  + " WHERE " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME + " = ?";

        try{

            cursor = database.rawQuery(sql, new String[]{month, year, name});
            if (cursor.moveToFirst()) {

                do {

                    Credit credit = new Credit();
                    credit.setId(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.MealDebitCreditTB.COL.KEY_ID)));
                    credit.setDate(cursor.getString(cursor.getColumnIndex(DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE)));
                    credit.setName(name);
                    credit.setTk(cursor.getDouble(cursor.getColumnIndex(DatabaseConstant.MealDebitCreditTB.COL.KEY_TK)));

                    credits.add(credit);

                } while (cursor.moveToNext());

            }


        } catch (Exception e){


        } finally {
            if(cursor != null)
                cursor.close();
        }


        return credits;

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

                String sql = "SELECT SUM(" + DatabaseConstant.MealDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " WHERE " +
                        DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ? AND " +
                        DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME + " = ?;";
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

    // view last date for homeFragment
    public Credit getLastDateAndName(String month, String year){


        Credit credit = new Credit();

        String sql = "SELECT "+DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE+","+DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME+","+ DatabaseConstant.MealDebitCreditTB.COL.KEY_TK+" FROM "+ DatabaseConstant.MealDebitCreditTB.NAME +" WHERE "
                + DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH +" = ? AND "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR +" = ? ORDER BY "+ DatabaseConstant.MealDebitCreditTB.COL.KEY_ID +" DESC LIMIT 1";

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


        MemberDataSource memberDataSource = MemberDataSource.getInstance(this.context);

        int persons = memberDataSource.getMembers(1).size();

        if(persons > 0) {

            String sql = "SELECT SUM(" + DatabaseConstant.MealDebitCreditTB.COL.KEY_TK + ") FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " WHERE " +
                    DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ?";


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

        String sql = "SELECT " + DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME + " FROM " + DatabaseConstant.MealDebitCreditTB.NAME + " WHERE " +
                DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR + " = ?";

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
