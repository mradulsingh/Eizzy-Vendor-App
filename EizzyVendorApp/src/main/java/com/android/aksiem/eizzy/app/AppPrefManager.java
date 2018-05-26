package com.android.aksiem.eizzy.app;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.di.AppScope;

import javax.inject.Inject;

@AppScope
public class AppPrefManager {

    private SharedPreferences sharedPreferences;

    @Inject
    public AppPrefManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getString(@NonNull String key) {
        return sharedPreferences.getString(key, "");
    }

    public String getString(@NonNull String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public int getInt(@NonNull String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public int getInt(@NonNull String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public float getFloat(@NonNull String key) {
        return sharedPreferences.getFloat(key, 0.0F);
    }

    public float getFloat(@NonNull String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean getBoolean(@NonNull String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void setPref(@NonNull String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setPref(@NonNull String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setPref(@NonNull String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setPref(@NonNull String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
}
