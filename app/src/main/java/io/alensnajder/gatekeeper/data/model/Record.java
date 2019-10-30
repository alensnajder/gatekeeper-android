package io.alensnajder.gatekeeper.data.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("gate")
    private Gate gate;
    @SerializedName("created_at")
    private Date createdAt;

    public Record(int id, User user, Gate gate, Date createdAt) {
        this.id = id;
        this.user = user;
        this.gate = gate;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtString() {
        return new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(createdAt);
    }
}
