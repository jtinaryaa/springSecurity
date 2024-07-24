package com.spring.security.spring_security_basic.repository;

import com.spring.security.spring_security_basic.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByEmail(String email);
}
