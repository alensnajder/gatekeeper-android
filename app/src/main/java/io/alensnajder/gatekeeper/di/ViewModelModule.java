package io.alensnajder.gatekeeper.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.alensnajder.gatekeeper.ui.auth.login.LoginViewModel;
import io.alensnajder.gatekeeper.ui.auth.signup.SignUpViewModel;
import io.alensnajder.gatekeeper.ui.auth.success.SignUpSuccessViewModel;
import io.alensnajder.gatekeeper.ui.gate.GateViewModel;
import io.alensnajder.gatekeeper.ui.main.MainViewModel;
import io.alensnajder.gatekeeper.ui.record.RecordViewModel;
import io.alensnajder.gatekeeper.ui.user.UserViewModel;
import io.alensnajder.gatekeeper.ui.user.detail.UserDetailViewModel;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpViewModel(SignUpViewModel signUpViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GateViewModel.class)
    abstract ViewModel bindGateViewModel(GateViewModel gateViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel.class)
    abstract ViewModel bindRecordViewModel(RecordViewModel recordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel.class)
    abstract ViewModel bindUserDetailViewModel(UserDetailViewModel userDetailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}