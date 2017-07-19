package com.example.dutchman.messcostcalc.constants;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dutchman on 7/13/17.
 */

public class MySharedPref {



    public static void save(Context context, String value){

        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor.putString(Constant.INTENT_FRAGMENT_NAME,value);
        editor.commit();
    }

    public static String get(Context context){

        SharedPreferences prefs = context.getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE);
        String fragmentName = prefs.getString(Constant.INTENT_FRAGMENT_NAME, Constant.Class.HOME);
        return fragmentName;

    }

}
