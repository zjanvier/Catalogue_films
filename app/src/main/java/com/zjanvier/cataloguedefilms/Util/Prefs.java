package com.zjanvier.cataloguedefilms.Util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Janvier Zagabe on 2020-01-02.
 */
public class Prefs {

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);

    }

    public void setSearch(String search) {
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch() {
        return sharedPreferences.getString("search", "Superman");
    }
}