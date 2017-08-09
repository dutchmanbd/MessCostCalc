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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.activities.MealEditActivity;
import com.example.dutchman.messcostcalc.adapters.CustomGoAlertDialog;
import com.example.dutchman.messcostcalc.adapters.MealAdapter;
import com.example.dutchman.messcostcalc.adapters.MealExpendableAdapter;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.db.DBHandler;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.db.MealDataSource;
import com.example.dutchman.messcostcalc.models.Meal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment {



    private static final String TAG = AddMealFragment.class.getSimpleName();
    private static final int OPEN_ADD_MEAL_ACTIVITY = 203;
    private boolean sentToEditMealActivity= false;

    private Context context;

    private ExpandableListView lvMeals;
    private TextView emptyViewMeals;

    private MealDataSource mealDataSource;

    private FloatingActionButton fabAddMeal;

    private List<Meal> meals;
    private HashMap<Meal, List<Meal>> hashMap;
    private MealAdapter mealAdapter;


    public AddMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        context = getContext();

        initializeViews(view);
        return view;
    }


    private void initializeViews(View view){

        lvMeals = (ExpandableListView) view.findViewById(R.id.lv_meals);
        emptyViewMeals = (TextView) view.findViewById(R.id.empty_view_meals);
        fabAddMeal = (FloatingActionButton) view.findViewById(R.id.fab_add_meal);


        lvMeals.setEmptyView(emptyViewMeals);

        Log.d(TAG, "initializeViews: init");

        meals = new ArrayList<>();
        hashMap = new HashMap<>();

        Meal meal = new Meal();
        meal.setDate("15-07-2017");
        meal.setMeal(12.00);

        Meal meal1 = new Meal();
        meal1.setDate("16-07-2017");
        meal1.setMeal(12.00);

        Meal meal2 = new Meal();
        meal2.setDate("17-07-2017");
        meal2.setMeal(12.00);

        meals.add(meal);
        meals.add(meal1);
        meals.add(meal2);

        List<Meal> lstItem = new ArrayList<>();

        Meal meal3 = new Meal();
        meal3.setName("Jewel");
        meal3.setMonth("July");
        meal3.setYear("2017");
        meal3.setMeal(3.00);
        lstItem.add(meal3);

        meal3 = new Meal();
        meal3.setName("Kamrul");
        meal3.setMonth("July");
        meal3.setYear("2017");
        meal3.setMeal(3.00);
        lstItem.add(meal3);

        meal3 = new Meal();
        meal3.setName("Lotifur");
        meal3.setMonth("July");
        meal3.setYear("2017");
        meal3.setMeal(2.00);
        lstItem.add(meal3);

        meal3 = new Meal();
        meal3.setName("Durjoy");
        meal3.setMonth("July");
        meal3.setYear("2017");
        meal3.setMeal(4.00);
        lstItem.add(meal3);

        hashMap.put(meals.get(0), lstItem);
        hashMap.put(meals.get(1), lstItem);
        hashMap.put(meals.get(2), lstItem);


        MealExpendableAdapter adapter = new MealExpendableAdapter(getActivity().getApplicationContext(), meals, hashMap);

        lvMeals.setAdapter(adapter);

        lvMeals.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                //Log.d(TAG, "onChildClick: group: "+groupPosition+" child "+childPosition);

                Meal pMeal = meals.get(groupPosition);
                Meal childMeal = hashMap.get(meals.get(groupPosition)).get(childPosition);
                childMeal.setDate(pMeal.getDate());

//                sentToEditMealActivity = true;
//                Intent intent = new Intent(getContext(), MealEditActivity.class);
//                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
//                intent.putExtra(Constant.MEMBER_ITEM_ID, meals.get(position).getId());
//                Log.e(TAG, "Clicked item id: " + id);
//                startActivityForResult(intent, OPEN_ADD_MEAL_ACTIVITY);

                return false;
            }
        });

        lvMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onItemLongClick: pos: "+position);
                return false;
            }
        });




        fabAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentToEditMealActivity = true;
                Intent intent = new Intent(getContext(), MealEditActivity.class);
                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_ADD);
                startActivityForResult(intent, OPEN_ADD_MEAL_ACTIVITY);
            }
        });
//
//
//        lvMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //showCreditDetailInDialog(position);
//                Log.e(TAG, "----------------------position: " + position + " id: " + id);
//
//                sentToEditMealActivity = true;
//                Intent intent = new Intent(getContext(), MealEditActivity.class);
//                intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_TYPE_EDIT);
//                intent.putExtra(Constant.MEMBER_ITEM_ID, meals.get(position).getId());
//                Log.e(TAG, "Clicked item id: " + id);
//                startActivityForResult(intent, OPEN_ADD_MEAL_ACTIVITY);
//            }
//        });
//
//        loadMeals();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ADD_MEAL_ACTIVITY) {
            loadMeals();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sentToEditMealActivity) {
            sentToEditMealActivity = false;
            loadMeals();
        }
    }

    private void loadMeals() {
        lvMeals.setAdapter(new MealAdapter(getContext(),R.layout.single_member_item, new ArrayList<Meal>()));

        try {

            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            meals = mealDataSource.getMeals(month , year); //.getMembers(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (meals.size() == 0) {
            emptyViewMeals.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "debitList size: " + meals.size());
            mealAdapter = new MealAdapter(getContext(), R.layout.single_member_item, meals);
            lvMeals.setAdapter(mealAdapter);

        }
    }



}
