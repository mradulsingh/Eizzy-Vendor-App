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
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.User;

import javax.inject.Inject;
/**
 * Created by Mradul on 10/06/18.
 */

/**
 * Repository that handles User Login.
 */
@AppScope
public class UserRepository {
    private final AppExecutors appExecutors;

    @Inject
    UserRepository(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> doUserLogin(String userId, String password) {
        return new NoCacheNetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected LiveData<User> getCallResult(@NonNull User user) {
                MutableLiveData<User> repoList = new MutableLiveData();
                repoList.setValue(user);
                return repoList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                //return appService.doManagerLogin(userId, password);
                return null;
            }
        }.asLiveData();
    }


    public LiveData<Resource<User>> onForgotPassword(String userId) {
        return new NoCacheNetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected LiveData<User> getCallResult(@NonNull User user) {
                MutableLiveData<User> repoList = new MutableLiveData();
                repoList.setValue(user);
                return repoList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                //return appService.onForgotPassword(userId);
                return null;
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> validateOTP(String otp) {
        return new NoCacheNetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected LiveData<User> getCallResult(@NonNull User user) {
                MutableLiveData<User> repoList = new MutableLiveData();
                repoList.setValue(user);
                return repoList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                //return appService.validateOTP(otp);
                return null;
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> resetPassword(String password) {
        return new NoCacheNetworkBoundResource<User, User>(appExecutors) {
            @Override
            protected LiveData<User> getCallResult(@NonNull User user) {
                MutableLiveData<User> repoList = new MutableLiveData();
                repoList.setValue(user);
                return repoList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                //return appService.resetPassword(password);
                return null;
            }
        }.asLiveData();
    }
}
