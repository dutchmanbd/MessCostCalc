package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.dutchman.messcostcalc.activities.MemberEditActivity;
import com.example.dutchman.messcostcalc.adapters.MemberAdapter;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.MemberDataSource;
import com.example.dutchman.messcostcalc.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {

    private static final String TAG = MemberFragment.class.getSimpleName();

    private static final int OPEN_ADD_MEMBER_ACTIVITY = 203;
    private boolean sentToEditMemberActivity= false;

    private MemberDataSource memberDataSource;
    private Context context;

    private ListView lv_members;
    private TextView empty_view_members;
    private FloatingActionButton fab_add_member;

    private MemberAdapter memberAdapter;
    private List<Member> members;


    public MemberFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_members, container, false);

        this.context = getActivity().getApplicationContext();
        memberDataSource = new MemberDataSource(this.context);


        inits(view);

        return view;
    }

    private void inits(View view){

        lv_members = (ListView) view.findViewById(R.id.lv_members);
        empty_view_members = (TextView) view.findViewById(R.id.empty_view_members);
        fab_add_member = (FloatingActionButton) view.findViewById(R.id.fab_add_member);


        lv_members.setEmptyView(empty_view_members);

        fab_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentToEditMemberActivity = true;
                Intent intent = new Intent(getContext(), MemberEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
                startActivityForResult(intent, OPEN_ADD_MEMBER_ACTIVITY);
            }
        });


        lv_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showCreditDetailInDialog(position);
                Log.e("CrditFragment", "----------------------position: " + position + " id: " + id);

                sentToEditMemberActivity = true;
                Intent intent = new Intent(getContext(), MemberEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
                intent.putExtra(Constant.MEMBER_ITEM_ID, members.get(position).getId());
                Log.e(TAG, "Clicked item id: " + id);
                startActivityForResult(intent, OPEN_ADD_MEMBER_ACTIVITY);
            }
        });

        loadMembers();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ADD_MEMBER_ACTIVITY) {
            loadMembers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sentToEditMemberActivity) {
            sentToEditMemberActivity = false;
            loadMembers();
        }
    }

    private void loadMembers() {
        lv_members.setAdapter(new MemberAdapter(getContext(),R.layout.single_member_item, new ArrayList<Member>()));

        try {
            members = memberDataSource.getMembers(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (members.size() == 0) {
            empty_view_members.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "debitList size: " + members.size());
            memberAdapter = new MemberAdapter(getContext(), R.layout.single_member_item, members);
            lv_members.setAdapter(memberAdapter);
            //tvFooterDebitAmount.setText("" + expenseDataSource.getTotalDebitAmount());
        }
    }



}
