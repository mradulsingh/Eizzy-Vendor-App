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
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.CreateUserAccountFragmentBinding;
import com.android.aksiem.eizzy.ui.common.ClickActionHandler;
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
 * Created by napendersingh on 16/04/18.
 */

public class CreateUserAccountFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    @Inject
    ToastController toastController;

    @Inject
    AppPrefManager appPrefManager;

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
        createUserAccount(view);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreateUserAccountFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.create_user_account_fragment, container,
                        false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createUserAccountViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                CreateUserAccountViewModel.class);
        //initInputListener();
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
        String businessName = getValidatedBusinessName();
        String contactPerson = getValidatedContactPerson();
        String contactMobile = getValidatedContactMobile();
        String contactEmail = getValidatedContactEmail();

        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());

        if (businessName != null && contactPerson != null && contactMobile != null
                && contactEmail != null) {
            createUserAccountViewModel.setBusinessName(businessName);
            createUserAccountViewModel.setContactPerson(contactPerson);
            createUserAccountViewModel.setContactMobile(contactMobile);
            createUserAccountViewModel.setContactEmail(contactEmail);
            createUserAccountViewModel.createUserAccount().observe(this, storeResource -> {
                switch (storeResource.status) {
                    case LOADING:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.startAnimation();
                        }
                        break;
                    case SUCCESS:
                        EizzyAppState.AccountCreated.setAccountCreated(appPrefManager,
                                true);
                        EizzyAppState.AccountCreated.setBasicAccountDetails(appPrefManager,
                                storeResource.data.data);
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        navigationController.navigateToConfirmationFragment(true,
                                getString(R.string.confirmation_account_creation_title),
                                getString(R.string.confirmation_account_creation_subtitle),
                                getString(R.string.confirmation_account_creation_action),
                                (v1, args) -> {
                                    navigationController.navigateToVendorOnboardingFragment();
                                }, true, false);
                        break;
                    case ERROR:
                    default:
                        if (v instanceof CircularProgressButton) {
                            CircularProgressButton button = (CircularProgressButton) v;
                            button.revertAnimation();
                        }
                        toastController.showErrorToast(storeResource.message);
                }
            });
        }

    }

    private String getValidatedBusinessName() {
        String businessName = binding.get().businessName.getText().toString();
        if (businessName != null && businessName.length() > 0) {
            return businessName;
        } else {
            binding.get().textInputLayoutBusinessName.setError(getString(
                    R.string.validation_business_name_message));
            return null;
        }
    }

    private String getValidatedContactPerson() {
        String contactPerson = binding.get().contactPerson.getText().toString();
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        if (contactPerson != null && contactPerson.length() > 0
                && pattern.matcher(contactPerson).matches()) {
            return contactPerson;
        } else {
            binding.get().textInputLayoutContactPerson.setError(getString(
                    R.string.validation_user_name_message));
            return null;
        }
    }

    private String getValidatedContactMobile() {
        String contactMobile = binding.get().contactMobile.getText().toString();
        if (contactMobile != null && contactMobile.length() > 0 &&
                PhoneNumberUtils.isGlobalPhoneNumber(contactMobile)) {
            return contactMobile;
        } else {
            binding.get().textInputLayoutContactMobile.setError(getString(
                    R.string.validation_phone_message));
            return null;
        }
    }

    private String getValidatedContactEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        String contactEmail = binding.get().contactEmail.getText().toString();
        if (contactEmail != null && contactEmail.length() > 0 &&
                pattern.matcher(contactEmail).matches()) {
            return contactEmail;
        } else {
            binding.get().textInputLayoutContactEmail.setError(getString(
                    R.string.validation_email_message));
            return null;
        }
    }
}
