package com.android.aksiem.eizzy.ui.toast;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;

import static android.widget.Toast.LENGTH_LONG;

public class ToastViewModel extends ViewModel {

    AppResourceManager appResourceManager;

    private String message;

    @DrawableRes
    private int icResId;

    @ColorRes
    @DrawableRes
    private int bgToastResId;

    @ColorRes
    private int textColorResId;

    @DimenRes
    private int textSizeResId;

    private int duration;

    public ToastViewModel(AppResourceManager appResourceManager) {
        this.appResourceManager = appResourceManager;

        //set default values
        textSizeResId = R.dimen.font_14;
        bgToastResId = R.color.colorAccent;
        textColorResId = R.color.colorPrimaryText;
        duration = LENGTH_LONG;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIcResId(int icResId) {
        this.icResId = icResId;
    }

    public void setBgToastResId(int bgToastResId) {
        this.bgToastResId = bgToastResId;
    }

    public void setTextColorResId(int textColorResId) {
        this.textColorResId = textColorResId;
    }

    public void setTextSizeResId(int textSizeResId) {
        this.textSizeResId = textSizeResId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public int getIcResId() {
        return icResId;
    }

    public int getBgToast() {
        if (bgToastResId > 0)
            return appResourceManager.getColor(bgToastResId);

        return 0;
    }

    public int getTextColor() {
        if (textColorResId > 0)
            return appResourceManager.getColor(textColorResId);

        return 0;
    }

    public float getTextSize() {
        if (textSizeResId > 0) {
            return appResourceManager.getDimension(textSizeResId);
        } else {
            return 0;
        }
    }

    public int getDuration() {
        return duration;
    }
}
