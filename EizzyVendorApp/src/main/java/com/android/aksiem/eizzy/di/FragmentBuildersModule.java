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

package com.android.aksiem.eizzy.di;

import com.android.aksiem.eizzy.ui.common.ConfirmationFragment;
import com.android.aksiem.eizzy.ui.common.SortFilterDialogFragment;
import com.android.aksiem.eizzy.ui.login.CreatePasswordFragment;
import com.android.aksiem.eizzy.ui.login.CreateUserAccountFragment;
import com.android.aksiem.eizzy.ui.login.ForgotPasswordFragment;
import com.android.aksiem.eizzy.ui.login.LoginFragment;
import com.android.aksiem.eizzy.ui.login.ValidateOTPFragment;
import com.android.aksiem.eizzy.ui.order.CreateOrderFragment;
import com.android.aksiem.eizzy.ui.order.OrderDetailsFragment;
import com.android.aksiem.eizzy.ui.order.OrderItemsFragment;
import com.android.aksiem.eizzy.ui.settlement.SettlementDurationFragment;
import com.android.aksiem.eizzy.ui.settlement.SettlementFragment;
import com.android.aksiem.eizzy.ui.user.UserDetailFragment;
import com.android.aksiem.eizzy.ui.login.VendorOnboardingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract VendorOnboardingFragment contributeVendorOnboardingFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract CreateUserAccountFragment contributeCreateUserAccountFragment();

    @ContributesAndroidInjector
    abstract ForgotPasswordFragment contributeForgotPasswordFragment();

    @ContributesAndroidInjector
    abstract ValidateOTPFragment contributeValidateOTPFragment();

    @ContributesAndroidInjector
    abstract CreatePasswordFragment contributeCreatePasswordFragment();

    @ContributesAndroidInjector
    abstract UserDetailFragment contributeUserDetailFragment();

    @ContributesAndroidInjector
    abstract OrderItemsFragment contributeOrderItemsFragment();

    @ContributesAndroidInjector
    abstract OrderDetailsFragment contributeOrderDetailsFragment();

    @ContributesAndroidInjector
    abstract CreateOrderFragment contributeCreateOrderFragment();

    @ContributesAndroidInjector
    abstract SettlementFragment contributeSettlementFragment();

    @ContributesAndroidInjector
    abstract SettlementDurationFragment contributeSettlementDurationFragment();

    @ContributesAndroidInjector
    abstract SortFilterDialogFragment contributeSortFilterDialogFragment();

    @ContributesAndroidInjector
    abstract ConfirmationFragment contributeConfirmationFragment();

}
