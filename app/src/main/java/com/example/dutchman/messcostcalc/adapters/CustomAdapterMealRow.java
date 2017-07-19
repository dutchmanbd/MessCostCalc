package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.models.MemberInfo;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 10/22/16.
 */

public class CustomAdapterMealRow extends ArrayAdapter<MemberInfo> {

    private TextView tvCMRMealDate,tvCMRTotalMeal;

    private Context context;


    public CustomAdapterMealRow(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapterMealRow(Context context, int resource, List<MemberInfo> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_meal_row, null);
        }

        MemberInfo memberInfo = getItem(position);

        String date = memberInfo.getDate();
        int mealNo = memberInfo.getMealNo();

        tvCMRMealDate = (TextView) v.findViewById(R.id.tvCMRMealDate);

        tvCMRTotalMeal = (TextView) v.findViewById(R.id.tvCMRTotalMeal);


        if(date != null && !date.equals("")){
            tvCMRMealDate.setText(date);
            tvCMRTotalMeal.setText(mealNo+"");
        } else{
            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
        }

        return v;
    }


}