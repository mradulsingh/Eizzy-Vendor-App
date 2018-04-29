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

package com.android.aksiem.eizzy.ui.common;

import android.support.v4.app.FragmentManager;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.ui.login.CreatePasswordFragment;
import com.android.aksiem.eizzy.ui.login.CreateUserAccountFragment;
import com.android.aksiem.eizzy.ui.login.ForgotPasswordFragment;
import com.android.aksiem.eizzy.ui.login.LoginFragment;
import com.android.aksiem.eizzy.ui.login.ValidateOTPFragment;
import com.android.aksiem.eizzy.ui.order.OrderItemsFragment;
import com.android.aksiem.eizzy.ui.settlement.SettlementFragment;
import com.android.aksiem.eizzy.ui.vendorOnboarding.VendorOnboardingFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link EizzyActivity}.
 */
public class NavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(EizzyActivity activity) {
        this.containerId = R.id.container;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public void navigateToOrderItemsFragment() {
        OrderItemsFragment fragment = OrderItemsFragment.create();
        String tag = OrderItemsFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToSettlementFragment() {
        SettlementFragment fragment = SettlementFragment.create();
        String tag = SettlementFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        String tag = LoginFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToCreateUserAccount() {
        CreateUserAccountFragment fragment = new CreateUserAccountFragment();
        String tag = CreateUserAccountFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToForgotPasswordFragment() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        String tag = ForgotPasswordFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToValidateOTPFragment() {
        ValidateOTPFragment fragment = new ValidateOTPFragment();
        String tag = ValidateOTPFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToCreatePasswordFragment() {
        CreatePasswordFragment fragment = new CreatePasswordFragment();
        String tag = CreatePasswordFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commitAllowingStateLoss();
    }

    public void navigateToVendorOnboardingFragment() {
        VendorOnboardingFragment fragment = VendorOnboardingFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void openOrderSortFilterDialogFragment() {
        SortFilterDialogFragment fragment = new SortFilterDialogFragment();
        fragment.show(fragmentManager, "OrderSortFilterDialog");
    }


}
