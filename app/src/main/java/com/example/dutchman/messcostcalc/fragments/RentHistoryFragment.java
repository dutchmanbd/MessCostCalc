package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.db.RentDebitCreditDataSource;
import com.example.dutchman.messcostcalc.db.RentInfoDatatSource;
import com.example.dutchman.messcostcalc.models.Calculator;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.models.PersonCredit;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.adapters.CustomAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomAdapterRentHistory;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RentHistoryFragment extends Fragment {

    private Spinner spRHMonth;
    private EditText etRHYear;

    private RadioGroup rgRentHistoryOption;

    private RadioButton rbRentHisMeal;

    private ListView lvRentHistory;

    private List<Calculator> calculators;
    private DebitInfo debitInfo;
    private List<DebitInfo> list;

    private List<String> monthList;



    private Context context;
    private DBHandler handler;
    private RentInfoDatatSource rentInfoDatatSource;
    private RentDebitCreditDataSource rentDebitCreditDataSource;

    public RentHistoryFragment() {
        String[] monthsName = new String[]{"Select Month","January","February","March","April","May","June","July","August","September","October","November","December"};

        monthList = new ArrayList<>(Arrays.asList(monthsName));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_history, container, false);

        calculators = new ArrayList<>();

        context = getActivity().getApplicationContext();

        rentInfoDatatSource = RentInfoDatatSource.getInstance(context);
        rentDebitCreditDataSource = RentDebitCreditDataSource.getInstance(context);

        if(rentInfoDatatSource.getNumberOfMembers() <= 0){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

            dialog.goToAddMemeberFragment();

        } else{
            inits(view);
        }


        //lvRentHistory.getLayoutParams().height = height + height/2;

//        btnRHRent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(!handler.isMemberExists()){
//
//                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//                    dialog.goToAddMemeberFragment();
//
//                } else {
//
//                    if (etRHMonth.getSelectedItemPosition() > 0 && etRHYear.getText().toString().trim().length() > 0) {
//
//                        String month = etRHMonth.getSelectedItem().toString().trim();
//                        String year = etRHYear.getText().toString().trim();
//
//                        calculators = new ArrayList<>();
//
//                        if (handler.getNumberOfMembers("Rent", month, year) > 0) {
//
//                            calculators = handler.getRentCostInfo(month, year);
//
//                            if (!calculators.isEmpty()) {
//
//                                CustomAdapterRentHistory adapter = new CustomAdapterRentHistory(context, R.layout.custom_rent_history, calculators);
//
//                                lvRentHistory.setAdapter(adapter);
//
//                            } else {
//                                Toast.makeText(context, "No data found.", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(context, "No member found.", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else {
//                        Toast.makeText(context, "Select month", Toast.LENGTH_SHORT).show();
//                        etRHMonth.performClick();
//                    }
//                }
//
//            }
//        });
//
//        btnRHCost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(!handler.isMemberExists()){
//
//                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//                    dialog.goToAddMemeberFragment();
//
//                } else {
//
//                    if (etRHMonth.getSelectedItemPosition() > 0 && etRHYear.getText().toString().trim().length() > 0) {
//
//                        String month = etRHMonth.getSelectedItem().toString().trim();
//                        String year = etRHYear.getText().toString().trim();
//
//                        int personCost = handler.getPerheadCostFromRent(month, year);
//
//                        if (handler.getNumberOfMembers("Rent", month, year) > 0) {
//
//
//                            //List<PersonCredit> personCredits = handler.getPersonCredit("rent");
//                            List<PersonCredit> personCredits = handler.getPersonCreditForRHistory("Rent", month, year);
//                            list = new ArrayList<>();
//
//                            if (!personCredits.isEmpty()) {
//
//                                for (PersonCredit personCredit : personCredits) {
//
//                                    debitInfo = new DebitInfo(personCredit.getName(), personCredit.getTk(), personCost, (personCredit.getTk() - personCost));
//                                    list.add(debitInfo);
//
//                                }
//
//                                CustomAdapter adapter = new CustomAdapter(context, R.layout.custom_row, list);
//
//                                lvRentHistory.setAdapter(adapter);
//                            } else {
//                                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else {
//                            Toast.makeText(context, "No member found.", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else {
//                        Toast.makeText(context, "Select Month", Toast.LENGTH_SHORT).show();
//                        etRHMonth.performClick();
//                    }
//                }
//            }
//        });


        return view;
    }

    private void inits(View view){

        spRHMonth = (Spinner) view.findViewById(R.id.spRHMonth);
        etRHYear  = (EditText) view.findViewById(R.id.etRHYear);

        rgRentHistoryOption = (RadioGroup) view.findViewById(R.id.rg_rent_history_options);

        lvRentHistory = (ListView) view.findViewById(R.id.lvRentHistory);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,monthList);
        spRHMonth.setAdapter(monthAdapter);

        String mnth = new SimpleDateFormat("MMMM").format(new Date());

        int index = monthList.indexOf(mnth);

        spRHMonth.setSelection(index);

        etRHYear.setText(new SimpleDateFormat("yyyy").format(new Date()));
        etRHYear.setSelection(etRHYear.getText().toString().length());

        getRentHistory();

        rgRentHistoryOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                if(checkedId == R.id.rb_rent_his_rent){

                    getRentHistory();

                } else if(checkedId == R.id.rb_rent_his_cost){

                    getCostHistory();

                }

            }
        });

    }

    private void getRentHistory(){

        if(rentInfoDatatSource.getNumberOfMembers() <= 0){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

            dialog.goToAddMemeberFragment();

        } else {

            if (spRHMonth.getSelectedItemPosition() > 0 && etRHYear.getText().toString().trim().length() > 0) {

                String month = spRHMonth.getSelectedItem().toString().trim();
                String year = etRHYear.getText().toString().trim();

                calculators = new ArrayList<>();

                if (rentDebitCreditDataSource.getNumberOfMembersForRent(month, year) > 0) {

                    calculators = rentInfoDatatSource.getRentCostInfo(month, year);

                    if (!calculators.isEmpty()) {

                        CustomAdapterRentHistory adapter = new CustomAdapterRentHistory(context, R.layout.custom_rent_history, calculators);

                        lvRentHistory.setAdapter(adapter);

                    } else {
                        Toast.makeText(context, "No data found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "No member found.", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(context, "Select month", Toast.LENGTH_SHORT).show();
                spRHMonth.performClick();
            }
        }

    }

    private void getCostHistory(){

    }

}
