package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.model.request.CreateUserRequest;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @DELETE("/v1/users/{id}")
    Single<Response<Void>> removeUser(@Path("id") int id);
}