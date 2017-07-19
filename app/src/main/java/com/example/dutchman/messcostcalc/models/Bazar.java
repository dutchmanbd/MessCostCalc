package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 7/13/17.
 */

public class Bazar {

    private int id;
    private String date;
    private String month;
    private String year;
    private String mName;
    private double tk;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getTk() {
        return tk;
    }

    public void setTk(double tk) {
        this.tk = tk;
    }
}
