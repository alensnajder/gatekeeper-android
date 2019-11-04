package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.model.request.CreateUserRequest;
import io.alensnajder.gatekeeper.data.model.request.UpdateUserStatusRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("/v1/users")
    Single<List<User>> getUsers();

    @GET("/v1/users/{id}")
    Single<User> getUser(@Path("id") int id);

    @POST("/v1/users")
    Single<User> createUser(@Body CreateUserRequest request);

    @POST("/v1/users/{id}/status")
    Single<User> updateStatus(@Path("id") int id, @Body UpdateUserStatusRequest request);
}