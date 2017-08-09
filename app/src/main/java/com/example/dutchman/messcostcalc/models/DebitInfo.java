package com.example.dutchman.messcostcalc.models;

/**
 * Created by dutchman on 10/8/16.
 */
public class DebitInfo {

    private Credit credit;
    private double debit;
    private double balance;


    public DebitInfo(Credit credit, double debit, double balance){

        this.credit = credit;
        this.debit = debit;
        this.balance = balance;

    }

    public Credit getCredit() {
        return credit;
    }

    public double getDebit() {
        return debit;
    }

    public double getBalance() {
        return balance;
    }
}
