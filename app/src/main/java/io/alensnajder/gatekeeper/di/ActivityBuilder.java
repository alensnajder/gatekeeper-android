package io.alensnajder.gatekeeper.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract AuthActivity bindAuthActivity();
}