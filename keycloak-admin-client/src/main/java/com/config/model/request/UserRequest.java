package com.config.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRequest {
    @NotBlank
    @NonNull
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers"
    )
    private String password;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @NonNull
    private String firstName;
    @NotBlank
    @NonNull
    private String lastName;
}
