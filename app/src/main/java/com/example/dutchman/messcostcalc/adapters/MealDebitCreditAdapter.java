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

import java.util.List;

/**
 * Created by dutchman on 7/20/17.
 */

public class MealDebitCreditAdapter extends ArrayAdapter<Credit> {


    public MealDebitCreditAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Credit> credits) {
        super(context, resource, credits);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_meal_credit_item, parent, false);
        }

        Credit credit = getItem(position);

        TextView tvSingleMemberName = (TextView) listItemView.findViewById(R.id.tvMealDebitName);

        tvSingleMemberName.setText(credit.getName());

        TextView tvMealDebitCreditTk = (TextView) listItemView.findViewById(R.id.tvMealDebitCreditTk);

        tvMealDebitCreditTk.setText(credit.getTk()+"");

        TextView tvMealDebitDebitTk = (TextView) listItemView.findViewById(R.id.tvMealDebitDebitTk);

        tvMealDebitDebitTk.setText("" + credit.getTk());

        TextView tvMealDebitBalance = (TextView) listItemView.findViewById(R.id.tvMealDebitBalance);

        tvMealDebitDebitTk.setText("" + credit.getTk());

        return listItemView;
    }

}
