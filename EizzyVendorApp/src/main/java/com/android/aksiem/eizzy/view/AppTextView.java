package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.android.aksiem.eizzy.R;

/**
 * Created by Mradul on 10/06/18.
 */

public class AppTextView extends AppCompatTextView {

    private Drawable drawableLeft;
    private Drawable drawableRight;
    private Drawable drawableBottom;
    private Drawable drawableTop;


    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public void setDrawableLeft(Drawable drawableLeft, boolean setDrawableNow) {
        this.drawableLeft = drawableLeft;
        if (setDrawableNow)
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
    }

    public void setDrawableRight(Drawable drawableRight, boolean setDrawableNow) {
        this.drawableRight = drawableRight;
        if (setDrawableNow)
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
    }

    public void setDrawableBottom(Drawable drawableBottom, boolean setDrawableNow) {
        this.drawableBottom = drawableBottom;
        if (setDrawableNow)
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
    }

    public void setDrawableTop(Drawable drawableTop, boolean setDrawableNow) {
        this.drawableTop = drawableTop;
        if (setDrawableNow)
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AppTextView);

            drawableLeft = null;
            drawableRight = null;
            drawableBottom = null;
            drawableTop = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                setDrawableLeft(attributeArray.getDrawable(
                        R.styleable.AppTextView_drawableLeftCompat), false);
                setDrawableRight(attributeArray.getDrawable(
                        R.styleable.AppTextView_drawableRightCompat), false);
                setDrawableBottom(attributeArray.getDrawable(
                        R.styleable.AppTextView_drawableBottomCompat), false);
                setDrawableTop(attributeArray.getDrawable(
                        R.styleable.AppTextView_drawableTopCompat), false);

            } else {

                final int drawableLeftId = attributeArray.getResourceId(
                        R.styleable.AppTextView_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(
                        R.styleable.AppTextView_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(
                        R.styleable.AppTextView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(
                        R.styleable.AppTextView_drawableTopCompat, -1);

                if (drawableLeftId != -1)
                    setDrawableLeft(AppCompatResources.getDrawable(context, drawableLeftId),
                            false);
                if (drawableRightId != -1)
                    setDrawableRight(AppCompatResources.getDrawable(context, drawableRightId),
                            false);
                if (drawableBottomId != -1)
                    setDrawableBottom(AppCompatResources.getDrawable(context, drawableBottomId),
                            false);
                if (drawableTopId != -1)
                    setDrawableTop(AppCompatResources.getDrawable(context, drawableTopId),
                            false);
            }

            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                    drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }
}
