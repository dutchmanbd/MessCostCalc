package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 1/11/17.
 */

public class CustomUpdateMonthAdapter extends ArrayAdapter<String> {

    public CustomUpdateMonthAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomUpdateMonthAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_month_list,null);
        }

        //PersonCredit personCredit = getItem(position);
//
//        TextView tvCCDIDate = (TextView) v.findViewById(R.id.tvCCDIDate);
//        TextView tvCCDICredit = (TextView) v.findViewById(R.id.tvCCDICredit);
//
//        tvCCDIDate.setText(personCredit.getDate());
//        tvCCDICredit.setText(personCredit.getCredit());

        return v;
    }
}
