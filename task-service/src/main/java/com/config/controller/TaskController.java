package com.config.controller;

import com.config.entity.request.TaskRequest;
import com.config.response.ApiResponse;
import com.config.response.TaskResponse;
import com.config.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
@SecurityRequirement(name = "mini-project-2")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Add new task")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .message("A new task has been created successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(taskService.addTask(taskRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "taskName") String sortBy,
            @RequestParam Sort.Direction sortDirection
    ) {
        ApiResponse<List<TaskResponse>> response = ApiResponse.<List<TaskResponse>>builder()
                .message("Get all tasks successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(taskService.getAllTasks(pageNo, pageSize, sortBy, sortDirection))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long taskId) {
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .message("Get task successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(taskService.getTaskById(taskId))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update task by id")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest) {
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .message("Update task successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(taskService.updateTaskById(taskId, taskRequest))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task by id")
    public ResponseEntity<ApiResponse<TaskResponse>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .message("Delete task successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
