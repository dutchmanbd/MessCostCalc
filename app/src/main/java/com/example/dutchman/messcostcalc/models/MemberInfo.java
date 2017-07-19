package com.example.dutchman.messcostcalc.models;

import java.util.List;

/**
 * Created by dutchman on 10/9/16.
 */
public class MemberInfo {

    private String date;
    private String pName;
    private String pTk;
    private int total;

    private String month;
    private String year;

    private List<String> meals;
    private List<String> nameList;

    private int mealNo;

    public MemberInfo(){


    }

    public MemberInfo(String pName){
        this.pName = pName;
    }

    public MemberInfo(String pName, String pTk){

        this.pName = pName;
        this.pTk = pTk;
    }

    public MemberInfo(String date, int mealNo){
        this.date = date;
        this.mealNo = mealNo;
    }

    public MemberInfo(String date, String pName, String pTk){

        this.date = date;
        this.pName = pName;
        this.pTk = pTk;
    }

    public MemberInfo(String date, String pName, String pTk, int total){

        this.date = date;
        this.pName = pName;
        this.pTk = pTk;

        this.total = total;
    }

    public MemberInfo(String date, String month, String year, String pName, String pTk, int total){

        this.date = date;
        this.month = month;
        this.year = year;
        this.pName = pName;
        this.pTk = pTk;

        this.total = total;
    }

    public MemberInfo(List<String> nameList,String date,String month,String year,List<String> meals){

        this.nameList = nameList;
        this.date = date;
        this.month = month;
        this.year = year;
        this.meals = meals;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public String getDate() {

        return date;
    }

    public int getMealNo() {
        return mealNo;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getpTk() {
        return pTk;
    }

    public String getpName() {
        return pName;
    }
    public int getTotal() {
        return total;
    }

    public List<String> getMeals() {
        return meals;
    }
}
