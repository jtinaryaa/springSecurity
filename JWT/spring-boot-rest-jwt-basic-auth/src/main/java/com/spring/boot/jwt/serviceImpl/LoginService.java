package com.spring.boot.jwt.serviceImpl;

import com.spring.boot.jwt.modal.Employee;
import com.spring.boot.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username,"Username can not be null !");
        List<Employee> employeeList = employeeRepository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(employeeList.isEmpty()) {
            throw new UsernameNotFoundException("Username is not correct !");
        }
        authorities.add(new SimpleGrantedAuthority(employeeList.get(0).getRole()));
        return new User(employeeList.get(0).getUsername(),employeeList.get(0).getPassword(),authorities);
    }
}
