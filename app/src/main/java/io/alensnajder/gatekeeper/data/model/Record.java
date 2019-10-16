package io.alensnajder.gatekeeper.data.model;

import com.google.gson.annotations.SerializedName;

public class Record {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("gate")
    private Gate gate;

    public Record(int id, User user, Gate gate) {
        this.id = id;
        this.user = user;
        this.gate = gate;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Gate getGate() {
        return gate;
    }
}
