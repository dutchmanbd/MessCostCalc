package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 7/14/17.
 */

public class Meal {

    private int id;
    private String date;
    private String month;
    private String year;
    private String name;
    private double meal;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMeal() {
        return meal;
    }

    public void setMeal(double meal) {
        this.meal = meal;
    }
}
