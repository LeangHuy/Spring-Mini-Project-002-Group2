package com.config.client;

import com.config.configuration.interceptor.FeignClientInterceptor;
import com.config.response.ApiResponse;
import com.config.response.GroupResponse;
import com.config.response.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.UUID;

@FeignClient(name = "keycloak-admin-client")
public interface KeycloakFeignClient {
    @CircuitBreaker(name = "keycloakAdminClient", fallbackMethod = "groupFallback")
    @GetMapping("/api/v1/groups/{groupId}")
    ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable UUID groupId);

    @CircuitBreaker(name = "keycloakAdminClient", fallbackMethod = "userFallback")
    @GetMapping("/api/v1/users/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId);


        default ResponseEntity<ApiResponse<GroupResponse>> groupFallback(@PathVariable UUID groupId, Throwable throwable) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Service Unavailable")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .payload(new GroupResponse("unavailable", "unavailable"))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    default ResponseEntity<ApiResponse<UserResponse>> userFallback(@PathVariable String userId, Throwable throwable) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("Get user by id successfully.")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .payload(new UserResponse("unavailable", "unavailable", "unavailable", "unavailable", "unavailable", "unavailable", "unavailable"))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
