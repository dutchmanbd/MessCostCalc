package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.constants.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private RadioGroup rgMealSettings;
    private RadioButton rbWithoutMeal,rbWithMeal;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private FragmentManager manager;
    private HomeFragment homeFragment;

    private Context context;

    private int radioButtonId;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){

        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        sharedPref = context.getSharedPreferences(Constant.MY_PREFS_NAME,Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        rgMealSettings = (RadioGroup) view.findViewById(R.id.rgMealSettings);
        rbWithoutMeal  = (RadioButton) view.findViewById(R.id.rbWithoutMeal);
        rbWithMeal     = (RadioButton) view.findViewById(R.id.rbWithMeal);

        loadSettingsPreference();
        radioButtonId = rgMealSettings.getCheckedRadioButtonId();

        rgMealSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.rbWithoutMeal:
                        editor.putBoolean(Constant.SETTINGS_PREFS_WITHOUT_MEAL, true);
                        editor.putBoolean(Constant.SETTINGS_PREFS_WITH_MEAL, false);
                        editor.commit();
                        if(checkedId != radioButtonId) {


                            homeFragment = new HomeFragment();
                            homeFragment.setContext(context);

                            manager = getActivity().getSupportFragmentManager();

                            manager.beginTransaction().replace(R.id.rlContent, homeFragment, homeFragment.getTag()).commit();
                        }

                        break;

                    case R.id.rbWithMeal:
                        editor.putBoolean(Constant.SETTINGS_PREFS_WITH_MEAL, true);
                        editor.putBoolean(Constant.SETTINGS_PREFS_WITHOUT_MEAL, false);
                        editor.commit();

                        if(checkedId != radioButtonId) {


                            homeFragment = new HomeFragment();
                            homeFragment.setContext(context);

                            manager = getActivity().getSupportFragmentManager();

                            manager.beginTransaction().replace(R.id.rlContent, homeFragment, homeFragment.getTag()).commit();
                        }

                        break;

                }


            }
        });



        return view;
    }

    private void saveSettingsPreference() {
        SharedPreferences sharedPref = context.getSharedPreferences("settings",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        int selectedId = rgMealSettings.getCheckedRadioButtonId();
        if(selectedId == R.id.rbWithoutMeal)
            editor.putBoolean(Constant.SETTINGS_PREFS_WITHOUT_MEAL, true);
        else
            editor.putBoolean(Constant.SETTINGS_PREFS_WITH_MEAL, true);

        editor.commit();
    }

    public void loadSettingsPreference(){
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.MY_PREFS_NAME,Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(Constant.SETTINGS_PREFS_WITHOUT_MEAL, true))
            rbWithoutMeal.setChecked(true);
        else if(sharedPref.getBoolean(Constant.SETTINGS_PREFS_WITH_MEAL,true))
            rbWithMeal.setChecked(true);
    }

}
