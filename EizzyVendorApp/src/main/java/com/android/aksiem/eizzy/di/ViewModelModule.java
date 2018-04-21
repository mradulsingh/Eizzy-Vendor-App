package com.android.aksiem.eizzy.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.android.aksiem.eizzy.ui.login.CreatePasswordViewModel;
import com.android.aksiem.eizzy.ui.login.CreateUserAccountViewModel;
import com.android.aksiem.eizzy.ui.login.ForgotPasswordViewModel;
import com.android.aksiem.eizzy.ui.login.LoginViewModel;
import com.android.aksiem.eizzy.ui.login.ValidateOTPViewModel;
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
    @ViewModelKey(CreateUserAccountViewModel.class)
    abstract ViewModel bindCreateUserAccountViewModel(CreateUserAccountViewModel CreateUserAccountViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel.class)
    abstract ViewModel bindForgotPasswordViewModel(ForgotPasswordViewModel forgotPasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ValidateOTPViewModel.class)
    abstract ViewModel bindValidateOTPViewModel(ValidateOTPViewModel validateOTPViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreatePasswordViewModel.class)
    abstract ViewModel bindCreatePasswordViewModel(CreatePasswordViewModel createPasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VendorOnboardingViewModel.class)
    abstract ViewModel bindVendorOnboardingViewModel(VendorOnboardingViewModel vendorOnboardingViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
