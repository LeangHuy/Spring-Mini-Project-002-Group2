package com.config.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TaskResponse {
    private Long taskId;
    private String taskName;
    private String description;
    private UserResponse createdBy;
    private UserResponse assignedTo;
    private GroupResponse group;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
