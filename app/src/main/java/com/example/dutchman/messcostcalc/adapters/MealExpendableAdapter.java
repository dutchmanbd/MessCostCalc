package com.example.dutchman.messcostcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.models.Meal;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dutchman on 7/16/17.
 */

public class MealExpendableAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<Meal> listDataHeaders;
    private HashMap<Meal, List<Meal>> listHashMap;

    public MealExpendableAdapter(Context context, List<Meal> listDataHeaders, HashMap<Meal, List<Meal>> listHashMap) {

        this.context = context;
        this.listDataHeaders = listDataHeaders;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Meal mealTitle = (Meal) getGroup(groupPosition);

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_meal_item, null);
        }

        TextView tvSingleMealDate = (TextView) convertView.findViewById(R.id.tv_single_meal_date);
        TextView tvSingleMeal = (TextView) convertView.findViewById(R.id.tv_single_meal);

        tvSingleMealDate.setText(mealTitle.getDate());
        tvSingleMeal.setText(mealTitle.getMeal()+"");


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Meal meal = (Meal) getChild(groupPosition, childPosition);


        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_child_meal_item, null);
        }

        TextView tvSingleChildMealName = (TextView) convertView.findViewById(R.id.tv_single_child_meal_name);
        TextView tvSingleChildMeal = (TextView) convertView.findViewById(R.id.tv_single_child_meal);

        tvSingleChildMealName.setText(meal.getName());
        tvSingleChildMeal.setText(meal.getMeal()+"");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
