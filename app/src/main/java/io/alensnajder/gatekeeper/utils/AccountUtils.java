package io.alensnajder.gatekeeper.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.network.TokenInterceptor;

@Singleton
public class AccountUtils {

    private AppPreferences appPreferences;
    private TokenInterceptor tokenInterceptor;

    @Inject
    public AccountUtils(AppPreferences appPreferences, TokenInterceptor tokenInterceptor) {
        this.appPreferences = appPreferences;
        this.tokenInterceptor = tokenInterceptor;
    }

    public void handleLogin(String accessToken, String refreshToken) {
        appPreferences.setAccessToken(accessToken);
        appPreferences.setRefreshToken(refreshToken);
        tokenInterceptor.setAccessToken(accessToken);
    }

    public void handleLogout() {
        appPreferences.setAccessToken(null);
        appPreferences.setRefreshToken(null);
    }

    public boolean isLoggedIn() {
        String refreshToken = appPreferences.getRefreshToken();

        if (refreshToken != null) {
            return true;
        }

        return false;
    }
}
