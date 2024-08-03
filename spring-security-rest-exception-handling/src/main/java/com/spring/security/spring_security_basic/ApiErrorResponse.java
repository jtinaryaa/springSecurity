package com.spring.security.spring_security_basic;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {

    private HttpStatus httpStatus;
    private int httpCode;
    private LocalDateTime currentTime;
    private String errorMessage;
    private String errorDescription;
}
