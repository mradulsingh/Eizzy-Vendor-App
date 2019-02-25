package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.AppThemedCardLayoutBinding;

/**
 * Created by Mradul on 15/05/18.
 */

public class AppThemedCardLayout extends RelativeLayout {

    private static final int DEFAULT_COLOR_VALUE = Color.TRANSPARENT;

    private String atcvTitle;
    private String atcvSubtitle;
    private String additionalInfo;
    private String infoItem1Text;
    private String infoItem2Text;
    private String infoItem3Text;
    private String btnText;

    private int themeColor;

    private Drawable infoItem1DrawableLeftCompat;
    private Drawable infoItem2DrawableLeftCompat;
    private Drawable infoItem3DrawableLeftCompat;

    private AppThemedCardLayoutBinding binding;

    public AppThemedCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppThemedCardLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init(Context context, @Nullable AttributeSet attributeSet) {
        initAttrs(context, attributeSet);
        initView(context);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.AppThemedCardLayout);
            atcvTitle = typedArray.getString(R.styleable.AppThemedCardLayout_atcvTitle);
            atcvSubtitle = typedArray.getString(R.styleable.AppThemedCardLayout_atcvSubtitle);
            additionalInfo = typedArray.getString(R.styleable.AppThemedCardLayout_additionalInfo);
            infoItem1Text = typedArray.getString(R.styleable.AppThemedCardLayout_infoItem1Text);
            infoItem2Text = typedArray.getString(R.styleable.AppThemedCardLayout_infoItem2Text);
            infoItem3Text = typedArray.getString(R.styleable.AppThemedCardLayout_infoItem3Text);
            btnText = typedArray.getString(R.styleable.AppThemedCardLayout_btnText);

