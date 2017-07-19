package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 11/11/16.
 */
public class MemberMealInfo {

    private String mName;
    private int mealNo;

    public MemberMealInfo(){

    }
    public MemberMealInfo(String mName, int mealNo){

        this.mName = mName;
        this.mealNo = mealNo;
    }

    public String getmName() {
        return mName;
    }

    public int getMealNo() {
        return mealNo;
    }
}
