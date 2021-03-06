/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.aksiem.eizzy.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.ForgotPasswordFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mradul on 10/06/18.
 */

public class ForgotPasswordFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    ToastController toastController;

    AutoClearedValue<ForgotPasswordFragmentBinding> binding;

    private ForgotPasswordViewModel forgotPasswordViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_forgot_password)
                .toolbarSubtitleRes(R.string.screen_subtitle_forgot_password)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_get_otp)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View v) {
        onForgotPassword(v);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ForgotPasswordFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.forgot_password_fragment, container, false,
                dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        forgotPasswordViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ForgotPasswordViewModel.class);
        initInputListener();
    }

    private void initInputListener() {
        binding.get().userid.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onForgotPassword(v);
                return true;
            }
            return false;
        });
        binding.get().userid.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                onForgotPassword(v);
                return true;
            }
            return false;
        });
    }

    private void onForgotPassword(View v) {
        String phone = getValidatedPhone();

        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());

        if (phone != null) {
            forgotPasswordViewModel.setPhone(phone);
            forgotPasswordViewModel.onForgotPassword().observe(this, respone -> {
                switch (respone.status) {
                    case LOADING:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.startAnimation();
                        }
                        break;
                    case SUCCESS:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        navigationController.navigateToValidateOTPFragment(phone);
                        break;
                    case ERROR:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        toastController.showErrorToast(respone.message);
                        break;
                }
            });
        }
    }

    private String getValidatedPhone() {
        String phone = binding.get().userid.getText().toString();
        if (phone != null && phone.length() > 0 && PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
            return phone;
        }
        binding.get().textInputLayoutUserId.setError(getString(R.string.validation_phone_message));
        return null;
    }
}