            this.themeColor = typedArray.getColor(R.styleable.AppThemedCardLayout_themeColor,
                    DEFAULT_COLOR_VALUE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                infoItem1DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardLayout_infoItem1DrawableLeftCompat);
                infoItem2DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardLayout_infoItem2DrawableLeftCompat);
                infoItem3DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardLayout_infoItem3DrawableLeftCompat);
            } else {
                final int infoItem1DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardLayout_infoItem1DrawableLeftCompat, -1);
                final int infoItem2DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardLayout_infoItem2DrawableLeftCompat, -1);
                final int infoItem3DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardLayout_infoItem3DrawableLeftCompat, -1);

                if (infoItem1DrawableResId != -1)
                    infoItem1DrawableLeftCompat = AppCompatResources.getDrawable(
                            context, infoItem1DrawableResId);
                if (infoItem2DrawableResId != -1)
                    infoItem2DrawableLeftCompat = AppCompatResources.getDrawable(
                            context, infoItem2DrawableResId);
                if (infoItem3DrawableResId != -1)
                    infoItem3DrawableLeftCompat = AppCompatResources.getDrawable(
                            context, infoItem3DrawableResId);
            }
            typedArray.recycle();
        }
    }

    private void initView(Context context) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.app_themed_card_layout, this, true);

        setAtcvTitle(atcvTitle);
        setAtcvSubtitle(atcvSubtitle);
        setAdditionalInfo(additionalInfo);
        setBtnText(btnText);
        setInfoItem1(infoItem1Text, infoItem1DrawableLeftCompat);
        setInfoItem2(infoItem2Text, infoItem2DrawableLeftCompat);
        setInfoItem3(infoItem3Text, infoItem3DrawableLeftCompat);
        setTheme(themeColor);

    }

    public void setTheme(int themeColor) {
        this.themeColor = themeColor;
        binding.themedIndicator.setBackgroundColor(this.themeColor);
        binding.btn.setBackgroundColor(this.themeColor);
    }

    public String getAtcvTitle() {
        return atcvTitle;
    }

    public void setAtcvTitle(String title) {
        this.atcvTitle = title == null ? "" : title;
        binding.title.setText(this.atcvTitle);
    }

    public String getAtcvSubtitle() {
        return atcvSubtitle;
    }

    public void setAtcvSubtitle(String subtitle) {
        this.atcvSubtitle = subtitle == null ? "" : subtitle;
        binding.subtitle.setText(this.atcvSubtitle);
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo == null ? "" : additionalInfo;
        binding.additionalInfo.setText(this.additionalInfo);
    }

    private void setInfoItem(String textValue, Drawable drawableLeftValue, AppTextView textView) {
        if (textValue != null && textValue.trim().length() > 0) {
            textView.setText(textValue);
            textView.setDrawableLeft(drawableLeftValue, true);
        } else {
            textView.setText("");
            textView.setDrawableLeft(null, true);
        }
    }

    public void setInfoItem1(String text, Drawable drawable) {
        this.infoItem1Text = text == null ? "" : text;
        this.infoItem1DrawableLeftCompat = drawable;
        setInfoItem(this.infoItem1Text, this.infoItem1DrawableLeftCompat, binding.infoItem1);
    }

    public void setInfoItem2(String text, Drawable drawable) {
        this.infoItem2Text = text == null ? "" : text;
        this.infoItem2DrawableLeftCompat = drawable;
        setInfoItem(this.infoItem2Text, this.infoItem2DrawableLeftCompat, binding.infoItem2);
    }

    public void setInfoItem3(String text, Drawable drawable) {
        this.infoItem3Text = text == null ? "" : text;
        this.infoItem3DrawableLeftCompat = drawable;
        setInfoItem(this.infoItem3Text, this.infoItem2DrawableLeftCompat, binding.infoItem3);
    }

    public String getInfoItem1Text() {
        return infoItem1Text;
    }

    public void setInfoItem1Text(String infoItem1Text) {
        this.infoItem1Text = infoItem1Text == null ? "" : infoItem1Text;
        setInfoItem1(infoItem1Text, infoItem1DrawableLeftCompat);
    }

    public String getInfoItem2Text() {
        return infoItem2Text;
    }

    public void setInfoItem2Text(String infoItem2Text) {
        this.infoItem2Text = infoItem2Text == null ? "" : infoItem2Text;
        setInfoItem2(infoItem2Text, infoItem2DrawableLeftCompat);
    }

    public String getInfoItem3Text() {
        return infoItem3Text;
    }

    public void setInfoItem3Text(String infoItem3Text) {
        this.infoItem3Text = infoItem3Text == null ? "" : infoItem3Text;
        setInfoItem3(infoItem3Text, infoItem3DrawableLeftCompat);
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText == null ? "" : btnText;
        binding.btn.setText(this.btnText);
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
        setTheme(this.themeColor);
    }

    public Drawable getInfoItem1DrawableLeftCompat() {
        return infoItem1DrawableLeftCompat;
    }

    public void setInfoItem1DrawableLeftCompat(Drawable infoItem1DrawableLeftCompat) {
        this.infoItem1DrawableLeftCompat = infoItem1DrawableLeftCompat;
        binding.infoItem1.setDrawableLeft(this.infoItem1DrawableLeftCompat, true);
    }

    public Drawable getInfoItem2DrawableLeftCompat() {
        return infoItem2DrawableLeftCompat;
    }

    public void setInfoItem2DrawableLeftCompat(Drawable infoItem2DrawableLeftCompat) {
        this.infoItem2DrawableLeftCompat = infoItem2DrawableLeftCompat;
        binding.infoItem2.setDrawableLeft(this.infoItem2DrawableLeftCompat, true);
    }

    public Drawable getInfoItem3DrawableLeftCompat() {
        return infoItem3DrawableLeftCompat;
    }

    public void setInfoItem3DrawableLeftCompat(Drawable infoItem3DrawableLeftCompat) {
        this.infoItem3DrawableLeftCompat = infoItem3DrawableLeftCompat;
        binding.infoItem3.setDrawableLeft(this.infoItem3DrawableLeftCompat, true);
    }
}
