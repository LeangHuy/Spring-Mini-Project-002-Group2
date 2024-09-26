package com.config.service;

import com.config.entity.request.TaskRequest;
import com.config.response.TaskResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskService {
    TaskResponse addTask(TaskRequest taskRequest);
    List<TaskResponse> getAllTasks(int pageNo, int pageSize, String sortBy, Sort.Direction sortDirection);
    TaskResponse getTaskById(Long taskId);
    TaskResponse updateTaskById(Long taskId,TaskRequest taskRequest);
    void deleteTaskById(Long taskId);
}
