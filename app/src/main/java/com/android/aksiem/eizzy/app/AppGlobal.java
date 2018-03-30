package com.android.aksiem.eizzy.app;


import com.android.aksiem.eizzy.BuildConfig;

/**
 * Created by napendersingh on 25/03/18.
 */

public class AppGlobal {
    public static AppEnv APP_ENV = resolveAppEnv();

    public static final int ONE_SECOND = 1000; //1000 ms = 1 sec
    public static final int ONE_MIN = 60 * ONE_SECOND;
    public static final int ONE_HOUR = 60 * ONE_MIN;
    public static final long ONE_DAY = 24 * ONE_HOUR;

    private static AppEnv resolveAppEnv() {
        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
            APP_ENV = AppEnv.PROD;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            APP_ENV = AppEnv.DEV;
        }
        return APP_ENV;
    }
}
