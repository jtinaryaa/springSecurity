package com.spring.boot.jwt.controller;

import com.spring.boot.jwt.modal.ApiResponse;
import com.spring.boot.jwt.modal.Employee;
import com.spring.boot.jwt.modal.LoginResponse;
import com.spring.boot.jwt.serviceImpl.EmployeeService;
import com.spring.boot.jwt.serviceImpl.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/hello")
    public String hello() {
        return "Your Application is Working Fine !";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Employee employee) {
        return employeeService.register(employee);
    }

    @PostMapping("/welcome")
    public ResponseEntity<ApiResponse> welcome() {
        return ResponseEntity.ok(ApiResponse.builder().httpStatus(HttpStatus.OK).build());
    }

    @PostMapping("/getToken")
    public ResponseEntity<LoginResponse> accessLogin() {
       return jwtService.getJwtToken();

    }
}
