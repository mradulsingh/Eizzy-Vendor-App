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
import com.android.aksiem.eizzy.databinding.CreateUserAccountFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by napendersingh on 16/04/18.
 */

public class CreateUserAccountFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    ToastController toastController;

    AutoClearedValue<CreateUserAccountFragmentBinding> binding;

    private CreateUserAccountViewModel createUserAccountViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_create_account)
                .toolbarSubtitleRes(R.string.screen_subtitle_create_account)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_create_account)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        //doUserLogin(view);
        toastController.showErrorToast("Account Creation Failed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreateUserAccountFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.create_user_account_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createUserAccountViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateUserAccountViewModel.class);
        initInputListener();
    }

    private void initInputListener() {
        binding.get().contactEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                createUserAccount(v);
                return true;
            }
            return false;
        });
        binding.get().contactEmail.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                createUserAccount(v);
                return true;
            }
            return false;
        });
    }

    private void createUserAccount(View v) {
        String businessName = binding.get().businessName.getText().toString();
        String contactPerson = binding.get().contactPerson.getText().toString();
        String contactMobile = binding.get().contactMobile.getText().toString();
        String contactEmail = binding.get().contactEmail.getText().toString();

        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());
        createUserAccountViewModel.setBusinessName(businessName);
        createUserAccountViewModel.setContactPerson(contactPerson);
        createUserAccountViewModel.setContactMobile(contactMobile);
        createUserAccountViewModel.setContactEmail(contactEmail);
        createUserAccountViewModel.createUserAccount();
    }
}
