package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 10/8/16.
 */
public class DebitInfo {


    private String pName;
    private int pCredit;
    private int pDebit;
    private int pBalance;


    public DebitInfo(){

    }

    public DebitInfo(String pName,int pCredit, int pDebit, int pBalance){

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

    public int getpCredit() {
        return pCredit;
    }

    public void setpCredit(int pCredit) {
        this.pCredit = pCredit;
    }

    public int getpDebit() {
        return pDebit;
    }

    public void setpDebit(int pDebit) {
        this.pDebit = pDebit;
    }

    public int getpBalance() {
        return pBalance;
    }

    public void setpBalance(int pBalance) {
        this.pBalance = pBalance;
    }
}
