package io.alensnajder.gatekeeper.data.model.request;

import com.google.gson.annotations.SerializedName;

public class AccessTokenByRefreshRequest {
    @SerializedName("refresh_token")
    private String refreshToken;

    public AccessTokenByRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
