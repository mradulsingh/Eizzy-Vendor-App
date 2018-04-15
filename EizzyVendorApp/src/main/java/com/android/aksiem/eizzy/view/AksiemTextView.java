package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.android.aksiem.eizzy.R;

/**
 * Created by pdubey on 10/04/18.
 */

public class AksiemTextView extends AppCompatTextView {

    public AksiemTextView(Context context) {
        super(context);
    }

    public AksiemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AksiemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AksiemTextView);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                drawableLeft = attributeArray.getDrawable(
                        R.styleable.AksiemTextView_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(
                        R.styleable.AksiemTextView_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(
                        R.styleable.AksiemTextView_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(
                        R.styleable.AksiemTextView_drawableTopCompat);

            } else {

                final int drawableLeftId = attributeArray.getResourceId(
                        R.styleable.AksiemTextView_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(
                        R.styleable.AksiemTextView_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(
                        R.styleable.AksiemTextView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(
                        R.styleable.AksiemTextView_drawableTopCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }

            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }
}
