package com.config.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserGroupResponse {
    private GroupResponse group;
    private UserResponse user;
}
