package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.models.MemberMealInfo;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 11/11/16.
 */

public class CustomMemberMealAdapter extends ArrayAdapter<MemberMealInfo> {

    private TextView tvCMMRPName,tvCMMRPMealNo;

    private Context context;

    public CustomMemberMealAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomMemberMealAdapter(Context context, int resource, List<MemberMealInfo> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_member_meal_row, null);
        }

        MemberMealInfo memberMealInfo = getItem(position);

        String memberName = memberMealInfo.getmName();
        int mealNo = memberMealInfo.getMealNo();

        tvCMMRPName = (TextView) v.findViewById(R.id.tvCMMRPName);

        tvCMMRPMealNo = (TextView) v.findViewById(R.id.tvCMMRPMealNo);


        if(memberName != null && !memberName.equals("")){
            tvCMMRPName.setText(memberName);
            tvCMMRPMealNo.setText(mealNo+"");
        } else{
            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
        }




        return v;
    }


}