package io.alensnajder.gatekeeper.ui.auth;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.auth.login.LoginFragment;
import io.alensnajder.gatekeeper.ui.auth.signup.SignUpFragment;
import io.alensnajder.gatekeeper.ui.auth.success.SignUpSuccessFragment;

@Module
public abstract class AuthFragmentProvider {
    @ContributesAndroidInjector
    abstract LoginFragment bindLoginFragment();

    @ContributesAndroidInjector
    abstract SignUpFragment bindSignUpFragment();

    @ContributesAndroidInjector
    abstract SignUpSuccessFragment bindSignUpSuccessFragment();
}
