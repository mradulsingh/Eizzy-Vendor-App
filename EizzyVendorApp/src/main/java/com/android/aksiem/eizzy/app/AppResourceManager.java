package com.android.aksiem.eizzy.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.di.ApplicationContext;

import javax.inject.Inject;

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

}