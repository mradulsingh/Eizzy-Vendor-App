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
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.LoginFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mradul on 10/06/18.
 */

public class LoginFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    AppPrefManager appPrefManager;

    AutoClearedValue<LoginFragmentBinding> binding;

    private LoginViewModel loginViewModel;

    @Inject
    ToastController toastController;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_login)
                .toolbarSubtitleRes(R.string.screen_subtitle_login)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_login)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        doManagerLogin(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LoginFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.login_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                LoginViewModel.class);
        //initInputListener();
        binding.get().tvActionForgetPassword.setOnClickListener(v -> {
            dismissKeyboard(v.getWindowToken());
            navigationController.navigateToForgotPasswordFragment();
        });
    }

    private void initInputListener() {
        binding.get().password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doManagerLogin(v);
                return true;
            }
            return false;
        });
        binding.get().password.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                doManagerLogin(v);
                return true;
            }
            return false;
        });
    }

    private void doManagerLogin(View v) {
        String phone = getValidatedPhone();
        String password = getValidatedPassword();
        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());

        if (phone != null && password != null) {
            loginViewModel.doManagerLogin(phone, password).observe(this, managerResource -> {
                switch (managerResource.status) {
                    case LOADING:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.startAnimation();
                        }
                        break;
                    case SUCCESS:
                        EizzyAppState.ManagerLoggedIn.setManagerLoggedIn(appPrefManager,
                                true);
                        EizzyAppState.ManagerLoggedIn.setManagerDetails(appPrefManager,
                                managerResource.data.data);
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        ((EizzyActivity) getActivity()).initNavigationDrawerInfo();
                        navigationController.navigateToOrderItemsFragment();
                        break;
                    case ERROR:
                    default:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        toastController.showErrorToast(managerResource.message);
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

    private String getValidatedPassword() {
        //final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        String password = binding.get().password.getText().toString();
        if (password != null && pattern.matcher(password).matches()) {
            return password;
        }
        binding.get().textInputLayoutPassword.setError(
                getString(R.string.validation_password_message));
        return null;
    }
}
