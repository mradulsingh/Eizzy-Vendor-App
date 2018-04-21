package com.android.aksiem.eizzy.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.AppInfoDialogBinding;

/**
 * @author Napender Singh
 */
public class AppInfoDialog extends Dialog implements View.OnClickListener {

    private static final double FACTOR_DIALOG_WIDTH = 0.80;

    private Context mContext;
    private ButtonClickListener mPositiveBtnListener = null;
    private ButtonClickListener mNegativeBtnListener = null;
    private boolean shouldDismissOnButtonClick = false;
    private boolean isCancelable = false;
    private CountDownTimer mCountDownTimer;
    private TimerListener mTimerListener;
    private boolean shouldDismissOnTimerFinish = false;
    private AppInfoDialogBinding binding;


    public AppInfoDialog setCancelableFlag(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    public AppInfoDialog setDismissOnButtonClick(boolean dismissOnButtonClick) {
        this.shouldDismissOnButtonClick = dismissOnButtonClick;
        return this;
    }

    public void setHtmlMessage(String htmlMsgText) {
        binding.tvInfoMessage.setText(Html.fromHtml(htmlMsgText), TextView.BufferType.SPANNABLE);
    }

    public AppInfoDialog setMessage(String messageTxt) {
        binding.setInfoMessage(messageTxt);
        return this;
    }

    public void setMessage(SpannableString msgTxt) {
        binding.tvInfoMessage.setText(msgTxt);
    }

    public AppInfoDialog setTitle(String headerTxt) {
        binding.setDialogTitle(headerTxt);
        return this;
    }

    public AppInfoDialog setCountDownTimer(long millisInFuture, long countDownInterval, TimerListener listener) {
        mCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mTimerListener.onFinish(mContext);
                mCountDownTimer = null;
                if (shouldDismissOnTimerFinish) {
                    dismiss();
                }
            }
        };
        mTimerListener = listener;
        return this;
    }

    public AppInfoDialog setDismissOnTimerFinish(boolean shouldDismissOnTimerFinish) {
        this.shouldDismissOnTimerFinish = shouldDismissOnTimerFinish;
        return this;
    }

    public AppInfoDialog(Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.app_info_dialog, null, false);
        setContentView(binding.getRoot());
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * FACTOR_DIALOG_WIDTH);
        Window window = getWindow();
        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void create() {
        setViewData();
        super.create();
    }

    @Override
    public void show() {
        super.show();
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    @Override
    public void dismiss() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;

            if (mTimerListener != null) {
                mTimerListener.onFinish(mContext);
                mTimerListener = null;
            }
        }
        super.dismiss();
    }

    private void setViewData() {
        setCanceledOnTouchOutside(isCancelable);
        setCancelable(isCancelable);
    }

    private String convertToHtml(String htmlString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<![CDATA[");
        stringBuilder.append(htmlString);
        stringBuilder.append("]]>");
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.tv_positive:
                if (mPositiveBtnListener != null) {
                    mPositiveBtnListener.onBtnClick(v);
                    if (shouldDismissOnButtonClick) {
                        dismiss();
                    }
                } else {
                    dismiss();
                }
                break;
            case R.id.tv_negative:
                if (mNegativeBtnListener != null) {
                    mNegativeBtnListener.onBtnClick(v);
                    if (shouldDismissOnButtonClick) {
                        dismiss();
                    }
                } else {
                    dismiss();
                }
                break;*/
        }
    }

    public AppInfoDialog setPositiveButton(String text, @Nullable ButtonClickListener listener) {
        binding.setPositiveBtnText(text);
        mPositiveBtnListener = listener;
        return this;
    }

    public AppInfoDialog setPositiveButton(@Nullable ButtonClickListener listener) {
        mPositiveBtnListener = listener;
        return this;
    }


    public AppInfoDialog setNegativeButton(String text, @Nullable ButtonClickListener listener) {
        binding.setNegativeBtnText(text);
        mNegativeBtnListener = listener;
        return this;
    }

    public AppInfoDialog setNegativeButton(@Nullable ButtonClickListener listener) {
        mNegativeBtnListener = listener;
        return this;
    }

    public interface ButtonClickListener {
        void onBtnClick(View v);
    }

    public interface TimerListener {
        void onFinish(Context context);
    }
}
