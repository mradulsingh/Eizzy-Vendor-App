package com.android.aksiem.eizzy.app;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.aksiem.eizzy.di.Injectable;
import com.android.aksiem.eizzy.util.AppUtils;

public abstract class BaseInjectableFragment extends Fragment implements Injectable {

    protected void showInfoMessage(View view, String message) {
        if (isVisible()) {
            AppUtils.showInfoMessage(view, message);
        }
    }

    protected void showToast(String message) {
        if (isVisible()) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    protected void showInfoMessage(View view, int stringResId) {
        String message = getString(stringResId);
        showInfoMessage(view, message);
    }

    protected void dismissKeyboard(IBinder windowToken) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    protected void setBottomNavigationViewVisibility(boolean visible) {
        FragmentActivity activity = getActivity();
        if (activity instanceof EizzyActivity) {
            ((EizzyActivity) activity).setBottomNavigationViewVisibility(visible);
        }
    }

    protected void setNavDrawerMode(boolean unLocked) {
        FragmentActivity activity = getActivity();
        if (activity instanceof EizzyActivity) {
            ((EizzyActivity) activity).setDrawerLockMode(unLocked);
        }
    }

    protected void onBackPressed() {
        FragmentActivity activity = getActivity();
        if (activity instanceof EizzyActivity) {
            ((EizzyActivity) activity).onBackPressed();
        }
    }

    @CallSuper
    public void onFragmentResume() {
    }

    @CallSuper
    public void onFragmentPause() {
    }
}

