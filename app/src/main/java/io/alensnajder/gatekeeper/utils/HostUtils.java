package io.alensnajder.gatekeeper.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.network.HostInterceptor;

@Singleton
public class HostUtils {

    private AppPreferences appPreferences;
    private HostInterceptor hostInterceptor;

    @Inject
    public HostUtils(AppPreferences appPreferences, HostInterceptor hostInterceptor) {
        this.appPreferences = appPreferences;
        this.hostInterceptor = hostInterceptor;
    }

    public void setHost() {
        String host = appPreferences.getHost();

        if (host != null) {
            hostInterceptor.setHost(host);
        }
    }

    public void setHost(String host) {
        appPreferences.setHost(host);
        hostInterceptor.setHost(host);
    }

    public boolean isHostSet() {
        String host = appPreferences.getHost();

        if (host != null && !host.isEmpty()) {
            return true;
        }

        return false;
    }

    public String getHost() {
        return appPreferences.getHost();
    }
}
