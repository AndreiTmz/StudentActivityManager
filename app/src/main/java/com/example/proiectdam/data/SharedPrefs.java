package com.example.proiectdam.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Context context;
    private static SharedPrefs instance;

    private SharedPrefs(Context context)
    {
        preferences = context.getSharedPreferences("files",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPrefs getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new SharedPrefs(context);
        }
        return instance;
    }

    public void saveString(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }

    public void saveInt(String key, int value)
    {
        editor.putInt(key,value);
        editor.commit();
    }

    public String getString(String key)
    {
        return preferences.getString(key,"");
    }

    public int getInt(String key)
    {
        return preferences.getInt(key,-1);
    }

}
