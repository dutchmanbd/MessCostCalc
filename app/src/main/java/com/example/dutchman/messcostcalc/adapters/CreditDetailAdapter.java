package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.models.Credit;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 11/15/16.
 */

public class CreditDetailAdapter extends ArrayAdapter<Credit> {

    public CreditDetailAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CreditDetailAdapter(Context context, int resource, List<Credit> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_credit_detail_item, null);
        }

        Credit personCredit = getItem(position);

        TextView tvCCDIDate = (TextView) v.findViewById(R.id.tvCCDIDate);
        TextView tvCCDICredit = (TextView) v.findViewById(R.id.tvCCDICredit);

        tvCCDIDate.setText(personCredit.getDate());

        String amount = String.format("%.2f",personCredit.getTk());

        tvCCDICredit.setText(amount);

        return v;
    }
}