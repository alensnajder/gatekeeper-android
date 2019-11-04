package io.alensnajder.gatekeeper.data.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateUserStatusRequest {

    @SerializedName("is_active")
    private boolean isActive;

    public UpdateUserStatusRequest(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }
}
