package com.spring.security.spring_security_basic.dto;

import org.springframework.http.HttpStatus;

public record LoginResponse(HttpStatus status,String token,Long expiry) {
}
