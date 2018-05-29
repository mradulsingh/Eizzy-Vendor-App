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
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.Store;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreateUserAccountViewModel extends ViewModel {

    private String businessName;

    private String contactPerson;

    private String contactMobile;

    private String contactEmail;

    private StoreManagerRepository storeManagerRepository;

    @Inject
    public CreateUserAccountViewModel(StoreManagerRepository storeManagerRepository) {
        this.storeManagerRepository = storeManagerRepository;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @VisibleForTesting
    LiveData<Resource<Store>> createUserAccount() {
        return storeManagerRepository.createStoreAccount(businessName,
                contactPerson,
                contactMobile,
                contactEmail);
    }

    @VisibleForTesting
    public void retry() {
        createUserAccount();
    }
}
