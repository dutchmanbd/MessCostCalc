package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.activities.RentDebitCreditEditActivity;
import com.example.dutchman.messcostcalc.adapters.RentDebitCreditAdapter;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.RentDebitCreditDataSource;
import com.example.dutchman.messcostcalc.db.RentInfoDatatSource;
import com.example.dutchman.messcostcalc.models.Credit;
import com.example.dutchman.messcostcalc.models.DebitInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentCreditFragment extends Fragment {

    private static final String TAG = MemberFragment.class.getSimpleName();

    private static final int OPEN_ADD_RENT_CREDIT_ACTIVITY = 203;
    private boolean sentToEditRentCreditActivity= false;

    private RentDebitCreditDataSource rentDebitCreditDataSource;
    //private BazarDataSource memberDataSource;
    private Context context;

    private List<Credit> credits;
    private Credit credit;
    private List<DebitInfo> debitInfoList;

    private RentDebitCreditAdapter rentDebitCreditAdapter;
    private RentInfoDatatSource rentInfoDatatSource;

    private ListView lvRentDebits;
    private TextView emptyViewRentDebit;
    private FloatingActionButton fabAddRentDebit;



    public RentCreditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_credit, container, false);

        context = getActivity().getApplicationContext();

        inits(view);

        return view;
    }

    private void inits(View view){

        credits = new ArrayList<>();
        debitInfoList = new ArrayList<>();

        rentInfoDatatSource = RentInfoDatatSource.getInstance(context);
        rentDebitCreditDataSource = RentDebitCreditDataSource.getInstance(context);

        lvRentDebits = (ListView) view.findViewById(R.id.lv_rent_dc);
        emptyViewRentDebit = (TextView) view.findViewById(R.id.empty_view_rent_dc);
        fabAddRentDebit = (FloatingActionButton) view.findViewById(R.id.fab_add_rent_db);


        lvRentDebits.setEmptyView(emptyViewRentDebit);

        fabAddRentDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentToEditRentCreditActivity = true;
                Intent intent = new Intent(getContext(), RentDebitCreditEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
                startActivityForResult(intent, OPEN_ADD_RENT_CREDIT_ACTIVITY);
            }
        });


//        lvRentDebits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //showCreditDetailInDialog(position);
//                Log.e("CrditFragment", "----------------------position: " + position + " id: " + id);
//
//                sentToEditRentCreditActivity = true;
//                Intent intent = new Intent(getContext(), RentDebitCreditEditActivity.class);
//                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
//                intent.putExtra(Constant.MEMBER_ITEM_ID, credits.get(position).getId());
//                Log.e(TAG, "Clicked item id: " + id);
//                startActivityForResult(intent, OPEN_ADD_RENT_CREDIT_ACTIVITY);
//            }
//        });

        loadCredits();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ADD_RENT_CREDIT_ACTIVITY) {
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
        //rentDebitCreditDataSource = RentDebitCreditDataSource.getInstance(this.context);
        if (sentToEditRentCreditActivity) {
            sentToEditRentCreditActivity = false;
            loadCredits();
        }
    }

    private void loadCredits() {
        lvRentDebits.setAdapter(new RentDebitCreditAdapter(getContext(),R.layout.single_member_item, new ArrayList<DebitInfo>()));


        credits.clear();
        debitInfoList.clear();

        try {
            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            credits = rentDebitCreditDataSource.getCredits(month, year);
            double debit = rentInfoDatatSource.getPerheadCostFromRent(month, year);

            for (Credit credit : credits){

                DebitInfo debitInfo = new DebitInfo(credit, debit, (credit.getTk()-debit));

                debitInfoList.add(debitInfo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (debitInfoList.size() == 0) {
            emptyViewRentDebit.setVisibility(View.VISIBLE);
        } else {

            rentDebitCreditAdapter = new RentDebitCreditAdapter(getContext(), R.layout.single_member_item, debitInfoList);
            lvRentDebits.setAdapter(rentDebitCreditAdapter);
            //tvFooterDebitAmount.setText("" + expenseDataSource.getTotalDebitAmount());
        }
    }


}
