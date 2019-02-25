package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.android.aksiem.eizzy.R;

/**
 * Created by Mradul on 10/06/18.
 */

public class AppButton extends AppCompatButton {

    private Drawable vectorDrawable;

    public AppButton(Context context) {
        super(context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public Drawable getVectorDrawable() {
        return vectorDrawable;
    }

    public void setVectorDrawable(Drawable vectorDrawable) {
        this.vectorDrawable = vectorDrawable;
        if (vectorDrawable != null) {
            setBackground(vectorDrawable);
        }
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            vectorDrawable = null;
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.AppButton);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                vectorDrawable = a.getDrawable(R.styleable.AppButton_vectorDrawable);
            } else {
                final int drawableId = a.getResourceId(R.styleable.AppButton_vectorDrawable,
                        -1);
                vectorDrawable = AppCompatResources.getDrawable(context, drawableId);
            }
            setVectorDrawable(vectorDrawable);
            a.recycle();
        }
    }
}
