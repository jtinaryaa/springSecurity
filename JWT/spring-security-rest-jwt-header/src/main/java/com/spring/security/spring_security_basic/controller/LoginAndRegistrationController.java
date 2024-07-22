package com.spring.security.spring_security_basic.controller;

import com.spring.security.spring_security_basic.entity.Employee;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.security.spring_security_basic.service.EmployeeService;

@RestController
public class LoginAndRegistrationController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to Spring Security !";
    }

    /**
     * This Below mapping we have created to just get the JWT Token in the Response Header.
     */
    @PostMapping("/jwt")
    public String getJwtToken() {
        return "Return JWT Token in Header";
    }

    @PostMapping("/welcome")
    public String welcome() {
        return "Welcome User !";
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
