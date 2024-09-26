package com.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClientResponse {
    private UserResponse createdBy;
    private UserResponse assignedTo;
    private GroupResponse group;
}
