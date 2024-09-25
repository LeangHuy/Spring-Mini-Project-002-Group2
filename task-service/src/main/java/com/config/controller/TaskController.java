package com.config.controller;

import com.config.entity.request.TaskRequest;
import com.config.response.ApiResponse;
import com.config.response.TaskResponse;
import com.config.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
@SecurityRequirement(name = "mini-project-2")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Add new task")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@RequestBody TaskRequest taskRequest) {
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .message("A new task has been created successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(taskService.addTask(taskRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
