package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.util.Logger;

public class AppTextInputLayout extends TextInputLayout {

    public AppTextInputLayout(Context context) {
        super(context);
    }

    public AppTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AppTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        setErrorTextAppearance(R.style.ErrorMsgText);
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);

        try {
            EditText editText = getEditText();
            if (editText instanceof AppEditText) {
                ((AppEditText) editText).setError(error);
            }
        } catch (Exception e) {
            Logger.e("Exception in setting error message");
        }
    }

}