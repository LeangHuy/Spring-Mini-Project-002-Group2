package com.config.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserGroupResponse {
    private UserResponse user;
    private GroupResponse group;
}
