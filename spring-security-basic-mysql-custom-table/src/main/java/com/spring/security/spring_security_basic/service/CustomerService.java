package com.spring.security.spring_security_basic.service;

import com.spring.security.spring_security_basic.entity.Customer;
import com.spring.security.spring_security_basic.repository.CustomerRepository;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    /*
    @Description: This is the method which we override from UserDetailsService -> JdbcDaoImpl and
    provided our default implementation.
    This method in JdbcDaoImpl returns the UserDetailsService, so we are also returning the User
    class which implements the UserDetailsService interface.
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email=null;
        String password=null;
        List<GrantedAuthority> authorities = new ArrayList<>();

        List<Customer> customers = customerRepository.findByEmail(username);
        if(customers.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email id : "+username);
        }
        email = customers.get(0).getEmail();
        password = customers.get(0).getPassword();
        authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));

        return new User(email,password,authorities);
    }
}
