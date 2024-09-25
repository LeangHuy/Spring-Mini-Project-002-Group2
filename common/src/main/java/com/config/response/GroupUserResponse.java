package com.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupUserResponse {
    GroupResponse group;
    List<UserResponse> users;

    public void setUser(List<UserResponse> usersList) {
        this.users = usersList;
    }
}
