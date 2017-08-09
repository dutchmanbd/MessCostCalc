package com.example.dutchman.messcostcalc.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.activities.MealDebitCreditEditActivity;
import com.example.dutchman.messcostcalc.adapters.CreditDetailAdapter;
import com.example.dutchman.messcostcalc.adapters.MealDebitCreditAdapter;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.db.MealDebitCreditDataSource;
import com.example.dutchman.messcostcalc.models.Credit;
import com.example.dutchman.messcostcalc.models.DebitInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MealCreditFragment extends Fragment {


    private static final String TAG = MemberFragment.class.getSimpleName();

    private static final int OPEN_ADD_MEAL_CREDIT_ACTIVITY = 203;
    private boolean sentToEditMealCreditActivity= false;

    private MealDebitCreditDataSource mealDebitCreditDataSource;
    //private BazarDataSource memberDataSource;
    private Context context;

    private List<Credit> credits;
    private Credit credit;

    private MealDebitCreditAdapter mealDebitCreditAdapter;
    private BazarDataSource bazarDataSource;
    private List<DebitInfo> debitInfoList;

    private ListView lvMealDebits;
    private TextView emptyViewMealDebit;
    private FloatingActionButton fabAddMealDebit;



    public MealCreditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_credit, container, false);

        this.context = getActivity().getApplicationContext();
        mealDebitCreditDataSource = MealDebitCreditDataSource.getInstance(this.context);
        bazarDataSource = BazarDataSource.getInstance(context);
        debitInfoList = new ArrayList<>();
        credits = new ArrayList<>();


        inits(view);


        return view;
    }


    private void inits(View view){

        lvMealDebits = (ListView) view.findViewById(R.id.lv_meals_dc);
        emptyViewMealDebit = (TextView) view.findViewById(R.id.empty_view_meals_dc);
        fabAddMealDebit = (FloatingActionButton) view.findViewById(R.id.fab_add_meals_db);


        lvMealDebits.setEmptyView(emptyViewMealDebit);

        fabAddMealDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentToEditMealCreditActivity = true;
                Intent intent = new Intent(getContext(), MealDebitCreditEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
                startActivityForResult(intent, OPEN_ADD_MEAL_CREDIT_ACTIVITY);
            }
        });


        lvMealDebits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showCreditDetailInDialog(position);
//                Log.e("CrditFragment", "----------------------position: " + position + " id: " + id);
//
//                sentToEditMealCreditActivity = true;
//                Intent intent = new Intent(getContext(), MealDebitCreditEditActivity.class);
//                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
//                intent.putExtra(Constant.MEMBER_ITEM_ID, credits.get(position).getId());
//                Log.e(TAG, "Clicked item id: " + id);
//                startActivityForResult(intent, OPEN_ADD_MEAL_CREDIT_ACTIVITY);
                showNotificationView(view);
            }
        });

        loadCredits();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ADD_MEAL_CREDIT_ACTIVITY) {
            loadCredits();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mealDebitCreditDataSource = MealDebitCreditDataSource.getInstance(this.context);
        if (sentToEditMealCreditActivity) {
            sentToEditMealCreditActivity = false;
            loadCredits();
        }
    }

    private void loadCredits() {
        lvMealDebits.setAdapter(new MealDebitCreditAdapter(getContext(),R.layout.single_member_item, new ArrayList<DebitInfo>()));

        try {
            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            debitInfoList.clear();
            credits.clear();

            //credits = mealDebitCreditDataSource.getCredits(month, year);

            credits = mealDebitCreditDataSource.getCredits(month, year);

            double personCost = bazarDataSource.getTotalBazar(month,year);
            DebitInfo debitInfo;


            for (Credit credit : credits) {

                //debitInfo = new DebitInfo(credit.getName(), credit.getTk(), personCost, (credit.getTk() - personCost));
                debitInfo = new DebitInfo(credit, personCost, (credit.getTk() - personCost));
                debitInfoList.add(debitInfo);

            }


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (debitInfoList.size() == 0) {
                emptyViewMealDebit.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "debitList size: " + debitInfoList.size());
                mealDebitCreditAdapter = new MealDebitCreditAdapter(getContext(), R.layout.single_meal_credit_item, debitInfoList);
                lvMealDebits.setAdapter(mealDebitCreditAdapter);
                //tvFooterDebitAmount.setText("" + expenseDataSource.getTotalDebitAmount());
            }
        }


    }


    private void showNotificationView(View view){

        TextView tvMealDebitName = (TextView) view.findViewById(R.id.tvMealDebitName);

        String mName = tvMealDebitName.getText().toString().trim();

        List<Credit> personCredits = null;

        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        if(mName.trim().length() > 0){

            personCredits = mealDebitCreditDataSource.getPersonCredits(month,year,mName);


            if(personCredits != null && !personCredits.isEmpty()) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                //Text Title
                TextView title = new TextView(context);
                title.setText(mName);
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(getResources().getColor(R.color.colorAccent));
                title.setTextSize(25);

                //alertDialog.setTitle(cName);
                alertDialog.setCustomTitle(title);

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom_credit_detail, null);

                alertDialog.setView(convertView);


                ListView lvCCDetail = (ListView) convertView.findViewById(R.id.lvCCDetail);

                CreditDetailAdapter adapter = new CreditDetailAdapter(context, R.layout.custom_credit_detail_item, personCredits);

                lvCCDetail.setAdapter(adapter);

                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog dialog = alertDialog.create();
                dialog.show();

            }

        }
    }



}
