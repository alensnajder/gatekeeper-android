package io.alensnajder.gatekeeper.data.model;

import com.google.gson.annotations.SerializedName;

public class Gate {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("gpio_pin")
    private int gpioPin;
    @SerializedName("duration")
    private float duration;

    public Gate(int id, String name, int gpioPin, float duration) {
        this.id = id;
        this.name = name;
        this.gpioPin = gpioPin;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGpioPin() {
        return gpioPin;
    }

    public float getDuration() {
        return duration;
    }
}
