package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.adapters.CustomAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends Fragment {



    private TextView tvCreditDate;
    private Spinner etCreditName;
    private EditText etCreditTK;

    private Button btnCreditAdd;
    private Button btnCreditShowData;

    private String destName;

    private String cDate,cMonth,cYear,cName,cTk;
    private String uMonth,uYear;

    private Context context;

    private DBHandler handler;

    private List<String> mNameList;

    private List<String> monthList;


    public CreditFragment() {
        // Required empty public constructor



    }

    public void setDest(Context context,String destName){

        this.context = context;
        this.destName = destName;
        handler = DBHandler.getInstance(this.context);

        monthList = Arrays.asList(context.getResources().getStringArray(R.array.month_list));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_credit, container, false);

//        mNameList = new ArrayList<>();
//
//        mNameList = handler.getMNameList();
//
//        //Textview init
//
//        tvCreditDate = (TextView) view.findViewById(R.id.tvCreditDate);
//        etCreditName = (Spinner) view.findViewById(R.id.etCreditPName);
//        etCreditTK   = (EditText) view.findViewById(R.id.etCreditTK);
//
//        //Button init
//        btnCreditAdd = (Button) view.findViewById(R.id.btnCreditAdd);
//        btnCreditShowData = (Button) view.findViewById(R.id.btnCreditShowData);
//
//        mNameList.add(0,"Select name");
//
//        if(mNameList.size() > 1) {
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, mNameList);
//
//            etCreditName.setAdapter(adapter);
//        }
//
//        etCreditName.setSelection(0);
//
//
//        cDate = new SimpleDateFormat("dd-MM-yy").format(new Date());
//        cMonth = new SimpleDateFormat("MMMM").format(new Date());
//        cYear = new SimpleDateFormat("yyyy").format(new Date());
//
//        tvCreditDate.setText(cDate);
//
//        if(!handler.isMemberExists()){
//
//            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//            dialog.goToAddMemeberFragment();
//
//        }
//
//
//        // Add Button Clicked
//
//        btnCreditAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(handler.isMemberExists()) {
//
//                    if (etCreditName.getSelectedItemPosition() > 0) {
//
//                        if (etCreditTK.getText().toString().trim().length() > 0) {
//
//                            cName = etCreditName.getSelectedItem().toString();
//                            cTk = etCreditTK.getText().toString().trim();
//
//                            if (handler.isMemberExists(cName)) {
//
//                                new AlertDialog.Builder(context)
//                                        .setTitle("Add Entry")
//                                        .setMessage("Are you sure want to add this entry ?")
//                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                // continue with add
//
//
//                                                if (handler.insertPersonCredit(destName, cDate, cMonth, cYear, cName, cTk)) {
//
//                                                    Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
//                                                    etCreditName.setSelection(0);
//
//                                                } else {
//
//                                                    Toast.makeText(context, "Data can not added", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }
//                                        })
//                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                // do nothing
//                                            }
//                                        })
//                                        .setIcon(android.R.drawable.ic_dialog_alert)
//                                        .show();
//                            } else {
//                                Toast.makeText(context, cName + " is not exists. Please check member list", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Toast.makeText(context, "Add credit for " + etCreditName.getSelectedItem(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//
//                        etCreditName.performClick();
//                        Toast.makeText(context, "Please select name first", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else{
//
//                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//                    dialog.goToAddMemeberFragment();
//
//                }
//            }
//        });
//
//
//        //ShowData button Clicked
//
//        btnCreditShowData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(handler.isMemberExists()) {
//
//                    if (etCreditName.getSelectedItemPosition() > 0) {
//
//                        cName = etCreditName.getSelectedItem().toString();
//
//
//                        //cTk = etCreditTK.getText().toString().trim();
//
//                        setUpdateMonthAndYear();
//                        //etCreditName.setSelection(0);
//
//                    } else {
//                        etCreditName.performClick();
//                        Toast.makeText(context, "Please select name first", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else{
//
//                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//                    dialog.goToAddMemeberFragment();
//                }
//            }
//        });


        return view;
    }

