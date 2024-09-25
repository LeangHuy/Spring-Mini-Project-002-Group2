package com.config.controller;

import com.config.model.entity.Group;
import com.config.model.request.GroupRequest;
import com.config.response.ApiResponse;
import com.config.response.GroupResponse;
import com.config.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get current Group")
    public ResponseEntity<?> getCurrentGroup (@AuthenticationPrincipal Jwt jwt){
        Group group = groupService.getCurrentGroup(jwt.getSubject());

        ApiResponse<Group> apiResponse = ApiResponse.<Group>builder()
                .message("Get current user successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(group)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Create group for user successfully: ")
                .payload(group)
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/user/{userId}")
    public ResponseEntity<ApiResponse<Group>> AddUserToGroup(@PathVariable("groupId") UUID groupId, @PathVariable("userId") UUID userId) {
        Group group = groupService.AddUserToGroup(groupId, userId);
        if (group == null) {
            ApiResponse<Group> response = ApiResponse.<Group>builder()
                    .message("Create group for user not successfully: ")
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .timestamp(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }else {
            ApiResponse<Group> response = ApiResponse.<Group>builder()
                    .message("Create group for user successfully: ")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .payload(group)
                    .timestamp(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }
}
