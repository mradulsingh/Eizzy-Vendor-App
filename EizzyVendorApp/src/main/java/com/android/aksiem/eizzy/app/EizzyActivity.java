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

package com.android.aksiem.eizzy.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.api.MqttClientService;
import com.android.aksiem.eizzy.databinding.EizzyActivityBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.vo.StoreManager;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class EizzyActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    NavigationController navigationController;

    private EizzyActivityBinding binding;

    @Inject
    MqttClientService mqttClientService;

    @Inject
    ToastController toastController;

    @Inject
    AppPrefManager appPrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.eizzy_activity,
                null, false);
        setContentView(binding.getRoot());
        setupBottomNavigation();
        setupNavDrawer();

        //init mqttClientService
        mqttClientService.initService();

        if (savedInstanceState == null) {

            if (EizzyAppState.ManagerLoggedIn.isManagerLoggedIn(appPrefManager)) {
                StoreManager manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(
                        appPrefManager);
                Logger.tag("eizzyAppManager").d(manager.toString());
                navigationController.navigateToOrderItemsFragment();
            } else {
                navigationController.navigateToVendorOnboardingFragment();
            }
        }
    }

    protected Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager
                .findFragmentById(R.id.container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void setupNavDrawer() {
        binding.navView.setNavigationItemSelectedListener(this);
    }

    public void setDrawerLockMode(boolean unlocked) {
        if (unlocked) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.START);
    }

    public void setBottomNavigationViewVisibility(boolean visible) {
        if (visible) {
            binding.bottomNavigation.setVisibility(View.VISIBLE);
        } else {
            binding.bottomNavigation.setVisibility(View.GONE);
        }
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_orders:
                    // TODO: Navigate to orders fragment
                    navigationController.navigateToOrderItemsFragment();
                    return true;
                case R.id.nav_settlements:
                    // TODO: Navigate to settlements fragment
                    navigationController.navigateToSettlementFragment();
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_account:
                navigationController.navigateToStoreManagerFragment();
                break;
            case R.id.nav_tnc:
                toastController.showSuccessToast("show T&C");
                break;
            case R.id.nav_support:
                toastController.showSuccessToast("show Support");
                break;
            case R.id.nav_about_us:
                toastController.showSuccessToast("show About Us");
                break;
        }

        return true;
    }
}
