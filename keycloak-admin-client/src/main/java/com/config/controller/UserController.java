package com.config.controller;

import com.config.model.request.UserRequest;
import com.config.response.ApiResponse;
import com.config.response.UserResponse;
import com.config.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@SecurityRequirement(name = "mini-project-2")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Add user to keycloak")
    public ResponseEntity<ApiResponse<UserResponse> > register(@RequestBody @Valid UserRequest userRequest) throws Exception {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("User created successfully.")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(userService.addUser(userRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
