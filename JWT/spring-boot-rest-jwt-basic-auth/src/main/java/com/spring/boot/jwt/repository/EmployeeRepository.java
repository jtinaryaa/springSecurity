package com.spring.boot.jwt.repository;

import com.spring.boot.jwt.modal.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public List<Employee> findByUsername(String username);
}
