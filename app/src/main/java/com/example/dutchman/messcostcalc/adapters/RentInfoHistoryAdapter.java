package com.example.dutchman.messcostcalc.adapters;



import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.models.Calculator;
import com.example.dutchman.messcostcalc.R;

import java.util.List;


/**
 * Created by dutchman on 8/10/17.
 */

public class RentInfoHistoryAdapter extends ArrayAdapter<Calculator> {


    private TextView tvCRHRent, tvCRHGusCurrent, tvCRHServent, tvCRHNet, tvCRHPaper, tvCRHDirst, tvCRHOther, tvCRHMembers, tvCRHTotal, tvCRHPerhead;


    public RentInfoHistoryAdapter(Context context, int resource) {
        super(context, resource);
    }

    public RentInfoHistoryAdapter(Context context, int resource, List<Calculator> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_rent_history, null);
        }

        Calculator calculator = getItem(position);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        width /= 4;

        if (calculator != null) {


            tvCRHRent = (TextView) v.findViewById(R.id.tvCRHRent);
            tvCRHGusCurrent = (TextView) v.findViewById(R.id.tvCRHGusCurrent);
            tvCRHServent = (TextView) v.findViewById(R.id.tvCRHServent);
            tvCRHNet = (TextView) v.findViewById(R.id.tvCRHNet);
            tvCRHPaper = (TextView) v.findViewById(R.id.tvCRHPaper);
            tvCRHDirst = (TextView) v.findViewById(R.id.tvCRHDirst);
            tvCRHOther = (TextView) v.findViewById(R.id.tvCRHOthers);
            tvCRHMembers = (TextView) v.findViewById(R.id.tvCRHMembers);
            tvCRHTotal = (TextView) v.findViewById(R.id.tvCRHTotal);
            tvCRHPerhead = (TextView) v.findViewById(R.id.tvCRHPerhead);


//            imageview.getLayoutParams().height=height;

//            tvCMHDate.getLayoutParams().width = width;
//            tvCMHName.getLayoutParams().width = width;
//            tvCMHTk.getLayoutParams().width = width;

            if (tvCRHRent != null) {
                tvCRHRent.setText("House Rent: " + calculator.getHouseRent());

            }

            if (tvCRHGusCurrent != null) {
                tvCRHGusCurrent.setText("Gus Current: " + calculator.getGusCurrent());

            }

            if (tvCRHServent != null) {
                tvCRHServent.setText("Servent: " + calculator.getServent());
            }

            if (tvCRHNet != null) {
                tvCRHNet.setText("Internet: " + calculator.getInternet());

            }

            if (tvCRHPaper != null) {
                tvCRHPaper.setText("Paper: " + calculator.getPaper());

            }

            if (tvCRHDirst != null) {
                tvCRHDirst.setText("Dirst: " + calculator.getDirst());
            }

            if (tvCRHOther != null) {
                tvCRHOther.setText("Others: " + calculator.getOthers());
            }

            if (tvCRHMembers != null) {
                tvCRHMembers.setText("Members: " + calculator.getMembers());

            }

            if (tvCRHPerhead != null) {
                tvCRHPerhead.setText("Perhead: " + calculator.getPerhead());

            }

            if (tvCRHTotal != null) {
                tvCRHTotal.setText("Total: " + calculator.getTotal());
            }


        }

        return v;
    }
}
