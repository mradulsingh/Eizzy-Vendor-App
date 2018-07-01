package com.android.aksiem.eizzy.ui.webviews;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.WebviewFragmentBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.AppToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.util.Logger;

import javax.inject.Inject;

public class WebviewFragment extends NavigationFragment {

    private static final String KEY_URL = "url";
    private static final String KEY_SCREEN_TITLE = "screen_title";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    AutoClearedValue<WebviewFragmentBinding> binding;

    private WebviewViewModel webviewViewModel;

    @Inject
    ToastController toastController;

    private String mUrl;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    public static WebviewFragment newInstance(String url, String screenTitle) {
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        args.putString(KEY_SCREEN_TITLE, screenTitle);
        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return AppToolbarBuilder.mainToolbarWithBottomAction()
                .toolbarTitle(getScreenTitle())
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed());
    }

    private String getScreenTitle() {
        String screenTitle = getString(R.string.app_name);
        Bundle args = getArguments();
        if (args.containsKey(KEY_SCREEN_TITLE)) {
            screenTitle = getArguments().getString(KEY_SCREEN_TITLE);
        }
        return screenTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        WebviewFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.webview_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webviewViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WebviewViewModel.class);
        init();
    }

    private void init() {
        mUrl = getArguments().getString(KEY_URL);
        webviewViewModel.setUrl(mUrl);
        setupWebView();
    }

    private void setupWebView() {
        WebView webView = binding.get().webView;
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setInitialScale(0);
        webView.setWebViewClient(new Callback());
        webView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        onBackPressed();
                        return true;
                }
            }
            return false;
        });
        webView.loadUrl(mUrl);
    }

    @Override
    protected void onBackPressed() {
        if (binding.get().webView.canGoBack()) {
            binding.get().webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class Callback extends WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return overriddenUrlLoading(view, url);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return overriddenUrlLoading(view, url);
        }

        private boolean overriddenUrlLoading(WebView view, String url) {
            Logger.d("url :: " + url);
//            if (launchInBrowser(url)) {
//                view.loadUrl(url);
//            } else {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                browserIntent.setData(Uri.parse(url));
//                if (browserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(browserIntent);
//                }
//            }
            return false;
        }

        public void onPageFinished(WebView view, String url) {
        }

        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            view.loadUrl("about:blank");
            showWebPageErrorDialog(failingUrl);
        }

        private boolean launchInBrowser(String url) {
            return true;
        }
    }

    private void showWebPageErrorDialog(final String failingUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.webpage_error_dialog_message))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        binding.get().webView.loadUrl(failingUrl);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showLoading() {

    }

    private void hideLoading() {

    }
}
