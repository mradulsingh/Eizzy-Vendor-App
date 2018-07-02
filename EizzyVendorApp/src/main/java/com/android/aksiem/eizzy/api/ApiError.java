package com.android.aksiem.eizzy.api;

import com.google.gson.annotations.SerializedName;

public class ApiError {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
