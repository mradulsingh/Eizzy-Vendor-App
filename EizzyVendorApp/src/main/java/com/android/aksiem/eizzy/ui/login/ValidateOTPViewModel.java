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

import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.UserRepository;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class ValidateOTPViewModel extends ViewModel {

    private String otp;

    private UserRepository userRepository;

    @Inject
    public ValidateOTPViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setOTP(String otp) {
        this.otp = otp;
    }

    @VisibleForTesting
    void onValidateOTP() {
        userRepository.validateOTP(otp);
    }

    @VisibleForTesting
    public void retry() {
        onValidateOTP();
    }
}
