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

package com.android.aksiem.eizzy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.User;

import javax.inject.Inject;

/**
 * Repository that handles User Login.
 */
@AppScope
public class LoginRepository {
    private final AppService appService;
    private final AppExecutors appExecutors;
    private final AppResourceManager resourceManager;

    @Inject
    LoginRepository(AppExecutors appExecutors, AppService appService, AppResourceManager resourceManager) {
        this.appService = appService;
        this.appExecutors = appExecutors;
        this.resourceManager = resourceManager;
    }

    public LiveData<Resource<User>> doUserLogin(String userId, String password) {
        return new NetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return true;
            }

            @Override
            protected LiveData<User> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return appService.doUserLogin(userId, password);
            }
        }.asLiveData();
    }
}
