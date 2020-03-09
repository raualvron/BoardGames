package com.example.boardgames.Classes;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefService {

    SharedPreferences settings;
    SharedPreferences.Editor editor;


    public SharedPrefService(Context context) {
        this.settings = context.getSharedPreferences(ConstantsHelper.SHARE_PREFERENCE_FILE, 0);
        this.editor = settings.edit();
    }

    public void setSharedPrefString(String key, String value){
        this.editor.putString(key, value);
        this.editor.apply();
    }

    public void setSharedPrefInt(String key, int value){
        this.editor.putInt(key, value);
        this.editor.apply();
    }

    public void setSharedPrefBoolean(String key, boolean value){
        this.editor.putBoolean(key, value);
        this.editor.apply();
    }

    public String getSharedPrefString(String key){
        return this.settings.getString(key, "");
    }


    public int getSharedPrefInt(String key){
        return this.settings.getInt(key, 0);
    }

    public boolean getSharedPrefBoolean(String key){
        return this.settings.getBoolean(key, false);
    }

    public void clearAllSharedPref() {
        this.settings.edit().clear().apply();
    }

    public void clearSharedPrefByKey(String key) {
        this.settings.edit().remove(key).apply();
    }
}
