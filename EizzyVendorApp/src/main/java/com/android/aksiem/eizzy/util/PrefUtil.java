package com.android.aksiem.eizzy.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

/**
 * Created by napendersingh on 11/12/16.
 */

public class PrefUtil {
    private static SharedPreferences mSecurePrefs;
    private static SharedPreferences mPrefs;
    private static Gson gson = new Gson();
    public static final String PREF_APP_AUTH_TOKEN = "pref_app_auth_token";

    private static SharedPreferences getSecuredSharedPreferences(Context context) {
        if (mSecurePrefs == null) {
            mSecurePrefs = new SecurePreferences(context, "Eizzy_Vendor", context.getPackageName());
        }
        return mSecurePrefs;
    }

    private static SharedPreferences getSharedPreferences(Activity context) {
        if (mPrefs == null) {
            mPrefs = context.getPreferences(Context.MODE_PRIVATE);
        }
        return mPrefs;
    }

    public static void setSecuredPref(Context context, String key, String value) {
        if (context == null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            throw new IllegalArgumentException();
        }

        getSecuredSharedPreferences(context).edit().putString(key, value).commit();
    }

    public static String getSecuredPref(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        return getSecuredSharedPreferences(context).getString(key, "");
    }

    public static String getStringPref(Activity context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        return getSharedPreferences(context).getString(key, "");
    }

    public static void setPref(Activity context, String key, String value) {
        if (context == null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            throw new IllegalArgumentException();
        }
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getIntPref(Activity context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static void setPref(Activity context, String key, int value) {
        if (context == null || TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
