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
import com.android.aksiem.eizzy.databinding.ValidateOtpFragmentBinding;
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

public class ValidateOTPFragment extends NavigationFragment {

    private static final String PHONE_BUNDLE_KEY = "phoneBundleKey";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ToastController toastController;

    @Inject
    NavigationController navigationController;

    AutoClearedValue<ValidateOtpFragmentBinding> binding;

    private ValidateOTPViewModel validateOTPViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);


    public static ValidateOTPFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE_BUNDLE_KEY, phone);
        ValidateOTPFragment fragment = new ValidateOTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_validate_otp)
                .toolbarSubtitleRes(R.string.screen_subtitle_validate_otp)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_validate_otp)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        onValidateOTP(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ValidateOtpFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.validate_otp_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validateOTPViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                ValidateOTPViewModel.class);
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState) {
        initData(savedInstanceState);
        initInputListener();
    }

    private void initData(@Nullable Bundle savedInstanceState) {
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
        String phone = validatePhone(args.getString(PHONE_BUNDLE_KEY, null));
        if (phone !=  null) {
            validateOTPViewModel.setPhone(phone);
        }
    }

    private void initInputListener() {
        binding.get().userid.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onValidateOTP(v);
                return true;
            }
            return false;
        });
        binding.get().userid.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                onValidateOTP(v);
                return true;
            }
            return false;
        });
    }

    private void onValidateOTP(View v) {

        String phone = getValidatedPhone();
        String otp = getValidatedOtp();

        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());

        if (phone != null && otp != null) {
            validateOTPViewModel.setOTP(otp);
            validateOTPViewModel.setPhone(phone);
            validateOTPViewModel.onValidateOTP().observe(this, resource -> {
                switch (resource.status) {
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
                        navigationController.navigateToCreatePasswordFragment(phone, otp);
                        break;
                    case ERROR:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        toastController.showErrorToast(resource.message);
                        break;
                }
            });
        }
    }

    private String getValidatedOtp() {
        String otp = binding.get().userid.getText().toString();
        if (otp != null && otp.length() > 0 && otp.length() < 7) {
            return otp;
        }
        binding.get().textInputLayoutUserId.setError(getString(R.string.validation_otp_message));
        return null;
    }

    private String getValidatedPhone() {
        String phone = validateOTPViewModel.getPhone();
        if (phone == null) {
            toastController.showErrorToast(getString(R.string.validation_phone_otp_message));
        }
        return phone;
    }

    private String validatePhone(String phone) {
        return  (phone != null && phone.length() > 0
                && PhoneNumberUtils.isGlobalPhoneNumber(phone)) ? phone : null;
    }
}
