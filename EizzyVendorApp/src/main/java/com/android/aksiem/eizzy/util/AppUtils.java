package com.android.aksiem.eizzy.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by napendersingh on 25/03/18.
 */
public class AppUtils {

    public static void showInfoMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
