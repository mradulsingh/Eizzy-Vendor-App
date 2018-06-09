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
import com.android.aksiem.eizzy.vo.EizzyZone;
import com.android.aksiem.eizzy.vo.Store;
import com.android.aksiem.eizzy.vo.User;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * REST API access points
 */
public interface AppService {

    @Multipart
    @POST("dispatcher/store")
    LiveData<ApiResponse<EizzyApiRespone<Store>>> createUserAccount(
            @Header("language") String language,
            @Part("businessName") RequestBody businessName,
            @Part("contactPerson") RequestBody contactPerson,
            @Part("countryCode") RequestBody countryCode,
            @Part("phone") RequestBody contactMobile,
            @Part("email") RequestBody contactEmail,
            @Part("deviceId") RequestBody deviceId,
            @Part("deviceType") int deviceType);

    @Multipart
    @POST("dispatcher/sendOtp")
    LiveData<ApiResponse<EizzyApiRespone<String>>> sendOtp(
            @Header("language") String language,
            @Part("mobile") RequestBody phone,
            @Part("countryCode") RequestBody countryCode);

    @Multipart
    @POST("dispatcher/verifyOtp")
    LiveData<ApiResponse<EizzyApiRespone<String>>> validateOTP(
            @Header("language") String language,
            @Part("mobile") RequestBody phone,
            @Part("countryCode") RequestBody countryCode,
            @Part("code") RequestBody otp);

    @Multipart
    @PATCH("dispatcher/password")
    LiveData<ApiResponse<EizzyApiRespone<String>>> resetPassword(
            @Header("language") String language,
            @Part("mobile") RequestBody phone,
            @Part("countryCode") RequestBody countryCode,
            @Part("code") RequestBody otp,
            @Part("password") RequestBody password);

    @Multipart
    @POST("dispatcher/forgotPassword")
    LiveData<ApiResponse<EizzyApiRespone<String>>> forgotPassword(
            @Header("language") String language,
            @Part("emailOrMobile") RequestBody phone,
            @Part("verifyType") int verifyType);

    @Multipart
    @POST("dispatcher/zones")
    LiveData<ApiResponse<EizzyApiRespone<ArrayList<EizzyZone>>>> getEizzyZones(
            @Header("language") String language,
            @Header("authorization") String token,
            @Part("cityId") RequestBody cityId);


    @POST("user/resetPassword")
    LiveData<ApiResponse<User>> resetPassword(@Field("password") String password);
}
