package io.alensnajder.gatekeeper.data.service;

import io.alensnajder.gatekeeper.data.model.Auth;
import io.alensnajder.gatekeeper.data.model.request.AccessTokenByPasswordRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/v1/auth?grant_type=password")
    Single<Auth> getAccessTokenByPassword(@Body AccessTokenByPasswordRequest request);
}
