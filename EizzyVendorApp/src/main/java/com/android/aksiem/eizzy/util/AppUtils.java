package com.android.aksiem.eizzy.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by napendersingh on 25/03/18.
 */
public class AppUtils {

    public static int getColor(Activity activity, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getColor(resourceId);
        } else {
            return activity.getResources().getColor(resourceId);
        }
    }

    public static Drawable getDrawable(Activity activity, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getDrawable(resourceId);
        } else {
            return activity.getResources().getDrawable(resourceId);
        }
    }

    public static void showInfoMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
