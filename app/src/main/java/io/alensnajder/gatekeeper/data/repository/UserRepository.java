package io.alensnajder.gatekeeper.data.repository;

import java.util.List;

import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.model.request.CreateUserRequest;
import io.alensnajder.gatekeeper.data.model.request.UpdateUserStatusRequest;
import io.alensnajder.gatekeeper.data.service.UserService;
import io.reactivex.Single;

@Singleton
public class UserRepository {

    private UserService userService;

    public UserRepository(UserService userService) {
        this.userService = userService;
    }

    public Single<List<User>> getUsers() {
        return userService.getUsers();
    }

    public Single<User> getUser(int id) {
        return userService.getUser(id);
    }

    public Single<User> createUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        CreateUserRequest request = new CreateUserRequest(firstName, lastName, email, password, confirmPassword);

        return userService.createUser(request);
    }

    public Single<User> updateStatus(int id, boolean isActive) {
        UpdateUserStatusRequest request = new UpdateUserStatusRequest(isActive);

        return userService.updateStatus(id, request);
    }
}
