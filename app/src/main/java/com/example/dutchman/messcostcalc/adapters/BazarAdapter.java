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
import com.example.dutchman.messcostcalc.models.Bazar;
import com.example.dutchman.messcostcalc.models.Member;

import java.util.List;

/**
 * Created by dutchman on 7/13/17.
 */

public class BazarAdapter extends ArrayAdapter<Bazar> {

    public BazarAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Bazar> bazars) {
        super(context, resource, bazars);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_member_item, parent, false);
        }

        Bazar bazar = getItem(position);

        TextView tvSingleMemberName = (TextView) listItemView.findViewById(R.id.tv_single_member_name);

        tvSingleMemberName.setText(bazar.getmName());

        TextView tvSingleBazaDate = (TextView) listItemView.findViewById(R.id.tv_single_member_date);

        tvSingleBazaDate.setText(bazar.getDate());

        TextView tvSingleBazarTk = (TextView) listItemView.findViewById(R.id.tv_single_member_advance_tk);

        tvSingleBazarTk.setText("" + bazar.getTk());

        return listItemView;
    }
}
