package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.aksiem.eizzy.R;

/**
 * Created by pdubey on 03/05/18.
 */

public class AppSteppedProgressView extends View {

    private static final int[] mStates = {
            R.styleable.ProgressStates_pending,
            R.styleable.ProgressStates_inProgress,
            R.styleable.ProgressStates_complete
    };

    // Default to pending
    private int mStateIndex = 0;

    public AppSteppedProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public AppSteppedProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributesArray = context.obtainStyledAttributes(attrs,
                    R.styleable.AppSteppedProgress);
            mStateIndex = attributesArray.getInt(R.styleable.AppSteppedProgress_progressState,
                    -1);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState;
        if (mStateIndex < 0) {
            drawableState = super.onCreateDrawableState(extraSpace);
        } else {
            int[] newState = { getState() };
            drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, newState);
        }
        return drawableState;
    }

    public int getState() {
        return mStates[mStateIndex];
    }

    public void setPending() {
        setState(0);
    }

    public void setInProgress() {
        setState(1);
    }

    public void setComplete() {
        setState(2);
    }

    private void setState(int stateIndex) {
        mStateIndex = stateIndex;
        refreshDrawableState();
    }
}
