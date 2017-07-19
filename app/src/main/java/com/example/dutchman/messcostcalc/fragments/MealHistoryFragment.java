package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.models.MemberInfo;
import com.example.dutchman.messcostcalc.models.MemberMealInfo;
import com.example.dutchman.messcostcalc.models.PersonCredit;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.adapters.CustomAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomAdapterMealHistory;
import com.example.dutchman.messcostcalc.adapters.CustomAdapterMealRow;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;
import com.example.dutchman.messcostcalc.adapters.CustomMemberMealAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealHistoryFragment extends Fragment{


    private Context context;

    private DBHandler handler;

    private EditText etMHYear;
    private Spinner etMHMonth;

    private Button btnMHBazar,btnMHCost,btnMHMeal;

    private ListView lvMealHistory;

    private TextView tvMHTBazar,tvMHPerhead;

    private List<MemberInfo> list;
    private List<DebitInfo> list1;
    private List<MemberInfo> dateList;

    private List<String> monthList;



    public MealHistoryFragment() {
        // Required empty public constructor

        String[] monthsName = new String[]{"Select Month","January","February","March","April","May","June","July","August","September","October","November","December"};

        monthList = new ArrayList<>(Arrays.asList(monthsName));

    }

    public void setContext(Context context){

        this.context = context;

        this.handler = new DBHandler(this.context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_history, container, false);


        // init

        list = new ArrayList<>();
       list1 = new ArrayList<>();
        dateList = new ArrayList<>();


        etMHMonth = (Spinner) view.findViewById(R.id.etMHMonth);
        etMHYear  = (EditText) view.findViewById(R.id.etMHYear);


        // init spinner

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,monthList);
        etMHMonth.setAdapter(monthAdapter);

        String mnth = new SimpleDateFormat("MMMM").format(new Date());

        int index = monthList.indexOf(mnth);

        etMHMonth.setSelection(index);

        // set year

        etMHYear.setText( new SimpleDateFormat("yyyy").format(new Date()));

        btnMHBazar = (Button) view.findViewById(R.id.btnMHBazar);
        btnMHCost  = (Button) view.findViewById(R.id.btnMHCost);
        btnMHMeal  = (Button) view.findViewById(R.id.btnMHMeal);

        lvMealHistory = (ListView) view.findViewById(R.id.lvMealHistory);

        tvMHTBazar   = (TextView) view.findViewById(R.id.tvMHTBazar);
        tvMHPerhead  = (TextView) view.findViewById(R.id.tvMHPerhead);

        tvMHTBazar.setVisibility(View.GONE);
        tvMHPerhead.setVisibility(View.GONE);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated


        height /= 2;


        //lvMealHistory.getLayoutParams().height = height;

        lvMealHistory.setClickable(false);


        SharedPreferences sharedPref = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("is_without", true))
            btnMHMeal.setVisibility(View.GONE);
        else if(sharedPref.getBoolean("is_with",true))
            btnMHMeal.setVisibility(View.VISIBLE);


        if(!handler.isMemberExists()){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

            dialog.goToAddMemeberFragment();
        }


        btnMHBazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!handler.isMemberExists()){

                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();

                }

                lvMealHistory.setEnabled(false);

                if(etMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0){

                    String month = etMHMonth.getSelectedItem().toString().trim();
                    String year = etMHYear.getText().toString().trim();

                    list.clear();


                    list = handler.getBazarInfo(month,year);

                    if(list != null && list.size() > 0) {

                        CustomAdapterMealHistory adapter = new CustomAdapterMealHistory(context, R.layout.custom_meal_history, list);

                        lvMealHistory.setAdapter(adapter);

                        if(adapter.getCount() > 6){
                            View item = adapter.getView(0, null, lvMealHistory);
                            item.measure(0, 0);
                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (6.5 * item.getMeasuredHeight()));
                            lvMealHistory.setLayoutParams(params);
                        }

                        tvMHTBazar.setVisibility(View.VISIBLE);
                        tvMHPerhead.setVisibility(View.VISIBLE);


                        int total = list.get(0).getTotal();
                        int members = handler.getNumberOfMembers("Meal",month, year);

                        Toast.makeText(context,"members: "+members,Toast.LENGTH_SHORT).show();


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
                    etMHMonth.performClick();
                }
            }
        });

        btnMHCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!handler.isMemberExists()){

                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();

                }

                lvMealHistory.setEnabled(false);

                if(etMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0) {

                    tvMHTBazar.setVisibility(View.GONE);
                    tvMHPerhead.setVisibility(View.GONE);

                    list1.clear();

                    String month = etMHMonth.getSelectedItem().toString().trim();
                    String year = etMHYear.getText().toString().trim();

                    List<PersonCredit> personCredits = handler.getPersonCreditForMHistory("Meal",month, year);

                    int personCost = 0;

                    personCost = handler.getBazarPerCost(month,year);
                    DebitInfo debitInfo;

                    for (PersonCredit personCredit : personCredits) {

                        debitInfo = new DebitInfo(personCredit.getName(), personCredit.getTk(), personCost, (personCredit.getTk() - personCost));
                        list1.add(debitInfo);

                    }

                    CustomAdapter adapter = new CustomAdapter(context,R.layout.custom_row,list1);

                    lvMealHistory.setAdapter(adapter);
                } else{
                    Toast.makeText(context,"Select month first",Toast.LENGTH_SHORT).show();
                    etMHMonth.performClick();
                }
            }
        });

        btnMHMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!handler.isMemberExists()){

                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();

                }

                if(etMHMonth.getSelectedItemPosition() > 0 && etMHYear.getText().toString().trim().length() > 0){


                    String month = etMHMonth.getSelectedItem().toString().trim();
                    String year = etMHYear.getText().toString().trim();

                    tvMHTBazar.setVisibility(View.GONE);
                    tvMHPerhead.setVisibility(View.GONE);

                    //Toast.makeText(context,month+" "+year,Toast.LENGTH_SHORT).show();

                    dateList.clear();

                    dateList = handler.getMealInfo(month,year);


                    if(dateList != null) {
                        lvMealHistory.setEnabled(true);

                        CustomAdapterMealRow adapter = new CustomAdapterMealRow(context, R.layout.custom_meal_row, dateList);

                        lvMealHistory.setAdapter(adapter);

                        //lvMealHistory.setOnItemClickListener(MealHistoryFragment.this);


                        lvMealHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                lvMealHistory.setEnabled(false);
                                //Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                                TextView tvCMRMealDateClicked = (TextView) view.findViewById(R.id.tvCMRMealDate);

                                //Toast.makeText(context,tvCMRMealDateClicked.getText().toString(),Toast.LENGTH_SHORT).show();

                                List<MemberMealInfo> memberMealList;


                                memberMealList = handler.getMemberMeal(tvCMRMealDateClicked.getText().toString());

                                CustomMemberMealAdapter adapter = new CustomMemberMealAdapter(context, R.layout.custom_member_meal_row, memberMealList);

                                lvMealHistory.setAdapter(adapter);
                            }
                        });




                    } else{

                       Toast.makeText(context,"No data found in meal",Toast.LENGTH_SHORT).show();
                    }


                } else{
                    Toast.makeText(context,"Select month first",Toast.LENGTH_SHORT).show();
                    etMHMonth.performClick();
                }
            }
        });


        return view;
    }

}
