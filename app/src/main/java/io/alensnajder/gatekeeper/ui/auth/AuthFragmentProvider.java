package io.alensnajder.gatekeeper.ui.auth;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.auth.login.LoginFragment;

@Module
public abstract class AuthFragmentProvider {
    @ContributesAndroidInjector
    abstract LoginFragment bindLoginFragment();
}
