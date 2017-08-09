package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.MainActivity;
import com.example.dutchman.messcostcalc.db.RentInfoDatatSource;
import com.example.dutchman.messcostcalc.models.Bazar;
import com.example.dutchman.messcostcalc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private TextView tvHomeDate;

    private TextView tvHBDate,tvHBDay;

    private TextView tvHLName,tvHLCost;

    private TextView tvHTotalBazar,tvHBPerhead;

    private TextView tvHRent,tvHRentCost;

    private SimpleDateFormat simpleDateFormat;

    private Context context;

    private BazarDataSource bazarDataSource;
    private RentInfoDatatSource rentInfoDatatSource;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity().getApplicationContext();

        bazarDataSource = BazarDataSource.getInstance(context);
        rentInfoDatatSource = RentInfoDatatSource.getInstance(context);

        inits(view);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.MY_PREFS_NAME,Context.MODE_PRIVATE);

        if(sharedPref != null)
            if(sharedPref.getBoolean(Constant.SETTINGS_PREFS_WITHOUT_MEAL,true)) {

                navigationView.getMenu().findItem(R.id.nav_add_meal).setVisible(false);

            } else if(sharedPref.getBoolean(Constant.SETTINGS_PREFS_WITH_MEAL,true)){

                navigationView.getMenu().findItem(R.id.nav_add_meal).setVisible(true);
            }
    }

    private void inits(View view){

        //Date
        tvHomeDate   = (TextView) view.findViewById(R.id.tvHomeDate);

        //Total Rent and perhead
        tvHRent      = (TextView) view.findViewById(R.id.tv_total_rent);

        tvHRentCost  = (TextView) view.findViewById(R.id.tv_perhead);

        //Last Date
        tvHBDate     = (TextView) view.findViewById(R.id.tv_bazar_last_date);
        tvHBDay      = (TextView) view.findViewById(R.id.tv_bazar_last_day);

        //Last bazar member name and tk

        tvHLName     = (TextView) view.findViewById(R.id.tv_last_person_name);
        tvHLCost     = (TextView) view.findViewById(R.id.tv_last_bazar_tk);

        // Total Bazar
        tvHTotalBazar     = (TextView) view.findViewById(R.id.tv_total_bazar);
        tvHBPerhead     = (TextView) view.findViewById(R.id.tv_bazar_perhead);


        //Show Date
        simpleDateFormat = new SimpleDateFormat("EEEE  dd MMMM yyyy");
        String dt = simpleDateFormat.format(new Date());
        tvHomeDate.setText(dt);

        // Show Total and perhead rent cost
        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        // fetch the total rent cost and perhead
        int total = rentInfoDatatSource.getTotalCostForRent(month,year);
        int perhead = rentInfoDatatSource.getPerheadCostFromRent(month,year);

        // get member info
        Bazar bazar = bazarDataSource.getLastDateAndName(month,year);


        if(total > 0 && perhead > 0){

            tvHRent.setText(total+"");
            tvHRentCost.setText(perhead+"");

        } else {

            Calendar cal1 =  Calendar.getInstance();
            cal1.add(Calendar.MONTH ,-1);

            if(month.equals("January")){

                Calendar cal2 =  Calendar.getInstance();
                cal2.add(Calendar.YEAR ,-1);
                year = new SimpleDateFormat("yyyy").format(cal1.getTime());
            }

            month = new SimpleDateFormat("MMMM").format(cal1.getTime());

            total = rentInfoDatatSource.getTotalCostForRent(month,year);
            perhead = rentInfoDatatSource.getPerheadCostFromRent(month,year);
            tvHRent.setText(total+"");
            tvHRentCost.setText(perhead+"");
        }

        if(bazar != null){

            tvHBDate.setText(bazar.getDate());
            tvHLName.setText(bazar.getmName());
            tvHLCost.setText(bazar.getTk()+"");

        } else{

            Calendar cal1 =  Calendar.getInstance();
            cal1.add(Calendar.MONTH ,-1);

            if(month.equals("January")){

                Calendar cal2 =  Calendar.getInstance();
                cal2.add(Calendar.YEAR ,-1);
                year = new SimpleDateFormat("yyyy").format(cal1.getTime());
            }

            month = new SimpleDateFormat("MMMM").format(cal1.getTime());

            bazar = bazarDataSource.getLastDateAndName(month,year);

            if(bazar != null){

                tvHBDate.setText(bazar.getDate());
                tvHLName.setText(bazar.getmName());
                tvHLCost.setText(bazar.getTk()+"");
            }
        }
        try {

            String dTemp = tvHBDate.getText().toString().trim();

            if(dTemp != null && !dTemp.equals("")) {

                Date myDate = new SimpleDateFormat("dd-MM-yyyy").parse(dTemp);
                String dayName = new SimpleDateFormat("EEEE").format(myDate);
                tvHBDay.setText(dayName);
            } else{

                tvHBDay.setText("DAY");
                tvHBDate.setText("Date");
                tvHLName.setText("Name");

            }


        } catch (ParseException e) {
            //tvHBDay.setText("Error day");
        }
        month = new SimpleDateFormat("MMMM").format(new Date());
        year = new SimpleDateFormat("yyyy").format(new Date());

        double totalBazar = bazarDataSource.getTotalBazar(month,year);
        double bPerCost = 0.0;

        if(totalBazar > 0)
            bPerCost= totalBazar / bazarDataSource.getMembersName(month,year).size();

        tvHTotalBazar.setText(totalBazar+"");
        tvHBPerhead.setText(String.format("%.2f",Math.ceil(bPerCost)));
    }

}
