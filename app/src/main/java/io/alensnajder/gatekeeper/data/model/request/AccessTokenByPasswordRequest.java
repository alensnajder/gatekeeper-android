package io.alensnajder.gatekeeper.data.model.request;

import com.google.gson.annotations.SerializedName;

public class AccessTokenByPasswordRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public AccessTokenByPasswordRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}