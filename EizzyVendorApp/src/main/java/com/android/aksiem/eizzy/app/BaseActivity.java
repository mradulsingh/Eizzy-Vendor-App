package com.android.aksiem.eizzy.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.aksiem.eizzy.R;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_activity);
        View view = getLayoutInflater().inflate(layoutResID, null);
        FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParam);

        ((LinearLayout) findViewById(R.id.ll_main_container))
                .addView(view);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
