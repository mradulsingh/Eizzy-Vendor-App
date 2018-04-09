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
import com.android.aksiem.eizzy.ui.login.LoginFragment;
import com.android.aksiem.eizzy.ui.vendorValueProp.VendorOnboardingFragment;
import com.android.aksiem.eizzy.ui.vendorValueProp.VendorOnboardingFragment;

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

    public void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        String tag = LoginFragment.class.getSimpleName();
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToVendorOnboardingFragment() {
        VendorOnboardingFragment fragment = VendorOnboardingFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }
}
