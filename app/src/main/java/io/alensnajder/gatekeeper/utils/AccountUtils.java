package io.alensnajder.gatekeeper.utils;

import com.auth0.android.jwt.JWT;

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

    public int getAccountId() {
        String accessToken = appPreferences.getAccessToken();

        if (accessToken != null) {
            JWT jwt = new JWT(accessToken);

            return jwt.getClaim("id").asInt();
        }

        return 0;
    }

    public boolean isActive() {
        String accessToken = appPreferences.getAccessToken();

        if (accessToken != null) {
            JWT jwt = new JWT(accessToken);

            return jwt.getClaim("is_active").asBoolean();
        }

        return false;
    }
}
