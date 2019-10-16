package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.model.request.CreateUserRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @GET("/v1/users")
    Single<List<User>> getUsers();

    @POST("/v1/users")
    Single<User> createUser(@Body CreateUserRequest request);
}