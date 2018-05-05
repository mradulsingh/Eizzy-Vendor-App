package com.android.aksiem.eizzy.ui.common;

import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.databinding.AppToastBinding;
import com.android.aksiem.eizzy.ui.toast.ToastViewModel;

import javax.inject.Inject;

public class ToastController {

    private EizzyActivity mActivity;

    @Inject
    AppResourceManager resourceManager;

    @Inject
    public ToastController(EizzyActivity activity) {
        this.mActivity = activity;
    }

    public void showErrorToast(String message) {
        ToastViewModel viewModel = getToastViewModel();
        viewModel.setBgToastResId(R.color.fireEngineRed);
        viewModel.setMessage(message);
        showToast(viewModel);
    }

    public void showSuccessToast(String message) {
        ToastViewModel viewModel = getToastViewModel();
        viewModel.setBgToastResId(R.color.indianYellow);
        viewModel.setMessage(message);
        showToast(viewModel);
    }

    private ToastViewModel getToastViewModel() {
        return new ToastViewModel(resourceManager);
    }

    private void showToast(ToastViewModel viewModel) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        AppToastBinding dataBinding = DataBindingUtil.bind(inflater.inflate(R.layout.app_toast, null));
        Toast toast = new Toast(mActivity);
        toast.setView(dataBinding.getRoot());
        dataBinding.setViewModel(viewModel);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
                0, (int) resourceManager.dp(100));
        toast.setDuration(viewModel.getDuration());
        toast.show();
    }
}
