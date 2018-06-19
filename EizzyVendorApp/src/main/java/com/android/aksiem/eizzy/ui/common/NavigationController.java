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
import com.android.aksiem.eizzy.ui.order.CreateOrderFragment;
import com.android.aksiem.eizzy.ui.order.OrderDetailsFragment;
import com.android.aksiem.eizzy.ui.order.OrderItemsFragment;
import com.android.aksiem.eizzy.ui.settlement.SettlementFragment;
import com.android.aksiem.eizzy.ui.user.UserDetailFragment;
import com.android.aksiem.eizzy.ui.login.VendorOnboardingFragment;
import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.vo.EizzyZone;
import com.android.aksiem.eizzy.vo.OrderDetailItem;

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
        addFragment(fragment, true, false);
    }

    public void navigateToOrderDetailsFragment(OrderDetailItem orderDetailItem) {
        OrderDetailsFragment fragment = OrderDetailsFragment.create(orderDetailItem);
        String tag = OrderDetailsFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToSettlementFragment() {
        SettlementFragment fragment = SettlementFragment.newInstance();
        String tag = SettlementFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToLogin() {
        LoginFragment loginFragment = LoginFragment.newInstance();
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
                .add(containerId, fragment, tag)
                .addToBackStack(tag)
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

    public void navigateToValidateOTPFragment(String phone) {
        ValidateOTPFragment fragment = ValidateOTPFragment.newInstance(phone);
        String tag = ValidateOTPFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToCreatePasswordFragment(String phone, String otp) {
        CreatePasswordFragment fragment = CreatePasswordFragment.newInstance(phone, otp);
        String tag = CreatePasswordFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commitAllowingStateLoss();
    }

    public void navigateToVendorOnboardingFragment() {
        VendorOnboardingFragment fragment = VendorOnboardingFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToUserDetailFragment() {
        UserDetailFragment fragment = new UserDetailFragment();
        String tag = UserDetailFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToCreateOrderFragment(ArrayList<EizzyZone> eizzyZones) {
        CreateOrderFragment fragment = CreateOrderFragment.newInstance(eizzyZones);
        String tag = CreateOrderFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void openOrderSortFilterDialogFragment() {
        SortFilterDialogFragment fragment = new SortFilterDialogFragment();
        fragment.show(fragmentManager, "OrderSortFilterDialog");
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
