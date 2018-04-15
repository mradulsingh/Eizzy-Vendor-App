package com.android.aksiem.eizzy.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.android.aksiem.eizzy.ui.login.LoginViewModel;
import com.android.aksiem.eizzy.ui.vendorOnboarding.VendorOnboardingViewModel;
import com.android.aksiem.eizzy.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VendorOnboardingViewModel.class)
    abstract ViewModel bindVendorOnboardingViewModel(VendorOnboardingViewModel vendorOnboardingViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
