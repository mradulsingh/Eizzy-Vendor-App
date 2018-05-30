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

package com.android.aksiem.eizzy.api;

import android.arch.lifecycle.LiveData;

import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.Store;
import com.android.aksiem.eizzy.vo.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * REST API access points
 */
public interface AppService {

    @Multipart
    @POST("dispatcher/store")
    LiveData<ApiResponse<EizzyApiRespone<Store>>> createUserAccount(@Header("language") String language,
                                                                    @Part("businessName") String businessName,
                                                                    @Part("contactPerson") String contactPerson,
                                                                    @Part("countryCode") String countryCode,
                                                                    @Part("phone") String contactMobile,
                                                                    @Part("email") String contactEmail,
                                                                    @Part("deviceId") String deviceId,
                                                                    @Part("deviceType") int deviceType);

    @POST("user/forgotpassword")
    LiveData<ApiResponse<User>> onForgotPassword(@Field("userId") String userId);

    @POST("user/validateotp")
    LiveData<ApiResponse<User>> validateOTP(@Field("otp") String otp);


    @POST("user/resetPassword")
    LiveData<ApiResponse<User>> resetPassword(@Field("password") String password);
}
