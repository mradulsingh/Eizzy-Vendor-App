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
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.CreatePasswordFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreatePasswordFragment extends NavigationFragment {

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
        createPasswordViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreatePasswordViewModel.class);
        initInputListener();
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
        String password = binding.get().password.getText().toString();

        // Dismiss keyboard
        if (validatePassword()) {
            dismissKeyboard(v.getWindowToken());
            createPasswordViewModel.setPassword(password);
            createPasswordViewModel.resetPassword();
        }
    }

    private boolean validatePassword() {
        String password = binding.get().password.getText().toString();
        String confirmPassword = binding.get().confirmPassword.getText().toString();

        binding.get().textInputLayoutPassword.setError(null);
        binding.get().textInputLayoutConfirmPassword.setError(null);

        if (password.length() < 6) {
            binding.get().textInputLayoutPassword.setError(appResourceManager.getString(R.string.edittext_error_password_invalid));
            return false;
        }

        if (!password.equals(confirmPassword)) {
            binding.get().textInputLayoutConfirmPassword.setError(appResourceManager.getString(R.string.edittext_error_password_notconfirmed));
            return false;
        }
        return true;
    }
}
