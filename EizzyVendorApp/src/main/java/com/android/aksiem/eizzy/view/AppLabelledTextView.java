package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aksiem.eizzy.R;

/**
 * Created by Mradul on 06/05/18.
 */

public class AppLabelledTextView extends RelativeLayout {
    private TextView tvLabel;
    private TextView tvText;

    public AppLabelledTextView(Context context) {
        super(context);
    }

    public AppLabelledTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AppLabelledTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            inflate(getContext(), R.layout.labelled_textview, this);
            this.tvLabel = (TextView) findViewById(R.id.tvLabel);
            this.tvText = (TextView) findViewById(R.id.tvText);

            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AppLabelledTextView);
            String text = attributeArray.getString(R.styleable.AppLabelledTextView_text);
            String textLabel = attributeArray.getString(R.styleable.AppLabelledTextView_labelText);
            setText(text);
            setTextLabel(textLabel);
            setTextAppearanceText(context, attributeArray.getResourceId(R.styleable.AppLabelledTextView_textAppearanceText, R.style.TextAppearance_AppLabelledTextViewText));
            setTextAppearanceLabel(context, attributeArray.getResourceId(R.styleable.AppLabelledTextView_textAppearanceLabel, R.style.TextAppearance_AppLabelledTextViewLabel));
            setTvLabelDrawables(context, attributeArray);
            setTvTextDrawables(context, attributeArray);
            attributeArray.recycle();
        }
    }

    private void setTvLabelDrawables(Context context, TypedArray attributeArray) {
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        Drawable drawableBottom = null;
        Drawable drawableTop = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableLeft = attributeArray.getDrawable(
                    R.styleable.AppLabelledTextView_drawableLabelLeftCompat);
            drawableRight = attributeArray.getDrawable(
                    R.styleable.AppLabelledTextView_drawableLabelRightCompat);
            drawableBottom = attributeArray.getDrawable(
                    R.styleable.AppLabelledTextView_drawableLabelBottomCompat);
            drawableTop = attributeArray.getDrawable(
                    R.styleable.AppLabelledTextView_drawableLabelTopCompat);
        } else {
            final int drawableLeftId = attributeArray.getResourceId(
                    R.styleable.AppLabelledTextView_drawableLabelLeftCompat, -1);
            final int drawableRightId = attributeArray.getResourceId(
                    R.styleable.AppLabelledTextView_drawableLabelRightCompat, -1);
            final int drawableBottomId = attributeArray.getResourceId(
                    R.styleable.AppLabelledTextView_drawableLabelBottomCompat, -1);
            final int drawableTopId = attributeArray.getResourceId(
                    R.styleable.AppLabelledTextView_drawableLabelTopCompat, -1);

            if (drawableLeftId != -1)
                drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
            if (drawableRightId != -1)
                drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
            if (drawableBottomId != -1)
                drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
            if (drawableTopId != -1)
                drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
        }

        this.tvLabel.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                drawableRight, drawableBottom);
    }

    private void setTvTextDrawables(Context context, TypedArray attributeArray) {
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        Drawable drawableBottom = null;
        Drawable drawableTop = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableLeft = attributeArray.getDrawable(
                    R.styleable.AppTextView_drawableLeftCompat);
            drawableRight = attributeArray.getDrawable(
                    R.styleable.AppTextView_drawableRightCompat);
            drawableBottom = attributeArray.getDrawable(
                    R.styleable.AppTextView_drawableBottomCompat);
            drawableTop = attributeArray.getDrawable(
                    R.styleable.AppTextView_drawableTopCompat);
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
                drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
            if (drawableRightId != -1)
                drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
            if (drawableBottomId != -1)
                drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
            if (drawableTopId != -1)
                drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
        }

        this.tvText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
                drawableRight, drawableBottom);
    }

    public void setText(String text) {
        this.tvText.setText(text);
    }

    public void setTextLabel(String text) {
        this.tvLabel.setText(text);
    }

    public void setTextAppearanceText(Context context, int styleId) {
        if (styleId > 0)
            this.tvText.setTextAppearance(context, styleId);
    }

    public void setTextAppearanceLabel(Context context, int styleId) {
        if (styleId > 0)
            this.tvLabel.setTextAppearance(context, styleId);
    }
}
