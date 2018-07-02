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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.EizzyActivity;
import com.android.aksiem.eizzy.ui.login.CreatePasswordFragment;
import com.android.aksiem.eizzy.ui.login.CreateUserAccountFragment;
import com.android.aksiem.eizzy.ui.login.ForgotPasswordFragment;
import com.android.aksiem.eizzy.ui.login.LoginFragment;
import com.android.aksiem.eizzy.ui.login.ValidateOTPFragment;
import com.android.aksiem.eizzy.ui.login.VendorOnboardingFragment;
import com.android.aksiem.eizzy.ui.order.CreateOrderFragment;
import com.android.aksiem.eizzy.ui.order.OrderDetailsFragment;
import com.android.aksiem.eizzy.ui.order.OrderItemsFragment;
import com.android.aksiem.eizzy.ui.settlement.SettlementFragment;
import com.android.aksiem.eizzy.ui.user.StoreManagerDetailFragment;
import com.android.aksiem.eizzy.ui.webviews.WebviewFragment;
import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.vo.order.EizzyZone;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link EizzyActivity}.
 */
public class NavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;
    private final EizzyActivity mActivity;

    @Inject
    public NavigationController(EizzyActivity activity) {
        this.containerId = R.id.container;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.mActivity = activity;
    }

    public void navigateToOrderItemsFragment() {
        OrderItemsFragment fragment = OrderItemsFragment.create();
        addFragment(fragment, false, true);
    }

    public void navigateToOrderDetailsFragment(String orderId) {
        OrderDetailsFragment fragment = OrderDetailsFragment.create(orderId);
        addFragment(fragment, true);
    }

    public void navigateToSettlementFragment() {
        SettlementFragment fragment = SettlementFragment.newInstance();
        addFragment(fragment, false, true);
    }

    public void navigateToLogin() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        addFragment(loginFragment, true);
    }

    public void navigateToCreateUserAccount() {
        CreateUserAccountFragment fragment = new CreateUserAccountFragment();
        addFragment(fragment, true);
    }

    public void navigateToForgotPasswordFragment() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        addFragment(fragment, true);
    }

    public void navigateToValidateOTPFragment(String phone) {
        ValidateOTPFragment fragment = ValidateOTPFragment.newInstance(phone);
        addFragment(fragment, true);
    }

    public void navigateToCreatePasswordFragment(String phone, String otp) {
        CreatePasswordFragment fragment = CreatePasswordFragment.newInstance(phone, otp);
        addFragment(fragment, false, true);
    }

    public void navigateToVendorOnboardingFragment() {
        VendorOnboardingFragment fragment = VendorOnboardingFragment.newInstance();
        addFragment(fragment, false, true);
    }

    public void navigateToStoreManagerFragment() {
        StoreManagerDetailFragment fragment = new StoreManagerDetailFragment();
        addFragment(fragment, true);
    }

    public void navigateToCreateOrderFragment(ArrayList<EizzyZone> eizzyZones) {
        CreateOrderFragment fragment = CreateOrderFragment.newInstance(eizzyZones);
        addFragment(fragment, true);
    }

    public void openOrderSortFilterDialogFragment() {
        SortFilterDialogFragment fragment = new SortFilterDialogFragment();
        fragment.show(fragmentManager, "OrderSortFilterDialog");
    }

    public void navigateToWebViewFragment(String url, String screenTitle) {
        WebviewFragment fragment = WebviewFragment.newInstance(url, screenTitle);
        addFragment(fragment, true);
    }


    public void navigateToConfirmationFragment(Boolean backButtonExists, String title,
                                               String subTitle, String actionButtonText,
                                               ClickActionHandler listener, boolean replace,
                                               boolean addToBackStack) {
        ConfirmationFragment fragment = ConfirmationFragment.newInstance(backButtonExists, title,
                subTitle, actionButtonText, listener);
        String tag = ConfirmationFragment.class.getSimpleName();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (replace)
            transaction.replace(containerId, fragment, tag);
        else
            transaction.add(containerId, fragment, tag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void addFragment(Fragment fragment, boolean addStack) {
        addFragment(containerId, fragment, addStack, false, 0, 0);
    }

    public void addFragment(Fragment fragment, boolean addStack,
                            int inAnimation, int outAnimation) {
        addFragment(containerId, fragment, addStack, false, inAnimation, outAnimation);
    }

    public void addFragment(Fragment fragment, boolean addStack, boolean isReplace) {
        addFragment(containerId, fragment, addStack, isReplace, 0, 0);
    }

    public void addFragment(Fragment fragment, boolean addStack, boolean isReplace,
                            int inAnimation, int outAnimation) {
        addFragment(containerId, fragment, addStack, isReplace, inAnimation, outAnimation);
    }

    public void addFragment(int containerViewId, Fragment fragment,
                            boolean addToBackStack, boolean isReplace,
                            int inAnimation, int outAnimation) {
        if (mActivity.isFinishing()) {
            Logger.e("addFragment :: Fragment is null.");
            return;
        }

        String tag = fragment.getClass().toString();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isReplace) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
            fragmentManager.executePendingTransactions();
        }

        if (inAnimation > 0 && outAnimation <= 0) {
            fragmentTransaction.setCustomAnimations(inAnimation, outAnimation);
        } else if (inAnimation > 0 && outAnimation > 0) {
            fragmentTransaction.setCustomAnimations(inAnimation, inAnimation, outAnimation,
                    outAnimation);
        }

        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            if (!isReplace) {
                fragmentTransaction.add(containerViewId, fragment, tag);
            } else {
                fragmentTransaction.replace(containerViewId, fragment, tag);
            }
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        } else {
            fragmentManager.popBackStack(tag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }
}
