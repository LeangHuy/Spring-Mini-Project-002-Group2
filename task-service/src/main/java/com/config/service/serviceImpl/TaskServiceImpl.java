package com.config.service.serviceImpl;

import com.config.client.KeycloakFeignClient;
import com.config.entity.request.TaskRequest;
import com.config.repository.TaskRepository;
import com.config.response.ApiResponse;
import com.config.response.GroupResponse;
import com.config.response.TaskResponse;
import com.config.response.UserResponse;
import com.config.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final KeycloakFeignClient keycloakClient;

    public TaskServiceImpl(TaskRepository taskRepository, KeycloakFeignClient keycloakClient) {
        this.taskRepository = taskRepository;
        this.keycloakClient = keycloakClient;
    }

    @Override
    public TaskResponse addTask(TaskRequest taskRequest) {
        ResponseEntity<ApiResponse<GroupResponse>> getGroupById = keycloakClient.getGroup(UUID.fromString(taskRequest.groupId()));
        ResponseEntity<ApiResponse<UserResponse>> getCreatedBy = keycloakClient.getUserById(taskRequest.createdBy());
        ResponseEntity<ApiResponse<UserResponse>> getAssignTo = keycloakClient.getUserById(taskRequest.createdBy());
        System.out.println("Group "+getGroupById);
        GroupResponse groupResponse = null;
        if (getGroupById.getStatusCode().is2xxSuccessful()) {
            ApiResponse<GroupResponse> groupApiResponse = getGroupById.getBody();
            assert groupApiResponse != null;
            groupResponse = groupApiResponse.getPayload();
        }
        UserResponse createdByResponse = null;
        if (getCreatedBy.getStatusCode().is2xxSuccessful()) {
            ApiResponse<UserResponse> createdByApiResponse = getCreatedBy.getBody();
            assert createdByApiResponse != null;
            createdByResponse = createdByApiResponse.getPayload();
        }
        UserResponse assignToResponse = null;
        if (getAssignTo.getStatusCode().is2xxSuccessful()) {
            ApiResponse<UserResponse> assignToApiResponse = getAssignTo.getBody();
            assert assignToApiResponse != null;
            assignToResponse = assignToApiResponse.getPayload();
        }
        return taskRepository.save(taskRequest.toEntity()).toResponse(createdByResponse,assignToResponse,groupResponse);
    }
}
