package com.spring.security.spring_security_basic.controller;

import com.spring.security.spring_security_basic.dto.LoginRequest;
import com.spring.security.spring_security_basic.dto.LoginResponse;
import com.spring.security.spring_security_basic.entity.ApiResponse;
import com.spring.security.spring_security_basic.entity.Employee;
import com.spring.security.spring_security_basic.service.JWTServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.security.spring_security_basic.service.EmployeeService;

@RestController
public class LoginAndRegistrationController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JWTServiceImpl jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * This Below mapping we have created to just get the JWT Token in the Response Body.
     */
    @PostMapping("/jwt-token")
    public ResponseEntity<?> getJwtToken(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username()
                ,loginRequest.password());
        Authentication authResponse = authenticationManager.authenticate(authentication);
        if(authResponse.isAuthenticated()) {
            return jwtService.generateJwtToken(authResponse);
        } else {
            return ResponseEntity.ok(ApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                            .errorMessage("Bad Credentials !")
                            .errorMessage("Username and passwords are incorrect !")
                    .build());
        }
    }

    @PostMapping("/welcome")
    public ResponseEntity<?> welcome() {
        return ResponseEntity.ok("Welcome to the User !");
    }

    /**
     *
     * @param employee
     * The Below Request Mapping We have created to register the user and this is not authenticated.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Employee employee) {
        return employeeService.register(employee);
    }

}
