package com.config.model.entity;

import com.config.response.UserResponse;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String createdAt;
    private String lastModifiedAt;
}
