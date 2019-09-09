package io.alensnajder.gatekeeper.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;
import io.alensnajder.gatekeeper.ui.auth.AuthFragmentProvider;
import io.alensnajder.gatekeeper.ui.main.MainActivity;
import io.alensnajder.gatekeeper.ui.main.MainFragmentProvider;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = AuthFragmentProvider.class)
    abstract AuthActivity bindAuthActivity();

    @ContributesAndroidInjector(modules = MainFragmentProvider.class)
    abstract MainActivity bindMainActivity();
}