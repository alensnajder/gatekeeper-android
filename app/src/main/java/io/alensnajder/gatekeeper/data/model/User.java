package io.alensnajder.gatekeeper.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("is_admin")
    private int isAdmin;
    @SerializedName("is_active")
    private int isActive;

    public User(int id, String firstName, String lastName, String email, int isAdmin, int isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public int isActive() {
        return isActive;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}