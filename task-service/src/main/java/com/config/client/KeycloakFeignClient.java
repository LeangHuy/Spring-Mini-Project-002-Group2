package com.config.client;

import com.config.response.ApiResponse;
import com.config.response.GroupResponse;
import com.config.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "keycloak-admin-client")
public interface KeycloakFeignClient {
    @GetMapping("/api/v1/groups/{groupId}")
    ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable UUID groupId);

    @GetMapping("/api/v1/users/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId);
}
