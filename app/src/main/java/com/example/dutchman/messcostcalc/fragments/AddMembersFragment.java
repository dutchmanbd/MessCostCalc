package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.adapters.MemberAdapter;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.db.MemberDataSource;
import com.example.dutchman.messcostcalc.models.Member;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMembersFragment extends Fragment {

    private MemberDataSource memberDataSource;
    private Context context;

    private ListView lv_members;
    private TextView empty_view_members;
    private FloatingActionButton fab_add_member;

    private MemberAdapter memberAdapter;
    private List<Member> members;

    public AddMembersFragment() {
        // Required empty public constructor


    }

    public void setContext(Context context){
//        this.context = context;
//        //handler = new DBHandler(this.context);
//        memberDataSource = new MemberDataSource(this.context);

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
        memberDataSource = MemberDataSource.getInstance(this.context);

        inits(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        memberDataSource = MemberDataSource.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void inits(View view){

        lv_members = (ListView) view.findViewById(R.id.lv_members);
        empty_view_members = (TextView) view.findViewById(R.id.empty_view_members);
        fab_add_member = (FloatingActionButton) view.findViewById(R.id.fab_add_member);


        lv_members.setEmptyView(empty_view_members);

        members = memberDataSource.getMembers(1);

        memberAdapter = new MemberAdapter(context, R.layout.single_member_item, members);

        lv_members.setAdapter(memberAdapter);


        fab_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), );
//                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
//                startActivityForResult(intent, OPEN_DEBIT_EDITOR_ACTIVITY);
            }
        });

    }

//    @Override
//    public void onClick(View view) {
//
//
//        if((etMemberName.getText().toString().trim().length() > 0 && etAdvancedTK.getText().toString().trim().length() > 0) || view.getId() == R.id.btnShowMember || view.getId() == R.id.btnDeleteMember) {
//
//            mName = etMemberName.getText().toString().trim();
//
//            advancedTk = etAdvancedTK.getText().toString().trim();
//            isAvailable = etIsAvailable.getText().toString().trim();
//
//            switch (view.getId()) {
//
//                case R.id.btnAddMember:
//
//
//                    new AlertDialog.Builder(context)
//                            .setTitle("Add Entry")
//                            .setMessage("Are you sure want to add this entry ?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with add
//
//                                    if(handler.insertMember(mDate,mName,advancedTk)){
//
//                                        Toast.makeText(context, mName+" added successfully", Toast.LENGTH_SHORT).show();
//
//                                    } else{
//
//                                        Toast.makeText(context, "Data can not added.", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//
//                    break;
//                case R.id.btnUpdateMember:
//
//                    new AlertDialog.Builder(context)
//                            .setTitle("Update Entry")
//                            .setMessage("Are you sure want to Update this entry ?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with add
//
//                                    if(handler.isUpdateMember(mDate,mName,advancedTk,isAvailable)){
//
//                                        Toast.makeText(context, mName+" update successfully", Toast.LENGTH_SHORT).show();
//
//                                    } else{
//
//                                        Toast.makeText(context, "No data found for update", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//
//
//                    break;
//
//                case R.id.btnShowMember:
//
////                    List<MemberInfo> list = handler.getMembers();
////
////                    StringBuffer sb = new StringBuffer();
////
////                    sb.append("Date\t\tMember Name\t\tAdvanced Tk\n");
////
////                    for(MemberInfo memberInfo : list){
////
////                        sb.append(memberInfo.getDate()).append("\t\t");
////                        sb.append(memberInfo.getpName()).append("\t\t");
////                        sb.append(memberInfo.getpTk()).append("\n");
////                    }
////
////                    new AlertDialog.Builder(context)
////                            .setTitle("Member List")
////                            .setMessage(sb.toString())
////                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int which) {
////
////                                    dialog.dismiss();
////                                }
////                            })
////                            .show();
//
//
//                    CustomMemberDetailDialog dialog = new CustomMemberDetailDialog(context,handler);
//
//                    dialog.showMemberDetailDialog();
//
//
//
//                    break;
//
//                case R.id.btnDeleteMember:
//                    if(etMemberName.getText().toString().trim().length() > 0){
//
//                        String name = etMemberName.getText().toString().trim();
//
//                        if(handler.isDeleteMember(name)){
//
//                            Toast.makeText(context,name+" is successfully deleted.",Toast.LENGTH_SHORT).show();
//
//                        } else{
//                            Toast.makeText(context,name+" is not deleted",Toast.LENGTH_SHORT).show();
//                        }
//                    } else{
//                        Toast.makeText(context,"Fill name field first",Toast.LENGTH_SHORT).show();
//                    }
//
//                    break;
//
//            }
//        } else{
//            Toast.makeText(AddMembersFragment.this.getContext(),"Fill all the field", Toast.LENGTH_SHORT).show();
//        }
//
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_done){
//            if(isFilled()){
//                insertData();
//            }
            return true;

        } else if(id == R.id.action_view){

            return true;
        }

        return false;
    }

//    private void insertData(){
//
//        new AlertDialog.Builder(context)
//                .setTitle("Add Entry")
//                .setMessage("Are you sure want to add this entry ?")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with add
//                        if(handler.insertMember(mDate,mName,advancedTk)){
//                            Toast.makeText(context, mName+" added successfully", Toast.LENGTH_SHORT).show();
//                        } else{
//                            Toast.makeText(context, "Data can not added.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
}
