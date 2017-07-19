package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 10/13/16.
 */
public class PersonCredit {

    private String date;
    private String month;
    private String year;
    private String name;
    private String credit;
    private int tk;

    public PersonCredit(){


    }

    public PersonCredit(String name,int tk){
        this.name = name;
        this.tk = tk;
    }

    public PersonCredit(String date,String credit){
        this.date = date;
        this.credit = credit;
    }

    public PersonCredit(String date, String month, String year, String name, int tk){

        this.date = date;
        this.month = month;
        this.year = year;
        this.name = name;
        this.tk = tk;
    }

    public String getDate() {
        return date;
    }

    public String getCredit() {
        return credit;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public int getTk() {
        return tk;
    }
}
