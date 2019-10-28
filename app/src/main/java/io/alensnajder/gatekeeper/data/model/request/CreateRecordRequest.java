package io.alensnajder.gatekeeper.data.model.request;

import com.google.gson.annotations.SerializedName;

public class CreateRecordRequest {

    @SerializedName("gate_id")
    private int gateId;

    public CreateRecordRequest(int gateId) {
        this.gateId = gateId;
    }

    public int getGateId() {
        return gateId;
    }
}
