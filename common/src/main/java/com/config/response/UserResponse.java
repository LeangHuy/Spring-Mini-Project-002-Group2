package com.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private UUID userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

}
