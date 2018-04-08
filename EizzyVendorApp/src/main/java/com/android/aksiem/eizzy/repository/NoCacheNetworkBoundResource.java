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
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.android.aksiem.eizzy.api.ApiResponse;
import com.android.aksiem.eizzy.app.AppExecutors;
import com.android.aksiem.eizzy.util.Objects;
import com.android.aksiem.eizzy.vo.Resource;

/**
 * A generic class that can provide a resource backed by only the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NoCacheNetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NoCacheNetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        fetchFromNetwork();
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.mainThread().execute(() ->
                        result.addSource(getCallResult(processResponse(response)),
                                newData -> setValue(Resource.success(newData)))
                );
            } else {
                onFetchFailed();
                result.setValue(Resource.error(response.errorMessage, null));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> getCallResult(@NonNull RequestType item);


    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
