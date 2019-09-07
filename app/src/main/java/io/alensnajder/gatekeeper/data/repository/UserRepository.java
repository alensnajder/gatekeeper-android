package io.alensnajder.gatekeeper.data.repository;

import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.model.request.CreateUserRequest;
import io.alensnajder.gatekeeper.data.service.UserService;
import io.reactivex.Single;

@Singleton
public class UserRepository {

    private UserService userService;

    public UserRepository(UserService userService) {
        this.userService = userService;
    }

    public Single<User> createUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        CreateUserRequest request = new CreateUserRequest(firstName, lastName, email, password, confirmPassword);

        return userService.createUser(request);
    }
}
