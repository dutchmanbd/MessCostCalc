package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 10/8/16.
 */
public class DebitInfo {


    private String pName;
    private double pCredit;
    private double pDebit;
    private double pBalance;


    public DebitInfo(){

    }

    public DebitInfo(String pName,double pCredit, double pDebit, double pBalance){

        this.pName = pName;
        this.pCredit = pCredit;
        this.pDebit = pDebit;
        this.pBalance = pBalance;

    }

    public String getPersonName() {
        return pName;
    }

    public void setPersonName(String pName) {
        this.pName = pName;
    }

    public double getpCredit() {
        return pCredit;
    }

    public void setpCredit(int pCredit) {
        this.pCredit = pCredit;
    }

    public double getpDebit() {
        return pDebit;
    }

    public void setpDebit(int pDebit) {
        this.pDebit = pDebit;
    }

    public double getpBalance() {
        return pBalance;
    }

    public void setpBalance(int pBalance) {
        this.pBalance = pBalance;
    }
}
