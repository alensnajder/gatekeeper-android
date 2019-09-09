package io.alensnajder.gatekeeper.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.gate.GateFragment;

@Module
public abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    abstract GateFragment bindGateFragment();
}