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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.LoginFragmentBinding;
import com.android.aksiem.eizzy.ui.common.ClickActionHandler;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class LoginFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    AutoClearedValue<LoginFragmentBinding> binding;

    private LoginViewModel loginViewModel;

    @Inject
    ToastController toastController;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

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
        //doUserLogin(view);
        toastController.showErrorToast("Login Failed");
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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        initInputListener();
        binding.get().tvActionForgetPassword.setOnClickListener(v -> navigationController.navigateToForgotPasswordFragment());
    }

    private void initInputListener() {
        binding.get().password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doUserLogin(v);
                return true;
            }
            return false;
        });
        binding.get().password.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                doUserLogin(v);
                return true;
            }
            return false;
        });
    }

    private void doUserLogin(View v) {
        String phone = binding.get().userid.getText().toString();
        String password = binding.get().password.getText().toString();
        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());
        loginViewModel.setPhone(phone);
        loginViewModel.setPassword(password);
        loginViewModel.doUserLogin();
    }
}
