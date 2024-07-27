package com.spring.boot.jwt.serviceImpl;

import com.spring.boot.jwt.modal.ApiResponse;
import com.spring.boot.jwt.modal.Employee;
import com.spring.boot.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<?> register(Employee employee) {
        Assert.notNull(employee,"Employee can not be null !");
        List<Employee> isEmployeeExists = employeeRepository.findByUsername(employee.getUsername());
        if(isEmployeeExists.isEmpty()) {
            employee.setPassword(wrapPasswordWithEncoder(employee.getPassword()));
            return ResponseEntity.ok(employeeRepository.save(employee));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .errorMessage("USER EXISTS !")
                            .errorDescription(employee.getUsername()+" already exists !").build());
        }

    }

    private String wrapPasswordWithEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
