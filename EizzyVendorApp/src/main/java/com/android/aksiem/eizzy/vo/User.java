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

package com.android.aksiem.eizzy.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = "userId")
public class User {
    @SerializedName("userId")
    @NonNull
    public final String userId;

    @SerializedName("profile_url")
    public final String profileUrl;

    @SerializedName("name")
    public final String contactPerson;

    @SerializedName("business_name")
    public final String businessName;

    @SerializedName("mobile")
    public final String mobile;

    @SerializedName("email")
    public final String email;

    @SerializedName("aadhaar")
    public final String aadhaar;

    public User(@NonNull String userId, String profileUrl, String contactPerson, String businessName, String mobile, String email, String aadhaar) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.contactPerson = contactPerson;
        this.businessName = businessName;
        this.mobile = mobile;
        this.email = email;
        this.aadhaar = aadhaar;
    }

}
