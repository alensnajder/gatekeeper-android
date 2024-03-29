package io.alensnajder.gatekeeper.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.alensnajder.gatekeeper.ui.gate.GateFragment;
import io.alensnajder.gatekeeper.ui.record.RecordFragment;
import io.alensnajder.gatekeeper.ui.user.UserFragment;
import io.alensnajder.gatekeeper.ui.user.detail.UserDetailFragment;

@Module
public abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    abstract GateFragment bindGateFragment();

    @ContributesAndroidInjector
    abstract RecordFragment bindRecordFragment();

    @ContributesAndroidInjector
    abstract UserFragment bindUserFragment();

    @ContributesAndroidInjector
    abstract UserDetailFragment bindUserDetailFragment();
}