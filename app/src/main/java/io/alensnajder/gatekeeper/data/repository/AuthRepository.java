package io.alensnajder.gatekeeper.data.repository;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.Auth;
import io.alensnajder.gatekeeper.data.model.request.AccessTokenByPasswordRequest;
import io.alensnajder.gatekeeper.data.service.AuthService;
import io.reactivex.Single;

public class AuthRepository {

    private AuthService authService;

    @Inject
    public AuthRepository(AuthService authService) {
        this.authService = authService;
    }

    public Single<Auth> getAccessTokenByPassword(String email, String password) {
        AccessTokenByPasswordRequest request = new AccessTokenByPasswordRequest(email, password);

        return authService.getAccessTokenByPassword(request);
    }
}
