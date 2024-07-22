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
    private String message;
    private String errorCode;
    private Throwable errorDescription;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDescription=" + errorDescription +
                '}';
    }
}
