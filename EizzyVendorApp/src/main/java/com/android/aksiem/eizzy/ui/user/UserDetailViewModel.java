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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.vo.User;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class UserDetailViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<User> user = new MutableLiveData<>();

    @Inject
    UserDetailViewModel() {
    }

    @VisibleForTesting
    public void setUser(User user) {
        this.user.setValue(user);
    }

    @VisibleForTesting
    public LiveData<User> getUser() {
        return user;
    }
}
