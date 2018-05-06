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
import com.android.aksiem.eizzy.databinding.UserDetailFragmentBinding;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.User;

import javax.inject.Inject;

/**
 * Created by napendersingh on 06/05/18.
 */

public class UserDetailFragment extends NavigationFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    AutoClearedValue<UserDetailFragmentBinding> binding;

    private UserDetailViewModel userDetailViewModel;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

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
        UserDetailFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.user_detail_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailViewModel.class);
        userDetailViewModel.getUser().observe(this, userResource -> {
            binding.get().setUser(userResource);
        });
        userDetailViewModel.setUser(getUser());
    }

    private User getUser() {
        return new User("1", "", "Napender Singh",
                "Aksiem", "7259412526", "napender.singh@gmail.com", "772373737373");
    }

}
