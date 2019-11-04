package io.alensnajder.gatekeeper.network;

import java.io.IOException;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.data.model.AccessToken;
import io.alensnajder.gatekeeper.data.model.request.AccessTokenByRefreshRequest;
import io.alensnajder.gatekeeper.data.service.AuthService;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class TokenAuthenticator implements Authenticator {
    private AuthService authService;
    private AppPreferences appPreferences;
    private TokenInterceptor tokenInterceptor;

    @Inject
    public TokenAuthenticator(AuthService authService, AppPreferences appPreferences, TokenInterceptor tokenInterceptor) {
        this.authService = authService;
        this.appPreferences = appPreferences;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String refreshToken = appPreferences.getRefreshToken();

        if (refreshToken == null) {
            return null;
        }

        AccessTokenByRefreshRequest accessTokenRequest = new AccessTokenByRefreshRequest(refreshToken);
        Call<AccessToken> accessTokenCall = authService.getAccessTokenByRefreshToken(accessTokenRequest);
        retrofit2.Response<AccessToken> accessTokenResponse = accessTokenCall.execute();

        if (accessTokenResponse.code() == 401) {
            appPreferences.setRefreshToken(null);

            return null;
        }

        AccessToken accessToken = accessTokenResponse.body();

        if (accessToken != null) {
            appPreferences.setAccessToken(accessToken.getAccessToken());
            tokenInterceptor.setAccessToken(accessToken.getAccessToken());
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + accessToken.getAccessToken())
                    .build();
        } else {
            return null;
        }
    }

}