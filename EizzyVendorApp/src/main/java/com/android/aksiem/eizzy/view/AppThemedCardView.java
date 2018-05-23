package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.aksiem.eizzy.R;

/**
 * Created by pdubey on 15/05/18.
 */

public class AppThemedCardView extends CardView {

    private CardView rootView;
    private TextView atcvTitleView;
    private TextView atcvSubtitleView;
    private TextView additionalInfoView;
    private AppTextView infoItemText1;
    private AppTextView infoItemText2;
    private AppTextView infoItemText3;
    private AppTextView btnTextView;
    private View themeIndicatorView;

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

    public AppThemedCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppThemedCardView(@NonNull Context context, @Nullable AttributeSet attrs,
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
                    R.styleable.AppThemedCardView);
            atcvTitle = typedArray.getString(R.styleable.AppThemedCardView_atcvTitle);
            atcvSubtitle =  typedArray.getString(R.styleable.AppThemedCardView_atcvSubtitle);
            additionalInfo = typedArray.getString(R.styleable.AppThemedCardView_additionalInfo);
            infoItem1Text = typedArray.getString(R.styleable.AppThemedCardView_infoItem1Text);
            infoItem2Text = typedArray.getString(R.styleable.AppThemedCardView_infoItem2Text);
            infoItem3Text = typedArray.getString(R.styleable.AppThemedCardView_infoItem3Text);
            btnText = typedArray.getString(R.styleable.AppThemedCardView_btnText);

            this.themeColor = typedArray.getColor(R.styleable.AppThemedCardView_themeColor,
                    Color.TRANSPARENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                infoItem1DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardView_infoItem1DrawableLeftCompat);
                infoItem2DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardView_infoItem2DrawableLeftCompat);
                infoItem3DrawableLeftCompat = typedArray.getDrawable(
                        R.styleable.AppThemedCardView_infoItem3DrawableLeftCompat);
            } else {
                final int infoItem1DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardView_infoItem1DrawableLeftCompat, -1);
                final int infoItem2DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardView_infoItem2DrawableLeftCompat, -1);
                final int infoItem3DrawableResId = typedArray.getResourceId(
                        R.styleable.AppThemedCardView_infoItem3DrawableLeftCompat, -1);

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
        rootView = (CardView) LayoutInflater.from(context).inflate(
                R.layout.app_themed_card_view, this, true);
        atcvTitleView = rootView.findViewById(R.id.title);
        atcvSubtitleView = rootView.findViewById(R.id.subtitle);
        additionalInfoView = rootView.findViewById(R.id.additionalInfo);
        btnTextView = rootView.findViewById(R.id.btn);
        infoItemText1 = rootView.findViewById(R.id.infoItem1);
        infoItemText2 = rootView.findViewById(R.id.infoItem2);
        infoItemText3 = rootView.findViewById(R.id.infoItem3);
        themeIndicatorView = rootView.findViewById(R.id.themedIndicator);

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
        this.themeColor = themeColor < 0 ? Color.TRANSPARENT : themeColor;
        themeIndicatorView.setBackgroundColor(this.themeColor);
        btnTextView.setBackgroundColor(this.themeColor);
    }

    public String getAtcvTitle() {
        return atcvTitle;
    }

    public void setAtcvTitle(String title) {
        this.atcvTitle = title == null ? "" : title;
        atcvTitleView.setText(this.atcvTitle);
    }

    public String getAtcvSubtitle() {
        return atcvSubtitle;
    }

    public void setAtcvSubtitle(String subtitle) {
        this.atcvSubtitle = subtitle == null ? "" : subtitle;
        atcvSubtitleView.setText(this.atcvSubtitle);
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo == null ? "" : additionalInfo;
        additionalInfoView.setText(this.additionalInfo);
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
        setInfoItem(this.infoItem1Text, this.infoItem1DrawableLeftCompat, infoItemText1);
    }

    public void setInfoItem2(String text, Drawable drawable) {
        this.infoItem2Text = text == null ? "" : text;
        this.infoItem2DrawableLeftCompat = drawable;
        setInfoItem(this.infoItem2Text, this.infoItem2DrawableLeftCompat, infoItemText2);
    }

    public void setInfoItem3(String text, Drawable drawable) {
        this.infoItem3Text = text == null ? "" : text;
        this.infoItem3DrawableLeftCompat = drawable;
        setInfoItem(this.infoItem3Text, this.infoItem2DrawableLeftCompat, infoItemText3);
    }

    public String getInfoItem1Text() {
        return infoItem1Text;
    }

    public void setInfoItem1Text(String infoItem1Text) {
        this.infoItem1Text = infoItem1Text == null ? "" : infoItem1Text;
    }

    public String getInfoItem2Text() {
        return infoItem2Text;
    }

    public void setInfoItem2Text(String infoItem2Text) {
        this.infoItem2Text = infoItem2Text == null ? "" : infoItem2Text;
    }

    public String getInfoItem3Text() {
        return infoItem3Text;
    }

    public void setInfoItem3Text(String infoItem3Text) {
        this.infoItem3Text = infoItem3Text == null ? "" : infoItem3Text;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText == null ? "" : btnText;
        btnTextView.setText(this.btnText);
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor < 0 ? 0 : themeColor;
        setTheme(this.themeColor);
    }

    public Drawable getInfoItem1DrawableLeftCompat() {
        return infoItem1DrawableLeftCompat;
    }


    public Drawable getInfoItem2DrawableLeftCompat() {
        return infoItem2DrawableLeftCompat;
    }

    public Drawable getInfoItem3DrawableLeftCompat() {
        return infoItem3DrawableLeftCompat;
    }

}
