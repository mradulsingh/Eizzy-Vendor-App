package com.android.aksiem.eizzy.ui.webviews;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class WebviewViewModel extends ViewModel {
    private String mUrl;

    @Inject
    public WebviewViewModel() {
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
