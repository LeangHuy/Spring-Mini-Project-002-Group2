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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .message("Get all users successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(userService.getAllUsers())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId){
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("Get user by id successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(userService.getUserById(userId))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/username")
    @Operation(summary = "Get user by username")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@RequestParam String username){
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("Get user by username successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(userService.getUserByUsername(username))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/email")
    @Operation(summary = "Get user by email")
    public  ResponseEntity<ApiResponse<UserResponse>> getUserByUsernamme(@RequestParam String email){
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("Get user by email successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(userService.getUserByEmail(email))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{userId}")
    @Operation(summary = "Update user by user id")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserByUserId(@PathVariable String userId, @RequestBody @Valid UserRequest userRequest){
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("Update user by user id successfully.")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(userService.updateUserByUserId(userId, userRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by user id")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserByUserId(@PathVariable String userId){
       userService.deleteUserByUserId(userId);
    ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                    .message("Delete user by user id successfully.")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
