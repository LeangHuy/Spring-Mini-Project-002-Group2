package com.config.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Group {
    private UUID groupId;
    private String name;

    public void setId(UUID groupId) {
        this.groupId = groupId;
    }
}
