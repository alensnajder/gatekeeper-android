package io.alensnajder.gatekeeper.ui.main;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.utils.AccountUtils;
import io.alensnajder.gatekeeper.utils.HostUtils;

public class MainViewModel extends ViewModel {

    private AccountUtils accountUtils;
    private HostUtils hostUtils;

    @Inject
    public MainViewModel(AccountUtils accountUtils, HostUtils hostUtils) {
        this.accountUtils = accountUtils;
        this.hostUtils = hostUtils;
    }

    public boolean isReadyToRun() {
        if (accountUtils.isLoggedIn() && hostUtils.isHostSet()) {
            hostUtils.setHost();
            return true;
        }

        return false;
    }

    public void logout() {
        accountUtils.handleLogout();
    }
}
