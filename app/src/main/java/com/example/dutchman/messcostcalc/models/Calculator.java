package com.example.dutchman.messcostcalc.models;

import java.util.Calendar;

/**
 * Created by dutchman on 7/30/16.
 */
public class Calculator {

    private int houseRent;
    private int gusCurrent;
    private int servent;
    private int internet;
    private int paper;
    private int dirst;
    private int others;
    private int members;

    private int total;
    private int perhead;


    public Calculator(){

    }

    public Calculator(String hr, String gc, String st, String it, String pr, String dt, String os, String ms){

        houseRent = Integer.parseInt(hr);

        gusCurrent = Integer.parseInt(gc);

        servent = Integer.parseInt(st);

        internet = Integer.parseInt(it);

        paper = Integer.parseInt(pr);

        dirst = Integer.parseInt(dt);

        others = Integer.parseInt(os);

        members = Integer.parseInt(ms);


        calculate();
    }


    public Calculator(String hr, String gc, String st, String it, String pr, String dt, String os, String ms, String tt, String pp){

        houseRent = Integer.parseInt(hr);

        gusCurrent = Integer.parseInt(gc);

        servent = Integer.parseInt(st);

        internet = Integer.parseInt(it);

        paper = Integer.parseInt(pr);

        dirst = Integer.parseInt(dt);

        others = Integer.parseInt(os);

        members = Integer.parseInt(ms);

        total = Integer.parseInt(tt);

        perhead = Integer.parseInt(pp);
    }


    public int getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(int houseRent) {
        this.houseRent = houseRent;
    }

    public int getGusCurrent() {
        return gusCurrent;
    }

    public void setGusCurrent(int gusCurrent) {
        this.gusCurrent = gusCurrent;
    }

    public int getServent() {
        return servent;
    }

    public void setServent(int servent) {
        this.servent = servent;
    }

    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public int getDirst() {
        return dirst;
    }

    public void setDirst(int dirst) {
        this.dirst = dirst;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerhead() {
        return perhead;
    }

    public void setPerhead(int perhead) {
        this.perhead = perhead;
    }

    public void calculate(){

        total = getHouseRent() + getGusCurrent() + getServent() + getInternet() + getPaper() + getDirst() + getOthers();

        perhead = total / getMembers() + 1;


    }
}
