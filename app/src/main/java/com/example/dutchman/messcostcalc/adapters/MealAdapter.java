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
import com.example.dutchman.messcostcalc.models.Meal;

import java.util.List;

/**
 * Created by dutchman on 7/16/17.
 */

public class MealAdapter extends ArrayAdapter<Meal> {

    public MealAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Meal> meals) {
        super(context, resource, meals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_meal_item, parent, false);
        }

        Meal meal = getItem(position);

        TextView tvSingleMealDate = (TextView) listItemView.findViewById(R.id.tv_single_meal_date);

        tvSingleMealDate.setText(meal.getDate());

        TextView tvSingleMeal = (TextView) listItemView.findViewById(R.id.tv_single_meal);

        tvSingleMeal.setText(meal.getMeal()+"");

//        TextView tvSingleBazarTk = (TextView) listItemView.findViewById(R.id.tv_single_member_advance_tk);
//
//        tvSingleBazarTk.setText("" + meal.getMeal());

        return listItemView;
    }

}
