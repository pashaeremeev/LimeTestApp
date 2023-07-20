package com.example.practice;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesRepo {

    public static final String APP_PREFERENCES = "APP_PREFERENCES";

    private SharedPreferences preferences;

    public PreferencesRepo(Context context) {
        this.preferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
    }

    public void save(String listJsonString, String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, listJsonString);
        editor.commit();
    }

    public String get(String key) {
        return preferences.getString(key, "");
    }
}
