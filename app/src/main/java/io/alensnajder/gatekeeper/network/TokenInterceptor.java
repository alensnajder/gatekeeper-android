package io.alensnajder.gatekeeper.network;

import java.io.IOException;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.AppPreferences;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private AppPreferences appPreferences;
    private String accessToken;

    @Inject
    public TokenInterceptor(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (accessToken == null) {
            String accessTokenFromShared = appPreferences.getAccessToken();

            if (accessTokenFromShared != null) {
                this.accessToken = accessTokenFromShared;
            }
        }

        if (accessToken != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
        }

        return chain.proceed(request);
    }
}
