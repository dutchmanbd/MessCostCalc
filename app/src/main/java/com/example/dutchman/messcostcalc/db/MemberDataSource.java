package com.example.dutchman.messcostcalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;
import com.example.dutchman.messcostcalc.models.Member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.hand_hour;
import static android.R.attr.name;

/**
 * Created by dutchman on 7/11/17.
 */

public class MemberDataSource extends DatabaseDAO{

    private Context context;
    private static MemberDataSource memberDataSource;

    private MemberDataSource(Context context){
        super(context);
        this.context = context;

    }

    public static MemberDataSource getInstance(Context context){

        if(memberDataSource == null)
            memberDataSource = new MemberDataSource(context);

        return memberDataSource;
    }

    public boolean insertMember(Member member){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MemberTB.COL.KEY_DATE, member.getDate());
        values.put(DatabaseConstant.MemberTB.COL.KEY_MONTH, member.getMonth());
        values.put(DatabaseConstant.MemberTB.COL.KEY_YEAR, member.getYear());
        values.put(DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME, member.getName());                                    // Person Name
        values.put(DatabaseConstant.MemberTB.COL.KEY_ADVANCE_TK, member.getAdvanceTk());                                // Person TK
        values.put(DatabaseConstant.MemberTB.COL.KEY_IS_AVAILABLE, member.getIsAvailable());

        // Inserting Row
        long res = database.insert(DatabaseConstant.MemberTB.NAME, null, values);

        if(res == -1)
            return false;
        else
            return true;
    }

    public Member getMember(String month, String year){

        Cursor cursor = null;
        Member member = null;

        try {
            cursor = database.query(DatabaseConstant.MemberTB.NAME,
                    new String[]{
                            DatabaseConstant.MemberTB.COL.KEY_ID,
                            DatabaseConstant.MemberTB.COL.KEY_DATE,
                            DatabaseConstant.MemberTB.COL.KEY_MONTH,
                            DatabaseConstant.MemberTB.COL.KEY_YEAR,
                            DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME,
                            DatabaseConstant.MemberTB.COL.KEY_ADVANCE_TK},
                    DatabaseConstant.MemberTB.COL.KEY_MONTH + " = " + month + " and " +
                            DatabaseConstant.MemberTB.COL.KEY_YEAR + " = " + year,
                    null, null, null, null);

            cursor.moveToFirst();
            member = toMember(cursor);
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return member;
    }

    public Member getMember(int id){

        String sql = "Select * from "+ DatabaseConstant.MemberTB.NAME + " where "+ DatabaseConstant.MemberTB.COL.KEY_ID + " = ?;";

        Cursor cursor = null;
        Member member = null;
        try {
            cursor = database.rawQuery(sql, new String[]{id + ""});

            if (cursor != null) {
                cursor.moveToFirst();
                member = toMember(cursor);
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return member;
    }

    public List<Member> getMembers(int isAvailable){

        List<Member> members = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MemberTB.NAME + " where " + DatabaseConstant.MemberTB.COL.KEY_IS_AVAILABLE + " = ?", new String[]{isAvailable + ""});

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    Member member = toMember(cursor);
                    members.add(member);

                } while (cursor.moveToNext());

            }

            if(cursor != null)
                cursor.close();

        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }
        return members;

    }


    public List<String> getMembersName(int isAvailable){


        List<String> membersName = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MemberTB.NAME + " where " + DatabaseConstant.MemberTB.COL.KEY_IS_AVAILABLE + " = ?;", new String[]{isAvailable + ""});

            if (cursor.moveToFirst()) {

                do {

                    Member member = toMember(cursor);
                    membersName.add(member.getName());

                } while (cursor.moveToNext());

            }
        } catch (Exception e){


        } finally {
            if(cursor != null)
                cursor.close();
        }

        return membersName;
    }

    public List<String> getMembersName(String month, String year){


        List<String> membersName = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseConstant.MemberTB.NAME + " where " +
                    DatabaseConstant.MemberTB.COL.KEY_MONTH + " = ? AND " + DatabaseConstant.MemberTB.COL.KEY_YEAR + " = ?; ", new String[]{month, year});

            if (cursor.moveToFirst()) {

                do {

                    Member member = toMember(cursor);
                    membersName.add(member.getName());

                } while (cursor.moveToNext());

            }
            if(cursor != null){
                cursor.close();
            }

        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }

        return membersName;
    }



    public boolean isMemberExists(String name, int isAvailable){

        // Select All Query
        String selectQuery = "SELECT "+ DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME+" FROM " + DatabaseConstant.MemberTB.NAME +
                " WHERE "+ DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME +" = ? AND "+ DatabaseConstant.MemberTB.COL.KEY_IS_AVAILABLE +" = ?;";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectQuery, new String[]{name, isAvailable + ""});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                if (cursor.getString(0).equals(name)) {
                    return true;
                }
            }
        } catch (Exception e){

        } finally {
            if(cursor != null)
                cursor.close();
        }
        return false;
    }

    public boolean isUpdateMember(Member member){

        ContentValues values = new ContentValues();

        values.put(DatabaseConstant.MemberTB.COL.KEY_DATE, member.getDate());
        values.put(DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME, member.getName());                                    // Person Name
        values.put(DatabaseConstant.MemberTB.COL.KEY_ADVANCE_TK, member.getAdvanceTk());
        values.put(DatabaseConstant.MemberTB.COL.KEY_IS_AVAILABLE, member.getIsAvailable());

        int res = database.update(DatabaseConstant.MemberTB.NAME, values, DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME+" = ?", new String[]{member.getName()});

        return res > 0;
    }

    public boolean isDeleteMember(String name){

        int res = database.delete(DatabaseConstant.MemberTB.NAME, DatabaseConstant.MemberTB.COL.KEY_MEMBER_NAME+" = ?",new String[]{name});

        return res > 0;
    }

    public Member toMember(Cursor cursor){

        Member member = new Member();
        member.setId(cursor.getInt(0));
        member.setDate(cursor.getString(1));
        member.setMonth(cursor.getString(2));
        member.setYear(cursor.getString(3));
        member.setName(cursor.getString(4));
        member.setAdvanceTk(cursor.getDouble(5));
        member.setIsAvailable(cursor.getInt(6));

        return member;
    }


}
