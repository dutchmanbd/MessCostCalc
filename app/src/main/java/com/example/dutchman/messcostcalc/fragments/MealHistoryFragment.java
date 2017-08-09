package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.adapters.BazarAdapter;
import com.example.dutchman.messcostcalc.adapters.MealDebitCreditAdapter;
import com.example.dutchman.messcostcalc.adapters.MealExpendableAdapter;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.db.MealDataSource;
import com.example.dutchman.messcostcalc.db.MealDebitCreditDataSource;
import com.example.dutchman.messcostcalc.models.Bazar;
import com.example.dutchman.messcostcalc.models.Credit;
import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.models.Meal;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealHistoryFragment extends Fragment{


    private Context context;

    private DBHandler handler;

    private EditText etMHYear;
    private Spinner spMHMonth;


    private RadioGroup rgMealHistoryOption;

    private RadioButton rbMealHisMeal;


    private ListView lvMealHistory;

    private ExpandableListView evMealHistory;

    private TextView tvMHTBazar,tvMHPerhead;

    private List<Bazar> bazarList;
    private List<DebitInfo> debitInfoList;
    private List<Meal> dateList;

    private HashMap<Meal, List<Meal>> hashMap;

    private List<String> monthList;

    private BazarDataSource bazarDataSource;
    private MealDataSource mealDataSource;
    private MealDebitCreditDataSource mealDebitCreditDataSource;



    public MealHistoryFragment() {
        // Required empty public constructor

        String[] monthsName = new String[]{"Select Month","January","February","March","April","May","June","July","August","September","October","November","December"};

        monthList = new ArrayList<>(Arrays.asList(monthsName));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_history, container, false);

        context = getContext();

        mealDataSource = MealDataSource.getInstance(context);

        mealDebitCreditDataSource = MealDebitCreditDataSource.getInstance(context);

        bazarDataSource = BazarDataSource.getInstance(context);


        if(!bazarDataSource.isMemberExists(1)){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
            dialog.goToAddMemeberFragment();

        } else {

            inits(view);
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void inits(View view){

        // init
        bazarList = new ArrayList<>();
        debitInfoList = new ArrayList<>();
        dateList = new ArrayList<>();
        hashMap = new HashMap<>();


        // view init
        spMHMonth = (Spinner) view.findViewById(R.id.spMHMonth);
        etMHYear  = (EditText) view.findViewById(R.id.etMHYear);

        rgMealHistoryOption = (RadioGroup) view.findViewById(R.id.rg_meal_history_options);
        rbMealHisMeal = (RadioButton) view.findViewById(R.id.rb_meal_his_meal);

        lvMealHistory = (ListView) view.findViewById(R.id.lvMealHistory);
        evMealHistory = (ExpandableListView) view.findViewById(R.id.ev_meal_his);

        tvMHTBazar   = (TextView) view.findViewById(R.id.tvMHTBazar);
        tvMHPerhead  = (TextView) view.findViewById(R.id.tvMHPerhead);

        tvMHTBazar.setVisibility(View.GONE);
        tvMHPerhead.setVisibility(View.GONE);

        // init spinner
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context,R.layout.spinner_item,monthList);
        spMHMonth.setAdapter(monthAdapter);

        String currentMonth = new SimpleDateFormat("MMMM").format(new Date());
        int index = monthList.indexOf(currentMonth);

        spMHMonth.setSelection(index);

        // set year
        etMHYear.setText( new SimpleDateFormat("yyyy").format(new Date()));

        etMHYear.setSelection(etMHYear.getText().toString().length());

        lvMealHistory.setClickable(false);


        SharedPreferences sharedPref = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("is_without", true))
            rbMealHisMeal.setVisibility(View.GONE);
        else if(sharedPref.getBoolean("is_with",true))
            rbMealHisMeal.setVisibility(View.VISIBLE);


        evMealHistory.setVisibility(View.GONE);

        //By default bazar adapter is selected
        getBazarHistory();

        rgMealHistoryOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                if(checkedId == R.id.rb_meal_his_bazar){

                    getBazarHistory();

                } else if(checkedId == R.id.rb_meal_his_cost){

                    getCostHistory();

                } else if(checkedId == R.id.rb_meal_his_meal){

                    getMealHistory();
                }

            }
        });


    }

    private void getBazarHistory(){

        lvMealHistory.setEnabled(false);
        lvMealHistory.setVisibility(View.VISIBLE);
        evMealHistory.setVisibility(View.GONE);

        if(spMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0){

            String month = spMHMonth.getSelectedItem().toString().trim();
            String year = etMHYear.getText().toString().trim();

            bazarList.clear();

            bazarList = bazarDataSource.getBazars(month,year);

            if(bazarList != null && bazarList.size() > 0) {

                //CustomAdapterMealHistory adapter = new CustomAdapterMealHistory(context, R.layout.custom_meal_history, list);
                BazarAdapter bazarAdapter = new BazarAdapter(context, R.layout.single_member_item, bazarList);

                lvMealHistory.setAdapter(bazarAdapter);

                if(bazarAdapter.getCount() > 6){
                    View item = bazarAdapter.getView(0, null, lvMealHistory);
                    item.measure(0, 0);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (6.5 * item.getMeasuredHeight()));
                    lvMealHistory.setLayoutParams(params);
                }

                tvMHTBazar.setVisibility(View.VISIBLE);
                tvMHPerhead.setVisibility(View.VISIBLE);


                double total = bazarDataSource.getTotalBazar(month, year);          //bazarList.get(0).getTotal();
                int members = bazarDataSource.getBazars(month, year).size();        //handler.getNumberOfMembers("Meal",month, year);

                if (members != 0) {

                    tvMHTBazar.setText("Total: "+total);

                    tvMHPerhead.setText("Perhead: "+(total / members + 1));
                }
            } else{

                tvMHTBazar.setText("Total: 0");

                tvMHPerhead.setText("Perhead: 0");
            }


        } else{
            Toast.makeText(context,"Select month first",Toast.LENGTH_SHORT).show();
            spMHMonth.performClick();
        }
    }

    private void getCostHistory(){

        lvMealHistory.setEnabled(false);
        lvMealHistory.setVisibility(View.VISIBLE);
        evMealHistory.setVisibility(View.GONE);

        if(spMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0) {

            tvMHTBazar.setVisibility(View.GONE);
            tvMHPerhead.setVisibility(View.GONE);

            debitInfoList.clear();

            String month = spMHMonth.getSelectedItem().toString().trim();
            String year = etMHYear.getText().toString().trim();

            List<Credit> personCredits = mealDebitCreditDataSource.getCreditForMHistory(month, year);

            double personCost = bazarDataSource.getTotalBazar(month,year);
            DebitInfo debitInfo;

            for (Credit credit : personCredits) {

                //debitInfo = new DebitInfo(credit.getName(), credit.getTk(), personCost, (credit.getTk() - personCost));
                debitInfo = new DebitInfo(credit, personCost, (credit.getTk() - personCost));
                debitInfoList.add(debitInfo);

            }

            MealDebitCreditAdapter adapter = new MealDebitCreditAdapter(getContext(), R.layout.single_member_item, debitInfoList);

            lvMealHistory.setAdapter(adapter);
        } else{
            Toast.makeText(context,"Select month first",Toast.LENGTH_SHORT).show();
            spMHMonth.performClick();
        }
    }

    private void getMealHistory(){

        if(spMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0){

            String month = spMHMonth.getSelectedItem().toString().trim();
            String year = etMHYear.getText().toString().trim();

            tvMHTBazar.setVisibility(View.GONE);
            tvMHPerhead.setVisibility(View.GONE);
            lvMealHistory.setVisibility(View.GONE);
            evMealHistory.setVisibility(View.VISIBLE);

            dateList.clear();

            dateList = mealDataSource.getMealInfo(month,year);

            hashMap.clear();

            for(Meal meal : dateList){

                List<Meal> meals = mealDataSource.getMealOnDate(meal.getDate());

                hashMap.put(meal, meals);
            }

            if(dateList != null) {
                lvMealHistory.setEnabled(true);


                MealExpendableAdapter adapter = new MealExpendableAdapter(getActivity().getApplicationContext(), dateList, hashMap);
                //CustomAdapterMealRow adapter = new CustomAdapterMealRow(context, R.layout.custom_meal_row, dateList);
                evMealHistory.setAdapter(adapter);

            } else{

               Toast.makeText(context,"No data found in meal",Toast.LENGTH_SHORT).show();
            }


        } else{
            Toast.makeText(context,"Select month first",Toast.LENGTH_SHORT).show();
            spMHMonth.performClick();
        }
    }

}
