package com.config.service;

import com.config.model.request.UserRequest;
import com.config.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse addUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(String userId);

    UserResponse getUserByUsername(String username);

    UserResponse getUserByEmail(String email);

    UserResponse updateUserByUserId(String userId, UserRequest userRequest);
}
