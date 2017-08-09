package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.db.RentInfoDatatSource;
import com.example.dutchman.messcostcalc.models.Calculator;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment implements View.OnClickListener {


    private TextView tvMonth,tvYear,tvPerhead,tvTotal;

    private EditText etHouseRent,etGusCurrent,etServent,etNet,etPaper,etDirst,etOthers,etMembers;

    private Button btnCalc,btnInsert; //btnShow,btnShowAll,btnUpdate,btnDelete;

    private String hr,gc,st,it,pr,dt,os,ms,ph,tl;

    private Calculator cal;

    private RentInfoDatatSource rentInfoDatatSource;
    private Context context;


    public CostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cost, container, false);

        this.context = getActivity().getApplicationContext();
        rentInfoDatatSource = RentInfoDatatSource.getInstance(context);

        inits(view);

        // Button Action
        btnCalc.setOnClickListener(this);
        btnInsert.setOnClickListener(this);

        return view;
    }

    private void inits(View view){

        tvMonth      = (TextView) view.findViewById(R.id.tvMonth);
        tvYear       = (TextView) view.findViewById(R.id.tvYear);

        tvPerhead    = (TextView) view.findViewById(R.id.tvPerhead);
        tvTotal      = (TextView) view.findViewById(R.id.tvTotal);

        etHouseRent  = (EditText) view.findViewById(R.id.etHouseRent);
        etGusCurrent = (EditText) view.findViewById(R.id.etGusCurrent);

        etServent    = (EditText) view.findViewById(R.id.etServent);
        etNet        = (EditText) view.findViewById(R.id.etNet);

        etPaper      = (EditText) view.findViewById(R.id.etPaper);
        etDirst      = (EditText) view.findViewById(R.id.etDirst);

        etOthers     = (EditText) view.findViewById(R.id.etOthers);
        etMembers    = (EditText) view.findViewById(R.id.etMembers);

        btnCalc      = (Button) view.findViewById(R.id.btnCalc);
        btnInsert    = (Button) view.findViewById(R.id.btnInsert);

        etMembers.setText(""+rentInfoDatatSource.getNumberOfMembers());

        tvMonth.setText(new SimpleDateFormat("MMMM").format(new Date()));
        tvYear.setText(new SimpleDateFormat("yyyy").format(new Date()));


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnCalc:

                if(isAllEmpty()) {
                    setResultOnTV();
                }else {
                    showToast("Please fill all the field");
                }
                break;
            case R.id.btnInsert:
                String perhead = tvPerhead.getText().toString();
                String total   = tvTotal.getText().toString();
                if(isAllEmpty()) {
                    if (perhead.trim().length() == 0 && total.trim().length() == 0) {
                        showToast("Calculate First");
                        setResultOnTV();
                    }
                    AlertDialog diaBox = AskOption("Insert","Do you want to Insert data ?\nTotal: "+cal.getTotal()+"\tPerhead: "+cal.getPerhead());
                    diaBox.show();
                }else{
                    showToast("Fill all the field");
                }
                break;
        }

    }

    public void showToast(String toast){
        Toast.makeText(this.getActivity(),toast,Toast.LENGTH_SHORT).show();
    }

    private void setValues(){
        hr = etHouseRent.getText().toString().trim();
        gc = etGusCurrent.getText().toString().trim();
        st = etServent.getText().toString().trim();
        it = etNet.getText().toString().trim();
        pr = etPaper.getText().toString().trim();
        dt = etDirst.getText().toString().trim();
        os = etOthers.getText().toString().trim();
        ms = etMembers.getText().toString().trim();
    }

    private boolean isAllEmpty(){
        setValues();
        if((hr != null && !hr.equals("")) &&
                (gc != null && !gc.equals("")) &&
                (st != null && !st.equals("")) &&
                (it != null && !it.equals("")) &&
                (pr != null && !pr.equals("")) &&
                (dt != null && !dt.equals("")) &&
                (os != null && !os.equals("")) &&
                (ms != null && !ms.equals(""))
                ){
            return true;
        }else{
            return false;
        }
    }

    private void setResultOnTV(){
        cal = new Calculator(hr,gc,st,it,pr,dt,os,ms);
        showToast("Total: "+cal.getTotal()+"\nPerhead: "+cal.getPerhead());
        tvPerhead.setText(cal.getPerhead()+"");
        tvTotal.setText(cal.getTotal() + "");
    }

    private AlertDialog AskOption(String title,String message) {

        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this.getActivity())
                //set message, title, and icon
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        if(cal != null)
                            if(rentInfoDatatSource.insertIntoRentInfo(cal))
                                showToast("Data Inserted");
                            else
                                showToast("Data can not insert");
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        showToast("Cancel by the users.");
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }


}
