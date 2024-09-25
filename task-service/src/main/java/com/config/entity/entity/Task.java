package com.config.entity.entity;

import com.config.response.GroupResponse;
import com.config.response.TaskResponse;
import com.config.response.UserResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    private String description;
    private String createdBy;
    private String assignedTo;
    private String groupId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


    public TaskResponse toResponse(UserResponse createdBy, UserResponse assignedTo, GroupResponse group) {
        return new TaskResponse(this.id, this.taskName, this.description, createdBy, assignedTo, group, this.createdDate, this.lastModifiedDate);
    }

}
