package com.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String createdAt;
    private String lastModifiedAt;

}
