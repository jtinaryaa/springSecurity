package com.spring.security.spring_security_basic.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private HttpStatus httpStatus;
    private Throwable errorCause;
    private String errorMessage;
    private String errorDescription;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "httpStatus=" + httpStatus +
                ", errorCause=" + errorCause +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
