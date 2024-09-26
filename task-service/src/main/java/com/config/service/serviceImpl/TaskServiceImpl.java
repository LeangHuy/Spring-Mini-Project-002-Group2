package com.config.service.serviceImpl;

import com.config.client.KeycloakFeignClient;
import com.config.entity.entity.Task;
import com.config.entity.request.TaskRequest;
import com.config.exception.NotFoundException;
import com.config.repository.TaskRepository;
import com.config.response.*;
import com.config.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        ClientResponse clientResponse = clientResponse(UUID.fromString(taskRequest.groupId()),taskRequest.createdBy(),taskRequest.assignedTo());
        return taskRepository.save(taskRequest.toEntity()).toResponse(clientResponse);
    }

    @Override
    public List<TaskResponse> getAllTasks(int pageNo, int pageSize, String sortBy, Sort.Direction sortDirection) {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        TaskResponse getTaskById;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        List<Task> getAllTask = taskRepository.findAll(pageable).getContent();
        for (Task task : getAllTask){
            getTaskById = getTaskById(task.getId());
            taskResponseList.add(getTaskById);
        }
        return taskResponseList;
    }

    @Override
    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found."));
        ClientResponse clientResponse = clientResponse(UUID.fromString(task.getGroupId()),task.getCreatedBy(),task.getAssignedTo());
        return task.toResponse(clientResponse);
    }

    @Override
    public TaskResponse updateTaskById(Long taskId, TaskRequest taskRequest) {
        TaskResponse taskResponse = getTaskById(taskId);
        taskRepository.save(taskRequest.toEntity(taskId,taskResponse.getCreatedDate()));
        return getTaskById(taskId);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found."));
        taskRepository.delete(task);
    }

    public ClientResponse clientResponse(UUID groupId, String createdId, String assignedId){
        ResponseEntity<ApiResponse<GroupResponse>> getGroupById = keycloakClient.getGroup(groupId);
        ResponseEntity<ApiResponse<UserResponse>> getCreatedBy = keycloakClient.getUserById(createdId);
        ResponseEntity<ApiResponse<UserResponse>> getAssignTo = keycloakClient.getUserById(assignedId);
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
        return new ClientResponse(createdByResponse, assignToResponse, groupResponse);
    }
}
