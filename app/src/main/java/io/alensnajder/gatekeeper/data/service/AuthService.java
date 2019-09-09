package io.alensnajder.gatekeeper.data.service;

import io.alensnajder.gatekeeper.data.model.AccessToken;
import io.alensnajder.gatekeeper.data.model.Auth;
import io.alensnajder.gatekeeper.data.model.request.AccessTokenByPasswordRequest;
import io.alensnajder.gatekeeper.data.model.request.AccessTokenByRefreshRequest;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/v1/auth?grant_type=password")
    Single<Auth> getAccessTokenByPassword(@Body AccessTokenByPasswordRequest request);

    @POST("/v1/auth?grant_type=refresh_token")
    Call<AccessToken> getAccessTokenByRefreshToken(@Body AccessTokenByRefreshRequest request);
}
