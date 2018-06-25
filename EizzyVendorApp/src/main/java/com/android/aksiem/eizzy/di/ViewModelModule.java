package com.android.aksiem.eizzy.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.android.aksiem.eizzy.ui.login.CreatePasswordViewModel;
import com.android.aksiem.eizzy.ui.login.CreateUserAccountViewModel;
import com.android.aksiem.eizzy.ui.login.ForgotPasswordViewModel;
import com.android.aksiem.eizzy.ui.login.LoginViewModel;
import com.android.aksiem.eizzy.ui.login.ValidateOTPViewModel;
import com.android.aksiem.eizzy.ui.login.VendorOnboardingViewModel;
import com.android.aksiem.eizzy.ui.order.CreateOrderViewModel;
import com.android.aksiem.eizzy.ui.order.OrderItemsViewModel;
import com.android.aksiem.eizzy.ui.settlement.SettlementViewModel;
import com.android.aksiem.eizzy.ui.user.UserDetailViewModel;
import com.android.aksiem.eizzy.ui.webviews.WebviewViewModel;
import com.android.aksiem.eizzy.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OrderItemsViewModel.class)
    abstract ViewModel bindOrderItemsViewModel(OrderItemsViewModel orderItemsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateOrderViewModel.class)
    abstract ViewModel bindCreateOrderViewModel(CreateOrderViewModel createOrderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettlementViewModel.class)
    abstract ViewModel bindSettlementViewModel(SettlementViewModel settlementViewModel);

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
    @ViewModelKey(UserDetailViewModel.class)
    abstract ViewModel bindUserDetailViewModel(UserDetailViewModel userDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VendorOnboardingViewModel.class)
    abstract ViewModel bindVendorOnboardingViewModel(VendorOnboardingViewModel vendorOnboardingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WebviewViewModel.class)
    abstract ViewModel bindWebviewViewModel(WebviewViewModel webviewViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
