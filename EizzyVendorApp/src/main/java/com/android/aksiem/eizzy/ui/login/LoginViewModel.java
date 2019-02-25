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

package com.android.aksiem.eizzy.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.StoreManagerRepository;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.StoreManager;

import javax.inject.Inject;

/**
 * Created by Mradul on 10/06/18.
 */

public class LoginViewModel extends ViewModel {

    private String mPhone;

    private String mPassword;

    private StoreManagerRepository storeManagerRepository;

    @Inject
    public LoginViewModel(StoreManagerRepository storeManagerRepository) {
        this.storeManagerRepository = storeManagerRepository;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    @VisibleForTesting
    public LiveData<Resource<EizzyApiRespone<StoreManager>>> doManagerLogin(String phone,
                                                                            String password) {
        setPhone(phone);
        setPassword(password);
        return storeManagerRepository.doManagerLogin(mPhone, mPassword);
    }

    @VisibleForTesting
    public void retry() {
        doManagerLogin(mPhone, mPassword);
    }
}
