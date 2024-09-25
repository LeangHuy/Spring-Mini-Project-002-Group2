package com.config.controller;

import com.config.model.request.GroupRequest;
import com.config.response.*;
import com.config.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/group")
@SecurityRequirement(name = "mini-project-2")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "Add group to keycloak")
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        GroupResponse group = groupService.createGroup(groupRequest);
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Create group for user successfully")
                .payload(group)
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/user/{userId}")
    @Operation(summary = "Add user to group")
    public ResponseEntity<ApiResponse<UserGroupResponse>> AddUserToGroup(@PathVariable UUID groupId, @PathVariable UUID userId) {
        ApiResponse<UserGroupResponse> response = ApiResponse.<UserGroupResponse>builder()
                .message("Add user to group successfully ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.AddUserToGroup(groupId, userId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get group by group id")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable UUID groupId) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Get group successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.getGroupByID(groupId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all group")
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroup() {
        ApiResponse<List<GroupResponse>> response = ApiResponse.<List<GroupResponse>>builder()
                .message("Get All group successfully ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.getAllGroup())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupId}")
    @Operation(summary = "Update group by id")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(@PathVariable UUID groupId, @Valid @RequestBody GroupRequest groupRequest) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Update group successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.updateGroupByGroupID(groupId,groupRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group by group id")
    public ResponseEntity<ApiResponse<GroupResponse>> DeleteGroup(@PathVariable UUID groupId) {
        groupService.DeletedGroupByGroupID(groupId);
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Deleted group "+groupId+" successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}/users")
    @Operation(summary = "Get user in group by group id")
    public ResponseEntity<ApiResponse<GroupUserResponse>> getUsers(@PathVariable String groupId) {
        ApiResponse<GroupUserResponse> response = ApiResponse.<GroupUserResponse>builder()
                .message("Get All group user successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.getAllGroupUser(groupId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
