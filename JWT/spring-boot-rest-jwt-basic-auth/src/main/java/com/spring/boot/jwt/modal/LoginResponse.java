package com.spring.boot.jwt.modal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String id_token;
    private String jwt_token;
    private long expiryTime;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "id_token='" + id_token + '\'' +
                ", jwt_token='" + jwt_token + '\'' +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
