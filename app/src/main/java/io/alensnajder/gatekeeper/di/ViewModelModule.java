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
import io.alensnajder.gatekeeper.ui.record.RecordViewModel;

@Module
abstract class ViewModelModule {

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
    @ViewModelKey(SignUpSuccessViewModel.class)
    abstract ViewModel bindSignUpSuccessViewModel(SignUpSuccessViewModel signUpSuccessViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GateViewModel.class)
    abstract ViewModel bindGateViewModel(GateViewModel gateViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel.class)
    abstract ViewModel bindRecordViewModel(RecordViewModel recordViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}