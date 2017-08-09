package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.models.DebitInfo;
import com.example.dutchman.messcostcalc.models.PersonCredit;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.adapters.CustomAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomCreditDetailAdapter;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DebitFragment extends Fragment {


    private DebitInfo debitInfo;
    private List<DebitInfo> list;

    private ListView lvDebitResult;

    private Context context;

    private String destName;

    private DBHandler handler;

    private String month,year;


    public DebitFragment() {
        // Required empty public constructor
    }

    public void setDest(Context context,String destName){
        this.context = context;
        this.destName = destName;

        this.handler = DBHandler.getInstance(this.context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debit, container, false);

//
//
//        list = new ArrayList<>();
//
//
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        int width = display.getWidth();  // deprecated
//        int height = display.getHeight();  // deprecated
//
//        width /= 4;
//
//        TextView tvDebitName = (TextView) view.findViewById(R.id.tvDebitName);
//        TextView tvDebitCredit = (TextView) view.findViewById(R.id.tvDebitCreditTk);
//        TextView tvDebitDebit = (TextView) view.findViewById(R.id.tvDebitDebitTk);
//        TextView tvDebitBalance = (TextView) view.findViewById(R.id.tvDebitBalance);
//
//
//        tvDebitName.getLayoutParams().width = width;
//        tvDebitCredit.getLayoutParams().width = width;
//        tvDebitDebit.getLayoutParams().width = width;
//        tvDebitBalance.getLayoutParams().width = width;
//
//        lvDebitResult = (ListView) view.findViewById(R.id.lvDebitResult);
//
//
//        month = new SimpleDateFormat("MMMM").format(new Date());
//        year = new SimpleDateFormat("yyyy").format(new Date());
//
//        //lvDebitResult.setEnabled(true);
//
//        int personCost = 0;
//        if(destName.equals("meal")){}
////            personCost = handler.getBazarPerCost(month,year);
//        else if(destName.equals("rent"))
//            personCost = handler.getPerheadCostFromRent(month,year);
//
//        if(handler.getNumberOfMembers() > 0) {
//
//
//            List<PersonCredit> personCredits = handler.getPersonCredit(destName);
//
//            if(!personCredits.isEmpty()) {
//
//                for (PersonCredit personCredit : personCredits) {
//
//                    debitInfo = new DebitInfo(personCredit.getName(), personCredit.getTk(), personCost, (personCredit.getTk() - personCost));
//                    list.add(debitInfo);
//
//                }
//
//                CustomAdapter adapter = new CustomAdapter(DebitFragment.this.getContext(), R.layout.custom_row, list);
//
//                lvDebitResult.setAdapter(adapter);
//            } else{
//                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
//            }
//
//        } else{
//            //Toast.makeText(context, "No member found.", Toast.LENGTH_SHORT).show();
//            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);
//
//            dialog.goToAddMemeberFragment();
//        }
//
//        lvDebitResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tvCustomName = (TextView) view.findViewById(R.id.tvCustomName);
//
//                String cName = tvCustomName.getText().toString().trim();
//
//                List<PersonCredit> list1 = null;
//
//                if(destName.trim().length() > 0){
//
//                    if(cName.trim().length() > 0){
//
//                        list1 = handler.getPersonCreditDetails(destName,month,year,cName);
//
//
//                        if(list1 != null && !list1.isEmpty()) {
//
//                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//                            //Text Title
//                            TextView title = new TextView(context);
//                            title.setText(cName);
//                            title.setPadding(10, 10, 10, 10);
//                            title.setGravity(Gravity.CENTER);
//                            title.setTextColor(getResources().getColor(R.color.colorAccent));
//                            title.setTextSize(25);
//
//                            //alertDialog.setTitle(cName);
//                            alertDialog.setCustomTitle(title);
//
//                            LayoutInflater inflater = getActivity().getLayoutInflater();
//                            View convertView = (View) inflater.inflate(R.layout.custom_credit_detail, null);
//
//                            alertDialog.setView(convertView);
//
//
//                            ListView lvCCDetail = (ListView) convertView.findViewById(R.id.lvCCDetail);
//
//                            Button btnOK = (Button) convertView.findViewById(R.id.btnOK);
//
//                            CustomCreditDetailAdapter adapter = new CustomCreditDetailAdapter(context, R.layout.custom_credit_detail_item, list1);
//
//                            lvCCDetail.setAdapter(adapter);
//
//                            alertDialog.setCancelable(false);
//
//                            final AlertDialog dialog = alertDialog.create();
//
//                            btnOK.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                    dialog.dismiss();
//                                }
//                            });
//
//                            dialog.show();
//
//                        }
//
//                    }
//
//
//                }
//
//            }
//        });

        return view;
    }

}
