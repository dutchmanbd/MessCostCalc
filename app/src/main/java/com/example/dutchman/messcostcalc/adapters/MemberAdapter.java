package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 7/11/17.
 */

public class MemberAdapter extends ArrayAdapter<Member> {


    public MemberAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Member> members) {
        super(context, resource, members);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_member_item, parent, false);
        }

        Member member = getItem(position);

        TextView tvSingleMemberName = (TextView) listItemView.findViewById(R.id.tv_single_member_name);

        tvSingleMemberName.setText(member.getName());

        TextView tvSingleMemberDate = (TextView) listItemView.findViewById(R.id.tv_single_member_date);

        tvSingleMemberDate.setText(member.getDate());

        TextView tvSingleMemberAdvanceTk = (TextView) listItemView.findViewById(R.id.tv_single_member_advance_tk);

        tvSingleMemberAdvanceTk.setText("" + member.getAdvanceTk());

        return listItemView;
    }


}
