package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.models.MemberInfo;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 10/14/16.
 */
public class CustomAdapterMealHistory extends ArrayAdapter<MemberInfo> {

    public CustomAdapterMealHistory(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapterMealHistory(Context context, int resource, List<MemberInfo> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_meal_history, null);
        }

        MemberInfo memberInfo = getItem(position);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        width /= 4;

        if(memberInfo != null){

            TextView tvCMHDate = (TextView) v.findViewById(R.id.tvCMHDate);
            TextView tvCMHName = (TextView) v.findViewById(R.id.tvCMHName);
            TextView tvCMHTk = (TextView) v.findViewById(R.id.tvCMHTk);


//            imageview.getLayoutParams().height=height;

            tvCMHDate.getLayoutParams().width = width;
            tvCMHName.getLayoutParams().width = width;
            tvCMHTk.getLayoutParams().width = width;

            if(tvCMHDate != null){
                tvCMHDate.setText(memberInfo.getDate());

            }

            if(tvCMHName != null){
                tvCMHName.setText(memberInfo.getpName());

            }

            if(tvCMHTk != null) {
                tvCMHTk.setText(memberInfo.getpTk());
            }

        }

        return v;
    }
}