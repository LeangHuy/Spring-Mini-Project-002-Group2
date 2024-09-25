package com.config.service;

import com.config.model.request.UserRequest;
import com.config.response.UserResponse;

public interface UserService {

    UserResponse addUser(UserRequest userRequest);
}
