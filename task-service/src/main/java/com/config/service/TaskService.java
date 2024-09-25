package com.config.service;

import com.config.entity.request.TaskRequest;
import com.config.response.TaskResponse;

public interface TaskService {
    TaskResponse addTask(TaskRequest taskRequest);
}
