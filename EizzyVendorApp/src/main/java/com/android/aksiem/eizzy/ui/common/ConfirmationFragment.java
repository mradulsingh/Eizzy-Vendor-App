package com.android.aksiem.eizzy.ui.common;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.ConfirmationFragmentBinding;
import com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;

public class ConfirmationFragment extends NavigationFragment {

    private static String BUNDLE_BACK_BTN_KEY = "backBtnKey";
    private static String BUNDLE_TITLE_KEY = "titleKey";
    private static String BUNDLE_SUBTITLE_KEY = "subtitleKey";
    private static String BUNDLE_ACTION_BTN_KEY = "actionBtnKey";
    private ClickActionHandler confirmationListener;

    private String title;
    private String subTitle;
    private String actionButtonText;
    private Boolean backButtonExists;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<ConfirmationFragmentBinding> binding;


    public static ConfirmationFragment newInstance(Boolean backButtonExists, String title,
                                                   String subTitle, String actionButtonText,
                                                   ClickActionHandler listener) {

        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_BACK_BTN_KEY, backButtonExists);
        args.putString(BUNDLE_TITLE_KEY, title);
        args.putString(BUNDLE_SUBTITLE_KEY, subTitle);
        args.putString(BUNDLE_ACTION_BTN_KEY, actionButtonText);

        ConfirmationFragment fragment = new ConfirmationFragment();

        fragment.setArguments(args);
        fragment.setConfirmationListener(listener);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        ConfirmationFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.confirmation_fragment, container, false, dataBindingComponent);

        binding = new AutoClearedValue<>(this, dataBinding);

        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    private void init(@Nullable Bundle savedInstanceState) {
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;

        title = args.getString(BUNDLE_TITLE_KEY);
        subTitle = args.getString(BUNDLE_SUBTITLE_KEY);
        actionButtonText = args.getString(BUNDLE_ACTION_BTN_KEY);
        backButtonExists = args.getBoolean(BUNDLE_BACK_BTN_KEY);
    }


    @Override
    public NavigationBuilder buildNavigation() {
        CollapsableToolbarBuilder builder = CollapsableToolbarBuilder.mainCollapsableToolbarWithBottomAction()
                .toolbarTitle(title)
                .toolbarSubtitle(subTitle)
                .setBottomActionTitle(actionButtonText)
                .setBottomActionClickHandler(confirmationListener);

        if (backButtonExists) {
            builder.toolbarNavIconRes(R.drawable.ic_back)
                    .setToolbarNavClickListener(v -> onBackPressed());

        }

        return builder;

    }


    public void setConfirmationListener(ClickActionHandler confirmationListener) {
        this.confirmationListener = confirmationListener;
    }
}
