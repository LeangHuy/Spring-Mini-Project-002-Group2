package com.config.controller;

import com.config.model.request.GroupRequest;
import com.config.response.*;
import com.config.service.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity<ApiResponse<UserGroupResponse>> AddUserToGroup(@PathVariable("groupId") UUID groupId, @PathVariable("userId") UUID userId) {
        ApiResponse<UserGroupResponse> response = ApiResponse.<UserGroupResponse>builder()
                .message("Add user to group successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.AddUserToGroup(groupId, userId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable("groupId") UUID groupId) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Get group successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.getGroupByID(groupId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroup() {
        ApiResponse<List<GroupResponse>> response = ApiResponse.<List<GroupResponse>>builder()
                .message("Get All group successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.getAllGroup())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupID}")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(@PathVariable("groupID") UUID groupId, @RequestBody GroupRequest groupRequest) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Update group successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.updateGroupByGroupID(groupId,groupRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> DeleteGroup(@PathVariable("groupId") UUID groupId) {
        ApiResponse<GroupResponse> response = ApiResponse.<GroupResponse>builder()
                .message("Deleted group "+groupId+" successfully: ")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(groupService.DeletedGroupByGroupID(groupId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<ApiResponse<GroupUserResponse>> getUsers(@PathVariable("groupId") UUID groupId) {
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
