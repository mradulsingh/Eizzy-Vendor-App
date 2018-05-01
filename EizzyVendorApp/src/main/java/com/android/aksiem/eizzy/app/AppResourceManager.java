package com.android.aksiem.eizzy.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.di.ApplicationContext;

import javax.inject.Inject;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

@AppScope
public class AppResourceManager implements ResourceManager {

    private Context mContext;

    @Inject
    AppResourceManager(@ApplicationContext Context context) {
        this.mContext = context;
    }

    @Override
    public String getString(@StringRes final int resourceId) {
        return mContext.getString(resourceId);
    }

    @Override
    public String[] getStringArray(@ArrayRes final int resourceId) {
        return mContext.getResources().getStringArray(resourceId);
    }

    @Override
    public int getColor(@ColorRes final int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(mContext, resourceId);
        } else {
            return mContext.getResources().getColor(resourceId);
        }
    }

    @Override
    public Drawable getDrawable(@DrawableRes int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getDrawable(mContext, resourceId);
        } else {
            return mContext.getResources().getDrawable(resourceId);
        }
    }

    @Override
    public float getDimension(@DimenRes int resourceId) {
        return mContext.getResources().getDimension(resourceId);
    }

    @Override
    public float applyDimen(float value, int unit) {
        Resources res = mContext.getResources();
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    @Override
    public float dp(float dp) {
        return applyDimen(dp, COMPLEX_UNIT_DIP);
    }
}