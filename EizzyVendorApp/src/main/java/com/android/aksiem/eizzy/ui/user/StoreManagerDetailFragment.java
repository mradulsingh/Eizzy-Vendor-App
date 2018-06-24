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

package com.android.aksiem.eizzy.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.EizzyAppState;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.StoreManagerDetailFragmentBinding;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.StoreManager;

import javax.inject.Inject;

/**
 * Created by napendersingh on 06/05/18.
 */

public class StoreManagerDetailFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    AppPrefManager appPrefManager;

    private StoreManager manager;

    AutoClearedValue<StoreManagerDetailFragmentBinding> binding;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    public static StoreManagerDetailFragment newInstance() {
        StoreManagerDetailFragment fragment = new StoreManagerDetailFragment();
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_profile_detail)
                .toolbarSubtitleRes(R.string.screen_subtitle_profile_detail)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StoreManagerDetailFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.store_manager_detail_fragment, container,
                        false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        manager = EizzyAppState.ManagerLoggedIn.getManagerDetails(appPrefManager);
        binding.get().setManager(manager);
    }


}
