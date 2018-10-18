package com.example.android.popularmoviesstage2.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefrences {

    private static final String PREF_SORT_SELECTED = "pref_selected";

    private static final String SHARED_PREF_NAME = "save_contents";
    private static AppPrefrences mInstance;
    private final SharedPreferences pref;

    //constructor
    private AppPrefrences(Context context) {
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPrefrences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppPrefrences(context);
        }
        return mInstance;
    }

    public String getPrefUrlSelected() {
        return pref.getString(PREF_SORT_SELECTED, null);

    }

    public void setPrefUrlSelected(String selected) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_SORT_SELECTED, selected);
        editor.apply();
        editor.commit();
    }
}
