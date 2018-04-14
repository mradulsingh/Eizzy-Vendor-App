package com.android.aksiem.eizzy.app;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface ResourceManager {

    String getString(@StringRes final int resourceId);

    int getColor(@ColorRes final int resourceId);

    Drawable getDrawable(@DrawableRes int resourceId);

    float applyDimen(float value, int unit);

    float dp(float dp);
}
