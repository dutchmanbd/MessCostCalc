package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.MainActivity;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.fragments.AddMembersFragment;

/**
 * Created by dutchman on 1/11/17.
 */

public class CustomGoAlertDialog {

    private Context context;

    private FragmentManager manager;;

    public CustomGoAlertDialog(Context context){

        this.context = context;

    }


    public void goToAddMemeberFragment(){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Text Title
        TextView title = new TextView(context);
        title.setText("No member exist");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(context.getResources().getColor(R.color.colorAccent));
        title.setTextSize(25);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View convertView = (View) inflater.inflate(R.layout.custom_go_alert_dialog, null);

        Button btn_yes       = (Button) convertView.findViewById(R.id.btn_yes);
        Button btn_no        = (Button) convertView.findViewById(R.id.btn_no);

        TextView tvAlertText = (TextView) convertView.findViewById(R.id.tvAlertText);

        alertDialog.setView(convertView);

        alertDialog.setCancelable(false);

        final AlertDialog dialog = alertDialog.create();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddMembersFragment addMembersFragment = new AddMembersFragment();
                addMembersFragment.setContext(context);

                dialog.dismiss();

                manager = ((MainActivity) context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.rlContent,addMembersFragment,addMembersFragment.getTag())
                        .commit();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
