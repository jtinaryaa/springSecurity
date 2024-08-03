package com.spring.security.spring_security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
@Author:Jatin Arora (EAROJAT)
*/
@Configuration
public class EmployeeSecurityConfig {

    /*
       @Description: The below object of SecurityFilterChain is used to permit /welcome request without Authentication
       and asked the authentication credentials  for /hello request.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/welcome").authenticated()
                .requestMatchers("/register","/hello").permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    /*
       @Description: The below bean we have created to tell spring that we are using
       password encoder BCryptPasswordEncoder because this password encoder uses the hashing Algorithm
       and hashing algorithm is the best technique to encode your password.
       @Note: This can be used for Production env. and we have Autowire this password encoder while
              saving the details to Database. See EmployeeService.java for implementation.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @MoreOnPasswordEncoder: We use three types of technique to encode the passwords.
    #1- Encoding :
        => Encoding is defined as the process of converting data from one form to another and has
           nothing to do with cryptography.
        => It involves no secret and completely reversible.
        => Encoding can't be used for securing data. Below are the various publicly available algorithm
           used for encoding.
    #2- Encryption :
        => Encryption is defined as the process of transforming data in such a way that guarantees
           confidentially.
        => To achieve confidentially, encryption requires the use of a secret which, in cryptography
           terms, call a 'key' .
        => Encryption can be reversible by using decryption with the help of the 'key' . As long as
           the 'key' is confidential, encryption can be considered as secured.
     #3- Hashing :
        => In Hashing data is converted into the hash value using some hashing function.
        => Data once hashed is non-reversible. One can not determine the original data from as hash value
           generated.
        => Given some arbitrary data along with the output of a hashing algorithm, one can verify whether
           the data matches the original input data without needing to see the original data.
        => Visit --> https://bcrypt-generator.com/ to check the BcryptPasswordEncoder hashing technique.
        => So how it works , when user enter his password then it will convert into the hash value and then
           this value will check with the DB value. If match then login successfully.

    */


}
