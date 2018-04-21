package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.aksiem.eizzy.util.Logger;

import java.lang.reflect.Field;

public class AksiemTextInputLayout extends TextInputLayout {

    public AksiemTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);

        try {
            Field errorViewField = TextInputLayout.class.getDeclaredField("mErrorView");
            errorViewField.setAccessible(true);
            TextView errorView = (TextView) errorViewField.get(this);
            if (errorView != null) {
                //errorView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edittext_field_error, 0, 0, 0);
            }
        } catch (Exception e) {
            Logger.e("Exception in setting error message");
        }
    }

}