package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.models.Credit;
import com.example.dutchman.messcostcalc.models.DebitInfo;

import java.util.List;

/**
 * Created by dutchman on 8/8/17.
 */

public class RentDebitCreditAdapter extends ArrayAdapter<DebitInfo> {


    public RentDebitCreditAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DebitInfo> debitInfos) {
        super(context, resource, debitInfos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_meal_credit_item, parent, false);
        }

        DebitInfo debitInfo = getItem(position);

        Credit credit = debitInfo.getCredit();

        double debit = debitInfo.getDebit();
        double balance = debitInfo.getBalance();

        String creditString = String.format("%.2f",credit.getTk());
        String debitString = String.format("%.2f",debit);
        String balanceString = String.format("%.2f",balance);

        TextView tvSingleMemberName = (TextView) listItemView.findViewById(R.id.tvMealDebitName);

        tvSingleMemberName.setText(credit.getName());

        TextView tvMealDebitCreditTk = (TextView) listItemView.findViewById(R.id.tvMealDebitCreditTk);

        tvMealDebitCreditTk.setText(creditString);

        TextView tvMealDebitDebitTk = (TextView) listItemView.findViewById(R.id.tvMealDebitDebitTk);

        tvMealDebitDebitTk.setText(debitString);

        TextView tvMealDebitBalance = (TextView) listItemView.findViewById(R.id.tvMealDebitBalance);

        tvMealDebitBalance.setText(balanceString);

        return listItemView;
    }
}
