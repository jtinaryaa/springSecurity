package com.spring.security.spring_security_basic.service;

import com.spring.security.spring_security_basic.repository.EmployeeRepository;
import com.spring.security.spring_security_basic.entity.ApiResponse;
import com.spring.security.spring_security_basic.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email=null;
        String password = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Employee> employees = employeeRepository.findByEmail(username);
        if(employees.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with email : "+username);
        }

        email = employees.get(0).getEmail();
        password = employees.get(0).getPassword();
        authorities.add(new SimpleGrantedAuthority(employees.get(0).getRole()));

        return new User(email,password,authorities);
    }

    public ResponseEntity<?> register(Employee employee) {
        List<Employee> findEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(findEmployee.isEmpty()) {
            employee = employeeRepository.save(usePasswordEncoder(employee));
            return new ResponseEntity<>(employee,HttpStatus.CREATED);
        }
        ApiResponse response = ApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(employee.getEmail()+" already exists")
                .errorCode("USER EXISTS !")
                .build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public Employee usePasswordEncoder(Employee employee) {
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        return employee;
    }
}
