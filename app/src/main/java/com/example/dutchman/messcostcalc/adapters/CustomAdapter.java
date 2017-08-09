package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.R;

import java.util.List;

/**
 * Created by dutchman on 10/8/16.
 */
public class CustomAdapter extends ArrayAdapter<DebitInfo> {

    private TextView tvCustomName;
    private TextView tvCustomCredit;
    private TextView tvCustomDebit;
    private TextView tvCustomBalance;

    private Button btnCRAdd;
    private Context context;

    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapter(Context context, int resource, List<DebitInfo> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_row, null);
        }

        DebitInfo debitInfo = getItem(position);


        if(debitInfo != null){

            tvCustomName    = (TextView) v.findViewById(R.id.tvCustomName);
            tvCustomCredit  = (TextView) v.findViewById(R.id.tvCustomCredit);
            tvCustomDebit   = (TextView) v.findViewById(R.id.tvCustomDebit);
            tvCustomBalance = (TextView) v.findViewById(R.id.tvCustomBalance);


            if(tvCustomName != null){
                tvCustomName.setText(debitInfo.getPersonName());

            }

            if(tvCustomCredit != null){
                tvCustomCredit.setText(Double.toString(debitInfo.getpCredit()));

            }

            if(tvCustomDebit != null) {
                tvCustomDebit.setText(Double.toString(debitInfo.getpDebit()));
            }

            if(tvCustomBalance != null){
                tvCustomBalance.setText(Double.toString(debitInfo.getpBalance()));
            }

            /*btnCRAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tvCustomName != null && tvCustomBalance != null){
                        String name = tvCustomName.getText().toString();
                        String balance = tvCustomBalance.getText().toString();

                        Toast.makeText(context,name+" "+balance,Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

        }

        return v;
    }
}
