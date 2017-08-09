package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.models.MemberInfo;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 1/11/17.
 */


public class CustomMemberDetailDialog {

    private Context context;

    private DBHandler handler;;

    public CustomMemberDetailDialog(Context context, DBHandler handler){

        this.context = context;
        this.handler = handler;

    }


    public void showMemberDetailDialog(){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Text Title
        TextView title = new TextView(context);
        title.setText("Member List");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.colorAccent));
        title.setTextSize(25);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View convertView = (View) inflater.inflate(R.layout.custom_member_details, null);


        ListView lvCusMemberDatail = (ListView) convertView.findViewById(R.id.lvCusMemberDatail);

        Button btnCusMDetailOk = (Button) convertView.findViewById(R.id.btnCusMDetailOk);


        List<MemberInfo> list = null; //handler.getMembers();

        if(list != null) {

            CustomMemberDetailAdapter adapter = new CustomMemberDetailAdapter(context, R.layout.custom_meal_history, list);
            lvCusMemberDatail.setAdapter(adapter);
        }


        alertDialog.setView(convertView);

        alertDialog.setCancelable(false);

        final AlertDialog dialog = alertDialog.create();

        btnCusMDetailOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();

    }


    private class CustomMemberDetailAdapter extends ArrayAdapter<MemberInfo> {

//        private TextView tvCMMRPName,tvCMMRPMealNo;
//
        private Context context;

        public CustomMemberDetailAdapter(Context context, int resource) {
            super(context, resource);
        }

        public CustomMemberDetailAdapter(Context context, int resource, List<MemberInfo> objects) {
            super(context, resource, objects);

            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.custom_meal_history,null);
            }

            MemberInfo memberInfo = getItem(position);


            TextView tvCMHDate = (TextView) v.findViewById(R.id.tvCMHDate);
            TextView tvCMHName = (TextView) v.findViewById(R.id.tvCMHName);
            TextView tvCMHTk = (TextView) v.findViewById(R.id.tvCMHTk);

            if(memberInfo != null) {

                tvCMHDate.setText(memberInfo.getDate());
                tvCMHName.setText(memberInfo.getpName());
                tvCMHTk.setText(memberInfo.getpTk());
            }

            return v;
        }


    }

}