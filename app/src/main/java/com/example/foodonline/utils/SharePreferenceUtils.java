package com.example.foodonline.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.foodonline.DataModel.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class SharePreferenceUtils {

    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public SharePreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(Constant.SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public static void deleteSave(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

    public static void deleteKeySave(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).commit();
    }

    public static void insertStringData(Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getIntData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int r = preferences.getInt(key, 10);
        return r;
    }

    public static void insertIntData(Context context, String key, int value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getBooleanData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean r = preferences.getBoolean(key, false);
        return r;
    }

    public static void insertBooleanData(Context context, String key, boolean value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }



    public static String getStringData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String r = preferences.getString(key, "");
        return r;
    }

    public static void saveUserInfor(Context context, UserModel userInfor) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userInfor);
        editor.putString("user_info", json);
        editor.commit();
    }
    public static UserModel getUserInfo(Context context) {
        Gson gson = new Gson();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString("user_info", null);
        Type type = new TypeToken<UserModel>() {
        }.getType();
        UserModel userInfo = gson.fromJson(json, type);
        return userInfo;
    }

}
