package com.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private HttpStatus status;
    private Integer statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private LocalDateTime timestamp;

}
