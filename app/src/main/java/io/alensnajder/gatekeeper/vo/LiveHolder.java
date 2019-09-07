package io.alensnajder.gatekeeper.vo;

public class LiveHolder<T> {

    public final Status status;
    public final String errorMessage;
    public final T data;

    public LiveHolder(Status status, T data, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> LiveHolder<T> success(T data) {
        return new LiveHolder<>(Status.SUCCESS, data, null);
    }

    public static <T> LiveHolder<T> error(String errorMessage) {
        return new LiveHolder<>(Status.ERROR, null, errorMessage);
    }
}
