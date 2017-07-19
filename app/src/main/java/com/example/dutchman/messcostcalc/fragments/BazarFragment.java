package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.activities.BazarEditActivity;
import com.example.dutchman.messcostcalc.adapters.BazarAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.models.Bazar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BazarFragment extends Fragment {


    private static final String TAG = BazarFragment.class.getSimpleName();
    private static final int OPEN_ADD_BAZAR_ACTIVITY = 203;
    private boolean sentToEditBazarActivity= false;

    private Context context;

    private ListView lvBazars;
    private TextView emptyViewBazars;

    private BazarDataSource bazarDataSource;

    private FloatingActionButton fabAddBazar;

    private List<Bazar> bazars;
    private BazarAdapter bazarAdapter;


    public BazarFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bazar, container, false);

        this.context = getActivity().getApplicationContext();

        bazarDataSource = new BazarDataSource(context);

        initializeViews(view);

        return view;
    }

    private void initializeViews(View view){

        lvBazars = (ListView) view.findViewById(R.id.lv_bazars);
        emptyViewBazars = (TextView) view.findViewById(R.id.empty_view_bazars);
        fabAddBazar = (FloatingActionButton) view.findViewById(R.id.fab_add_bazar);


        lvBazars.setEmptyView(emptyViewBazars);

        fabAddBazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentToEditBazarActivity = true;
                Intent intent = new Intent(getContext(), BazarEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
                startActivityForResult(intent, OPEN_ADD_BAZAR_ACTIVITY);
            }
        });


        lvBazars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showCreditDetailInDialog(position);
                Log.e(TAG, "----------------------position: " + position + " id: " + id);

                sentToEditBazarActivity = true;
                Intent intent = new Intent(getContext(), BazarEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
                intent.putExtra(Constant.MEMBER_ITEM_ID, bazars.get(position).getId());
                Log.e(TAG, "Clicked item id: " + id);
                startActivityForResult(intent, OPEN_ADD_BAZAR_ACTIVITY);
            }
        });

        loadBazars();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ADD_BAZAR_ACTIVITY) {
            loadBazars();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sentToEditBazarActivity) {
            sentToEditBazarActivity = false;
            loadBazars();
        }
    }

    private void loadBazars() {
        lvBazars.setAdapter(new BazarAdapter(getContext(),R.layout.single_member_item, new ArrayList<Bazar>()));

        try {

            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            bazars = bazarDataSource.getBazars(month , year); //.getMembers(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bazars.size() == 0) {
            emptyViewBazars.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "debitList size: " + bazars.size());
            bazarAdapter = new BazarAdapter(getContext(), R.layout.single_member_item, bazars);
            lvBazars.setAdapter(bazarAdapter);

        }
    }


}
