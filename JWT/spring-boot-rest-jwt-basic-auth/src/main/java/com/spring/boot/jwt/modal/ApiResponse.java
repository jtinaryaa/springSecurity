package com.spring.boot.jwt.modal;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private HttpStatus httpStatus;
    private String errorMessage;
    private String errorDescription;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "httpStatus=" + httpStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
