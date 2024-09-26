package com.config.entity.request;

import com.config.entity.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
         @NotBlank @NotNull String taskName,
         @NotBlank @NotNull String description,
         @NotBlank @NotNull String createdBy,
         @NotBlank @NotNull String assignedTo,
         @NotBlank @NotNull String groupId
) {
    public Task toEntity() {
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();
        return new Task(null, this.taskName, this.description, this.createdBy, this.assignedTo, this.groupId, createdDate, lastModifiedDate);
    }
    public Task toEntity(Long taskId,LocalDateTime createdDate ) {
        LocalDateTime lastModifiedDate = LocalDateTime.now();
        return new Task(taskId, this.taskName, this.description, this.createdBy, this.assignedTo, this.groupId, createdDate, lastModifiedDate);
    }
}
