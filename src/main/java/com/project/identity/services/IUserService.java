package com.project.identity.services;

import com.project.identity.dtos.requests.UserCreateRequest;
import com.project.identity.dtos.requests.UserUpdateRequest;
import com.project.identity.dtos.responses.UserResponse;

import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse createUser(UserCreateRequest request);

    UserResponse updateUser(String id, UserUpdateRequest request);

    UserResponse getMe();
    void deleteUser(String id);
}

