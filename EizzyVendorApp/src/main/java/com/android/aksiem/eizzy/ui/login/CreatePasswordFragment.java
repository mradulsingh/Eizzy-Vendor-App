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
import android.support.design.widget.TextInputLayout;
import android.telephony.PhoneNumberUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.CreatePasswordFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import java.util.regex.Pattern;

import javax.inject.Inject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreatePasswordFragment extends NavigationFragment {

    private static final String BUNDLE_PHONE_KEY = "bundlePhoneKey";
    private static final String BUNDLE_OTP_KEY = "bundleOTPKey";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    AppResourceManager appResourceManager;

    @Inject
    ToastController toastController;

    AutoClearedValue<CreatePasswordFragmentBinding> binding;

    private CreatePasswordViewModel createPasswordViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    public static CreatePasswordFragment newInstance(String phone, String otp) {

        Bundle args = new Bundle();
        args.putString(BUNDLE_PHONE_KEY, phone);
        args.putString(BUNDLE_OTP_KEY, otp);
        CreatePasswordFragment fragment = new CreatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_create_password)
                .toolbarSubtitleRes(R.string.screen_subtitle_create_password)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_reset_password)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        onResetPassword(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreatePasswordFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.create_password_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPasswordViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                CreatePasswordViewModel.class);
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState) {
        initData(savedInstanceState);
        //initInputListener();
    }

    private void initData(@Nullable Bundle savedInstanceState) {
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
        String phone = validatePhone(args.getString(BUNDLE_PHONE_KEY, null));
        if (phone != null) {
            createPasswordViewModel.setPhone(phone);
        }
        String otp = validateOtp(args.getString(BUNDLE_OTP_KEY, null));
        if (otp != null) {
            createPasswordViewModel.setOtp(otp);
        }
    }

    private void initInputListener() {
        binding.get().password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onResetPassword(v);
                return true;
            }
            return false;
        });
        binding.get().password.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                onResetPassword(v);
                return true;
            }
            return false;
        });
    }

    private void onResetPassword(View v) {
        String phone = createPasswordViewModel.getPhone();
        String otp = createPasswordViewModel.getOtp();
        String password = getVerifiedPassword();

        dismissKeyboard(v.getWindowToken());

        if (phone != null && otp != null && password != null) {
            createPasswordViewModel.setPassword(password);
            createPasswordViewModel.resetPassword().observe(this, resource -> {
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
                        navigationController.navigateToLogin();
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

    private String getVerifiedPassword() {
        String password = getValidatedPassword(binding.get().password,
                binding.get().textInputLayoutPassword);
        String confirmPassword = getValidatedPassword(binding.get().confirmPassword,
                binding.get().textInputLayoutConfirmPassword);

        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            binding.get().textInputLayoutConfirmPassword.setError(
                    getString(R.string.edittext_error_password_notconfirmed));
            return null;
        }
        return password;
    }

    private String validatePhone(String phone) {
        return  (phone != null && phone.length() > 0
                && PhoneNumberUtils.isGlobalPhoneNumber(phone)) ? phone : null;
    }

    private String validateOtp(String otp) {
        return (otp != null && otp.length() > 0 && otp.length() < 7) ? otp : null;
    }

    private String getValidatedPassword(EditText passwordET, TextInputLayout passwordTIL) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        String password = passwordET.getText().toString();
        if (password != null && pattern.matcher(password).matches()) {
            if (password.length() > 5) {
                return password;
            } else {
                passwordTIL.setError(getString(R.string.edittext_error_password_invalid));
            }
        } else {
            passwordTIL.setError(getString(R.string.validation_password_diversity_message));
        }
        return null;
    }
}