//    private void setUpdateMonthAndYear(){
//
//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//        //Text Title
//        TextView title = new TextView(context);
//        title.setText("Search data for");
//        title.setPadding(10, 10, 10, 10);
//        title.setBackgroundResource(R.drawable.border_panel);
//        title.setGravity(Gravity.CENTER);
//        title.setTextColor(getResources().getColor(R.color.colorBlack));
//        title.setTextSize(35);
//
//        //alertDialog.setTitle(cName);
//        alertDialog.setCustomTitle(title);
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View convertView = (View) inflater.inflate(R.layout.custom_month_list, null);
//
//        TextView tvCusSearchName     = (TextView) convertView.findViewById(R.id.tvCusSearchName);
//        //TextView tvCusUpdateTk       = (TextView) convertView.findViewById(R.id.tvCusUpdateTk);
//
//        final Spinner spCusMonthList = (Spinner) convertView.findViewById(R.id.spCusMonthList);
//        final EditText etCusYear     = (EditText) convertView.findViewById(R.id.etCusYear);
//
//
//        Button btnCusSearchUpdate = (Button) convertView.findViewById(R.id.btnCusSearchUpdate);
//        Button btnCancelUpdate = (Button) convertView.findViewById(R.id.btnCancelUpdate);
//
//
//        tvCusSearchName.setText(cName);
//        //tvCusUpdateTk.setText(cTk);
//
//
//        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, monthList);
//
//        spCusMonthList.setAdapter(adapter);
//
//        spCusMonthList.setSelection(monthList.indexOf(cMonth));
//        etCusYear.setText(cYear);
//        etCusYear.setSelection(etCusYear.getText().toString().length());
//
//        alertDialog.setView(convertView);
//
//        alertDialog.setCancelable(false);
//
//        final AlertDialog dialog = alertDialog.create();
//
//        btnCusSearchUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(spCusMonthList.getSelectedItemPosition() > 0) {
//
//                    if(etCusYear.getText().toString().trim().length() > 0) {
//                        uMonth = spCusMonthList.getSelectedItem().toString().trim();
//                        uYear = etCusYear.getText().toString().trim();
//
//                        if(handler.isMemberExistForSearch(destName,uMonth,uYear,cName)){
//
//                            int perCost = 0;
//
//                            if(destName.equals("meal")){}
//                                //perCost = handler.getBazarPerCost(uMonth,uYear);
//
//                            else
//                                perCost = handler.getPerheadCostFromRent(uMonth,uYear);
//
//
//                            int pCredit   = handler.searchPaidTakaByName(destName,uMonth,uYear,cName);
//
//                            spCusMonthList.setSelection(0);
//                            dialog.dismiss();
//                            showPersonData(pCredit,perCost);
//
//                            //Toast.makeText(context,pCredit+" "+perCost+" "+(pCredit-perCost),Toast.LENGTH_LONG).show();
//
//
//                        }else{
//
//                            Toast.makeText(context,cName+" not exists",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } else{
//                        Toast.makeText(context, "add year"+spCusMonthList.getSelectedItem(), Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } else{
//
//                    spCusMonthList.performClick();
//                    Toast.makeText(context, "Please select Month first", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    private void showPersonData(int pCredit, int perCost){
//
//        DebitInfo debitInfo = new DebitInfo(cName,pCredit,perCost,(pCredit-perCost));
//        List<DebitInfo> debitInfoList = new ArrayList<>();
//
//        debitInfoList.add(debitInfo);
//
//        CustomAdapter adapter = new CustomAdapter(context,R.layout.custom_row,debitInfoList);
//
//
//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//        //Text Title
//        TextView title = new TextView(context);
//        title.setText(uMonth+" "+uYear);
//        title.setPadding(10, 10, 10, 10);
//        title.setGravity(Gravity.CENTER);
//        title.setTextColor(getResources().getColor(R.color.colorAccent));
//        title.setTextSize(25);
//
//        //alertDialog.setTitle(cName);
//        alertDialog.setCustomTitle(title);
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View convertView = (View) inflater.inflate(R.layout.custom_search_cost, null);
//
//        alertDialog.setView(convertView);
//
//
//        ListView lvCusSearchCost = (ListView) convertView.findViewById(R.id.lvCusSearchCost);
//
//        Button btnCancelSearchResult = (Button) convertView.findViewById(R.id.btnCancelSearchResult);
//
//        lvCusSearchCost.setAdapter(adapter);
//
//        alertDialog.setCancelable(false);
//
//        final AlertDialog dialog = alertDialog.create();
//
//        btnCancelSearchResult.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.show();
//
//    }



}
