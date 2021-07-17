package com.akash.internationaltimes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceUtils {
    private static final String TAG = SharedPreferenceUtils.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private static SharedPreferenceUtils sharedPrefUtilsInstance;

    private static final String PREFERENCE_NAME="com.akash.internationaltimes";


    //Shared Preference Constansts
    private static final String LOCATION="location";
    private static final String CATEGORY="category";


    //Initialization Methods
    private SharedPreferenceUtils(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if(context!=null){
            sharedPrefUtilsInstance = new SharedPreferenceUtils(context);
        }
    }

    public static SharedPreferenceUtils getInstance(){
        if(sharedPrefUtilsInstance == null){
            Log.e(TAG, " Prefs instance is null");
        }
        return sharedPrefUtilsInstance;
    }


    //Preference handler methods
    private void setStringPref(String name,String value){
        sharedPreferences.edit().putString(name,value).apply();
    }


    //Shared Preference getters and setters
    public void setLocation(String location){
        setStringPref(LOCATION,location);
    }
    public String getLocation(){
        return sharedPreferences.getString(LOCATION,"");
    }

    public void setCategory(String category){
        setStringPref(CATEGORY,category);
    }
    public String getCategory(){
        return sharedPreferences.getString(CATEGORY,"");
    }


}
