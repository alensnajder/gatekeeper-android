package io.alensnajder.gatekeeper;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.alensnajder.gatekeeper.di.DaggerAppComponent;

public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
