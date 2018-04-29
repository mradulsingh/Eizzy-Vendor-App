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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.EizzyActivityBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class EizzyActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    NavigationController navigationController;

    private EizzyActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.eizzy_activity, null, false);
        setContentView(binding.getRoot());
        setupBottomNavigation();
        setupNavDrawer();
        if (savedInstanceState == null) {
            //navigationController.navigateToVendorOnboardingFragment();
            navigationController.navigateToOrderItemsFragment();
        }
    }

    protected BaseInjectableFragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager
                .findFragmentById(R.id.container);
        if (currentFragment != null && currentFragment instanceof BaseInjectableFragment) {
            return (BaseInjectableFragment) currentFragment;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseInjectableFragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        BaseInjectableFragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean checkPermissionEnabled(final String permission, final int permissionCode, String title, String message) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage(message);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {permission}
                                    , permissionCode);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            permissionCode);
                }
            } else {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

    public boolean checkPermissionEnabled(String permission, final int permissionCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{permission}, permissionCode);
            return false;
        } else {
            return true;
        }
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

        if (id == R.id.nav_tnc) {
            Toast.makeText(this, "show tnc", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_support) {
            Toast.makeText(this, "show support info", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_about_us) {
            Toast.makeText(this, "show about us", Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
