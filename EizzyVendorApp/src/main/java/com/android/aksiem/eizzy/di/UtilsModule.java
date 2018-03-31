package com.android.aksiem.eizzy.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    private static final String APP_PREFS = "AppPrefs";

    @AppScope
    @Provides
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }
}
