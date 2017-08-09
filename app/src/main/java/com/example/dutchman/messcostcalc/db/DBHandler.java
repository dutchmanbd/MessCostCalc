package com.example.dutchman.messcostcalc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dutchman.messcostcalc.constants.DatabaseConstant;

/**
 * Created by dutchman on 10/8/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private Context context;

    // Database Name
    private static final String DATABASE_NAME       = "messCostInfo";

    // table name
    private static final String TB_MEMBER_INFO      = "member_info";

    private static final String TB_BAZAR_INFO       = "bazar_info";
    private static final String TB_BAZAR_PER_CREDIT = "bazar_per_credit";

    private static final String TB_MEAL_INFO        = "meal_info";

    private static final String TB_RENT_INFO        = "rent_info";
    private static final String TB_RENT_PER_CREDIT  = "rent_per_credit";

    private static final String TB_MEMBERS          = "members";

    // Table Columns names
    //For BazarInfo Table
    private static final String KEY_ADVANCE_TK   = "advance_tk";
    private static final String KEY_ID           = "id";
    private static final String KEY_DATE         = "date";
    private static final String KEY_NAME         = "person_name";
    private static final String KEY_TK           = "tk";

    //For Person Credit table
    private static final String KEY_CREDIT       = "credit";

    //For Rent Info table

    private static final String KEY_MONTH        = "month";
    private static final String KEY_YEAR         = "year";
    private static final String KEY_H_RENT       = "house_rent";
    private static final String KEY_GUS_CURRENT  = "gus_current";
    private static final String KEY_SERVENT      = "servent";
    private static final String KEY_NET_BILL     = "net_bill";
    private static final String KEY_PAPER        = "paper";
    private static final String KEY_DIRST        = "dirst";
    private static final String KEY_OTHERS       = "others";
    private static final String KEY_MEMBERS      = "members";
    private static final String KEY_TOTAL_RENT   = "total_rent";
    private static final String KEY_PERHEAD      = "perhead";

    private static final String KEY_IS_AVAILABLE = "isAvailable";
    private static final String KEY_MEAL         = "meal";

    private static final String KEY_NUMBER       = "number";

    // Sql Command create table


    private static final String SQL_TB_MEMBER_INFO = "CREATE TABLE "+ TB_MEMBER_INFO +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT," + KEY_MONTH+" TEXT,"+KEY_YEAR+" TEXT,"+
            KEY_NAME +" TEXT,"+
            KEY_ADVANCE_TK+" DOUBLE," + KEY_IS_AVAILABLE +" INTEGER" + ");";

    private static final String SQL_TB_BAZAR_INFO = "CREATE TABLE "+ TB_BAZAR_INFO+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_TK +" DOUBLE" + ");";

    private static final String SQL_TB_BAZAR_PER_CREDIT = "CREATE TABLE "+ DatabaseConstant.MealDebitCreditTB.NAME +"("+
            DatabaseConstant.MealDebitCreditTB.COL.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            DatabaseConstant.MealDebitCreditTB.COL.KEY_DATE +" TEXT,"+ DatabaseConstant.MealDebitCreditTB.COL.KEY_MONTH +" TEXT,"+
            DatabaseConstant.MealDebitCreditTB.COL.KEY_YEAR +" TEXT,"+ DatabaseConstant.MealDebitCreditTB.COL.KEY_MEMBER_NAME +" TEXT,"+
            DatabaseConstant.MealDebitCreditTB.COL.KEY_TK +" DOUBLE" + ");";


    private static final String SQL_TB_RENT_INFO = "CREATE TABLE "+ TB_RENT_INFO +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_H_RENT +" INTEGER,"+ KEY_GUS_CURRENT +" INTEGER,"+ KEY_SERVENT +" INTEGER,"+ KEY_NET_BILL +" INTEGER,"+ KEY_PAPER +" INTEGER,"+KEY_DIRST+" INTEGER,"
            + KEY_OTHERS +
            " INTEGER,"+ KEY_MEMBERS +" INTEGER,"+ KEY_TOTAL_RENT +" INTEGER,"+ KEY_PERHEAD +" INTEGER" + ");";

    private static final String SQL_TB_RENT_PER_CREDIT = "CREATE TABLE "+ TB_RENT_PER_CREDIT +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_TK +" DOUBLE" + ");";


    private static final String SQL_TB_MEAL_INFO = "CREATE TABLE "+ TB_MEAL_INFO+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_MEAL +" DOUBLE" + ");";

    private static final String SQL_TB_MEMBERS = "CREATE TABLE "+ TB_MEMBERS+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NUMBER +" INTEGER" + ");";

    private static DBHandler handler;

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DBHandler getInstance(Context context){

        if(handler == null)
            handler = new DBHandler(context);

        return handler;

    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_TB_MEMBER_INFO);

        db.execSQL(SQL_TB_BAZAR_INFO);
        db.execSQL(SQL_TB_BAZAR_PER_CREDIT);

        db.execSQL(SQL_TB_RENT_INFO);
        db.execSQL(SQL_TB_RENT_PER_CREDIT);

        db.execSQL(SQL_TB_MEAL_INFO);

        //db.execSQL(SQL_TB_MEMBERS);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TB_MEMBER_INFO);

        db.execSQL("DROP TABLE IF EXISTS " + TB_BAZAR_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstant.MealDebitCreditTB.NAME);

        db.execSQL("DROP TABLE IF EXISTS " + TB_RENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_RENT_PER_CREDIT);

        db.execSQL("DROP TABLE IF EXISTS "+ TB_MEAL_INFO);

        //db.execSQL("DROP TABLE IF EXISTS "+ TB_MEMBERS);

        // Create tables again
        onCreate(db);
    }


//    // Get number of members
//
//    public int getNumberOfMembers(){
//
//        String isAvail = "1";
//
//        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?;";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        int persons = 0;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{isAvail});
//
//            if (cursor.moveToFirst())
//                persons = cursor.getCount();
//        } catch(Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//            if(db != null)
//                db.close();
//        }
//
//        return persons;
//    }
//
//
//
//
//
//
//    // Get Number of member in specefic month and year
//
//    public int getNumberOfMembers(String type,String month,String year){
//
//
//        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
//        String currYear = new SimpleDateFormat("yyyy").format(new Date());
//
//        int persons = 0;
//
//        if(currMonth.equals(month) && currYear.equals(year)){
//
//            persons = getNumberOfMembers();
//
//            return persons;
//
//        } else{
//
//            if(type.equals("Meal")){
//
//
//                persons = getNumberOfMembersFromMeal(month,year);
//
//            } else if(type.equals("Rent")){
//
//                persons = getNumberOfMembersFromRent(month,year);
//            }
//
//        }
//
//        return persons;
//    }
//
//
//    // Get Number of member in specefic month and year
//
//    public int getNumberOfMembersFromMeal(String month,String year){
//
//
//        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?;";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        int persons = 0;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{month, year});
//            if (cursor.moveToFirst())
//                persons = cursor.getCount();
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return persons;
//    }
//
//
//
//    // Get Number of member in specefic month and year
//
//    public int getNumberOfMembersFromRent(String month,String year){
//
//
//        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?;";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        int persons = 0;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{month, year});
//
//            if (cursor.moveToFirst())
//                persons = cursor.getCount();
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//
//        return persons;
//    }
//
//
//
//
//    // GET Member info
//
//    public List<MemberInfo> getMembers(){
//
//        List<MemberInfo> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TB_MEMBER_INFO;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    MemberInfo memberInfo = new MemberInfo(cursor.getString(1), cursor.getString(4), cursor.getString(5));
//
//                    list.add(memberInfo);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return list;
//    }
//
//    // get Member Name
//    public List<String> getMNameList(){
//
//        List<String> list = new ArrayList<>();
//
//        String isAvail = "1";
//
//        // Select All Query
//        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?;";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{isAvail});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//
//
//                    list.add(cursor.getString(0));
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return list;
//    }
//
//    // Member List by date
//
//    public List<String> getMNameList(String date){
//
//        List<String> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEAL_INFO+" WHERE "+KEY_DATE +" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{date});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//
//
//                    list.add(cursor.getString(0));
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//        return list;
//    }
//
//    public boolean isMemberExists(String name){
//
//        String isAvail = "1";
//
//        // Select All Query
//        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_NAME+" = ? AND "+KEY_IS_AVAILABLE +" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{name, isAvail});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//
//                if (cursor.getString(0).equals(name)) {
//                    return true;
//                }
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//
//        return false;
//    }
//
//
//
//
//    public boolean isUpdateMember(String date, String name, String advancedTk, String isAvail){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(KEY_DATE, date);
//        values.put(KEY_NAME, name);                                    // Person Name
//        values.put(KEY_ADVANCE_TK, Integer.parseInt(advancedTk));     // Person TK
//        values.put(KEY_IS_AVAILABLE, Integer.parseInt(isAvail));
//
//        int res = db.update(TB_MEMBER_INFO, values,KEY_NAME+" = ?", new String[]{name});
//
//        db.close();
//
//        return res > 0;
//    }
//
//    public boolean isDeleteMember(String name){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        int res = db.delete(TB_MEMBER_INFO,KEY_NAME+" = ?",new String[]{name});
//
//        db.close();
//
//        return res > 0;
//    }


    // END TB_MEMBER_INFO


    // Start TB_BAZAR_INFO

    // Date,Month,Year,Name,Tk

    // view last date for homeFragment

//    public MemberInfo getLastDateAndName(String month, String year){
//
//
//        MemberInfo memberInfo = null;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        //SELECT ROWID from MYTABLE order by ROWID DESC limit 1
//
//        String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+" FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? ORDER BY "+ KEY_ID +" DESC LIMIT 1";
//
//        Cursor cursor = db.rawQuery(sql,new String[]{month,year});
//
//
//        if(cursor == null)
//            return null;
//
//
//        if(cursor.moveToFirst()){
//
//            memberInfo = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//
//        }
//        return memberInfo;
//    }
//
//
//    public int getTotalBazar(String month,String year){
//
//        int totalCost = 0;
//
//        int persons = getNumberOfMembers();
//
//        if(persons > 0) {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//
//            //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;
//
//            String sql = "SELECT SUM(" + KEY_TK + ") FROM " + TB_BAZAR_INFO + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";
//
//            Cursor cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//
//                totalCost = cursor.getInt(0);
//
//            }
//            return totalCost;
//
//        } else{
//            return totalCost;
//        }
//    }



//
//    public double getBazarPerCost(String month,String year){
//
//        double personCost = 0.0;
//
//
//        int persons = getNumberOfMembers();
//
//        if(persons > 0) {
//
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//
//            //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;
//
//            String sql = "SELECT SUM(" + KEY_TK + ") FROM " + TB_BAZAR_INFO + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";
//
//            Cursor cursor = null;
//            double totalCost = 0.0;
//            try {
//                cursor = db.rawQuery(sql, new String[]{month, year});
//
//                if (cursor.moveToFirst()) {
//
//                    totalCost = cursor.getDouble(0);
//
//                }
//            } catch (Exception e){
//
//            } finally {
//                if(cursor != null)
//                    cursor.close();
//            }
//
//            personCost = (totalCost / persons);
//
//            return Math.ceil(personCost);
//
//        } else{
//            return personCost;
//        }
//
//
//    }


//    //Call
//    public double getTotalBazarTk(String month, String year){
//
//        double sumOfBazar = 0;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//
//        String sql = "SELECT "+"SUM("+KEY_TK+") FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//
//                sumOfBazar = cursor.getDouble(0);
//
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return sumOfBazar;
//    }

    // For Meal History
    // Show Bazar Info
    // Date,Month,Year,Name,Tk
//
//    public List<MemberInfo> getBazarInfo(String month, String year){
//
//        List<MemberInfo> list = new ArrayList<>();
//        MemberInfo memberInfo;
//
//        double sumOfBazar = getTotalBazarTk(month,year);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//
//        //String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+",SUM("+KEY_TK+") FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//        String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+" FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//                do {
//                    //memberInfo = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
//
//                    memberInfo = new MemberInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), sumOfBazar);
//                    list.add(memberInfo);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//        return list;
//    }




    // END TB_BAZAR_INFO


    // Start TB_BAZAR_PER_CREDIT

    // Date,Month,Year,Name,Tk

//    public boolean insertPersonCredit(String type,String date, String month, String year, String name, String tk){
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(KEY_DATE, date);
//        values.put(KEY_MONTH, month);
//        values.put(KEY_YEAR, year);
//        values.put(KEY_NAME, name);                            // Person Name
//        values.put(KEY_CREDIT, Integer.parseInt(tk));     // Person TK
//
//        // Inserting Row
//        long res = -1;
//        if(type.equals("meal")){
//
//            //res = db.insert(TB_BAZAR_PER_CREDIT, null, values);
//
//        } else if(type.equals("rent")){
//            res = db.insert(TB_RENT_PER_CREDIT, null, values);
//        }
//
//        db.close(); // Closing database connection
//
//        if(res == -1)
//            return false;
//        else
//            return true;
//
//    }

//
//    public boolean isMemberExists(){
//
//
//        String isAvail = "1";
//
//        // Select All Query
//        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        int persons = 0;
//
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{isAvail});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//
//                persons = 1;//cursor.getCount();
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return persons > 0;
//
//    }



//    //Search memeber by name
//    public boolean isMemberExistForSearch(String type, String month, String year, String name){
//
//        boolean isExist;
//
//        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
//        String currYear = new SimpleDateFormat("yyyy").format(new Date());
//
//
//        if(month.equals(currMonth) && year.equals(currYear)){
//
//            isExist = isMemberExists(name);
//
//        } else{
//
//            isExist = isMemberExists(type,month,year,name);
//
//        }
//
//
//        return isExist;
//
//    }



    //isMemeberExists in month and year
//
//    public boolean isMemberExists(String type, String month, String year, String name){
//
//        // Select All Query
//        String selectQuery = "";
//        if(type.equals("meal"))
//            selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? AND "+ KEY_NAME +" = ?";
//        else if(type.equals("rent"))
//            selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? AND "+ KEY_NAME +" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{month, year, name});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//
//                if (cursor.getString(0).equals(name)) {
//                    return true;
//                }
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return false;
//
//    }


    //search data result
//
//    public int searchPaidTakaByName(String type, String month, String year, String name){
//
//        // Select All Query
//        String sql="";
//        int pCost = 0;
//
//        if(type.equals("meal"))
//
//            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//        else if(type.equals("rent"))
//
//            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year, name});
//
//
//            if (cursor.moveToFirst()) {
//
//                pCost = cursor.getInt(0);
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return pCost;
//
//    }



    // Update Person Credit
//
//    public boolean isUpdatePersonCredit(String type,String date, String month, String year, String name, String tk){
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(KEY_DATE, date);
//        values.put(KEY_MONTH, month);
//        values.put(KEY_YEAR, year);
//        values.put(KEY_NAME, name);                            // Person Name
//        values.put(KEY_CREDIT, Integer.parseInt(tk));          // Person TK
//
//        // Updated Row
//        long res = -1;
//        if(type.equals("meal")){
//
//            //res = db.insert(TB_BAZAR_PER_CREDIT, null, values);
//            res = db.update(TB_BAZAR_PER_CREDIT, values,KEY_NAME+" = ?", new String[]{name});
//
//        } else if(type.equals("rent")){
//            //res = db.insert(TB_RENT_PER_CREDIT, null, values);
//            res = db.update(TB_RENT_PER_CREDIT, values,KEY_NAME+" = ?", new String[]{name});
//        }
//
//        db.close(); // Closing database connection
//
//        return res > 0;
//
//    }



    // For DebitFragment
//
//    public List<PersonCredit> getPersonCredit(String type){
//
//        List<PersonCredit> list = new ArrayList<>();
//        List<MemberInfo> nameList = new ArrayList<>();
//
//        String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_IS_AVAILABLE+" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{"1"});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    MemberInfo memberInfo = new MemberInfo(cursor.getString(0));
//
//                    nameList.add(memberInfo);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        db = this.getReadableDatabase();
//
//        String month = new SimpleDateFormat("MMMM").format(new Date());
//        String year = new SimpleDateFormat("yyyy").format(new Date());
//
//        try {
//
//            for (MemberInfo memberInfo : nameList) {
//
//                //String sql = "SELECT "+KEY_CREDIT+" FROM "+TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH+"="+month+" AND "+KEY_YEAR+"="+year+" AND "+KEY_NAME+"="+memberInfo.getpName();
//
//                String sql = "";
//                if (type.equals("meal")) {
//                    sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//                    cursor = db.rawQuery(sql, new String[]{month, year, memberInfo.getpName()});
//
//                } else if (type.equals("rent")) {
//
//                    sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//                    cursor = db.rawQuery(sql, new String[]{month, year, memberInfo.getpName()});
//
//                }
//
//                if (cursor.moveToFirst()) {
//                    do {
////                    PersonCredit personCredit = new PersonCredit(cursor.getString(0),month,year,memberInfo.getpName(),cursor.getString(1));
//
//                        PersonCredit personCredit = new PersonCredit(memberInfo.getpName(), cursor.getInt(0));
//                        //Toast.makeText(context,personCredit.getName()+" "+personCredit.getTk(),Toast.LENGTH_SHORT).show();
//
//
//                        list.add(personCredit);
//
//
//                    } while (cursor.moveToNext());
//                }
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//
//        return list;
//    }

    //DebitFragment item clicked

//    public List<PersonCredit> getPersonCreditDetails(String type,String month,String year,String name){
//
//        List<PersonCredit> list = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//
//        String sql="";
//
//        try {
//
//            if (type.equals("meal")) {
//                sql = "SELECT " + KEY_DATE + "," + KEY_CREDIT + " FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//                cursor = db.rawQuery(sql, new String[]{month, year, name});
//
//            } else if (type.equals("rent")) {
//
//                sql = "SELECT " + KEY_DATE + "," + KEY_CREDIT + " FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//
//                cursor = db.rawQuery(sql, new String[]{month, year, name});
//            }
//
//            if (cursor.moveToFirst()) {
//                do {
//                    PersonCredit personCredit = new PersonCredit(cursor.getString(0), Integer.toString(cursor.getInt(1)));
//                    list.add(personCredit);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return list;
//    }


//    public List<String> getNameOfMembers(){
//        List<String> nameList = new ArrayList<String>();
//
//        String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_IS_AVAILABLE+" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{"1"});
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(0);
//
//                    nameList.add(name);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//
//        return nameList;
//    }
//
//    public List<String> getNameOfMembersFromMeal(String month, String year){
//
//        List<String> nameList = new ArrayList<String>();
//
//
//        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(0);
//
//                    nameList.add(name);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return nameList;
//    }
//
//    public List<String> getNamerOfMembersFromRent(String month, String year){
//
//        List<String> nameList = new ArrayList<String>();
//
//        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(selectQuery, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(0);
//
//                    nameList.add(name);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return nameList;
//    }


    // Get Number of member in specefic month and year

//    public List<String> getNameOfMembers(String type,String month,String year){
//
//
//        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
//        String currYear = new SimpleDateFormat("yyyy").format(new Date());
//
//        List<String> nameList = null;
//
//        if(currMonth.equals(month) && currYear.equals(year)){
//
//            nameList = getNameOfMembers();
//
//
//        } else{
//
//            if(type.equals("Meal")){
//
//
//                nameList = getNameOfMembersFromMeal(month,year);
//
//
//            } else if(type.equals("Rent")){
//
//                nameList = getNamerOfMembersFromRent(month,year);
//
//            }
//
//        }
//
//        return nameList;
//
//    }




//    //For MealHistoryFragment
//
//    public List<PersonCredit> getPersonCreditForMHistory(String type, String month, String year){
//
//        List<PersonCredit> list = new ArrayList<>();
//        List<String> nameList = getNameOfMembers(type,month,year);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//
//        try {
//            for (String name : nameList) {
//
//                String sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//                cursor = db.rawQuery(sql, new String[]{month, year, name});
//
//                if (cursor.moveToFirst()) {
//                    do {
//                        PersonCredit personCredit = new PersonCredit(name, cursor.getInt(0));
//                        list.add(personCredit);
//
//                    } while (cursor.moveToNext());
//                }
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return list;
//    }
//
//
//
//    //For RentHistoryFragment
//
//    public List<PersonCredit> getPersonCreditForRHistory(String type, String month, String year){
//
//        List<PersonCredit> list = new ArrayList<>();
//        List<String> nameList = getNameOfMembers(type,month,year);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//
//        try {
//
//            for (String name : nameList) {
//
//                String sql = "";
//
//                sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
//                cursor = db.rawQuery(sql, new String[]{month, year, name});
//
//                if (cursor.moveToFirst()) {
//                    do {
//                        PersonCredit personCredit = new PersonCredit(name, cursor.getInt(0));
//                        list.add(personCredit);
//
//                    } while (cursor.moveToNext());
//                }
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return list;
//    }
//
//
//
//
//    // START TB_RENT_INFO
//
//    // MONTH, YEAR, H_RENT, GUS_CURRENT, SERVENT, NET, PAPER, OTHERS, MEMBERS,TOTAL_COST,PERHEAD
//
//    public boolean insertIntoRentInfo(Calculator calculator){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        String month = new SimpleDateFormat("MMMM").format(new Date());
//        String year = new SimpleDateFormat("yyyy").format(new Date());
//
//        values.put(KEY_MONTH,month);
//        values.put(KEY_YEAR,year);
//
//        values.put(KEY_H_RENT,calculator.getHouseRent());
//        values.put(KEY_GUS_CURRENT,calculator.getGusCurrent());
//        values.put(KEY_SERVENT,calculator.getServent());
//        values.put(KEY_NET_BILL,calculator.getInternet());
//        values.put(KEY_PAPER,calculator.getPaper());
//        values.put(KEY_DIRST,calculator.getDirst());
//        values.put(KEY_OTHERS,calculator.getOthers());
//        values.put(KEY_MEMBERS,calculator.getMembers());
//        values.put(KEY_TOTAL_RENT,calculator.getTotal());
//        values.put(KEY_PERHEAD,calculator.getPerhead());
//
//        // Inserting Row
//        long res = db.insert(TB_RENT_INFO, null, values);
//        db.close(); // Closing database connection
//
//        if(res == -1)
//            return false;
//        else
//            return true;
//
//
//    }
//
//
//    //View Total Cost For Rent
//
//    public int getTotalCostForRent(String month,String year){
//
//        int total = 0;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String sql = "SELECT "+ KEY_TOTAL_RENT +" FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//
//                total = cursor.getInt(0);
//
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return total;
//    }



    // view perhead cost For Rent

//    public int getPerheadCostFromRent(String month,String year){
//
//        int perhead = 0;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String sql = "SELECT "+ KEY_PERHEAD +" FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//
//                perhead = cursor.getInt(0);
//
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//
//        return perhead;
//    }


//    // view all data from rent
//
//    public List<Calculator> getRentCostInfo(String month,String year){
//
//        List<Calculator> calculators = new ArrayList<>();
//        Calculator calculator;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String sql = "SELECT * FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";
//
//        Cursor cursor = null;
//        try {
//            cursor = db.rawQuery(sql, new String[]{month, year});
//
//            if (cursor.moveToFirst()) {
//                do {
//                    calculator = new Calculator(cursor.getString(3), cursor.getString(4), cursor.getString(5),
//                            cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
//                            cursor.getString(10), cursor.getString(11), cursor.getString(12)
//                    );
//
//                    calculators.add(calculator);
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e){
//
//        } finally {
//            if(cursor != null)
//                cursor.close();
//        }
//        return calculators;
//    }


}