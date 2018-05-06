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

package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.CreateOrderFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.AppToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreateOrderFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    AutoClearedValue<CreateOrderFragmentBinding> binding;

    private CreateOrderViewModel createOrderViewModel;

    @Inject
    ToastController toastController;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    @Override
    public NavigationBuilder buildNavigation() {
        return AppToolbarBuilder.mainToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_create_order)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_create_order)
                .setBottomActionClickHandler(v -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        toastController.showErrorToast("Order Creation Failed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreateOrderFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.create_order_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createOrderViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateOrderViewModel.class);
    }

    private void createOrderListing(View v) {
        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());
    }
}
