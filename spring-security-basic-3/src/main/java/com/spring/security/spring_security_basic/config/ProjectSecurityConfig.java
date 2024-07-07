package com.spring.security.spring_security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/*
   @Description: This is the default SecurityFilterChain Object defined in SecurityFilterChainConfiguration.java
                 @Bean
                 @Order(SecurityProperties.BASIC_AUTH_ORDER)
                 SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                 http.authorizeHttpRequests().anyRequest().authenticated();
                 http.formLogin();
                 http.httpBasic();
                 return http.build();
               }

        So we have created our this bean in our class and modify the code so that spring will use our below code not the
        default SecurityFilterChain Code mentioned above.
   */
/*
@Author:Jatin Arora (EAROJAT)
*/
@Configuration
public class ProjectSecurityConfig {

    /*
       @Description: The below object of SecurityFilterChain is used to permit /welcome request without Authentication
       and asked the authetication credentials  for /hello request.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/hello").authenticated()
                .requestMatchers("/welcome").permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    /*
    #Approach 1
    @Description: We have InMemoryUserDetailsManager class in spring where we can define the multiple
    users with authorities. In this method we are using the defaultPasswordEncoder for password.
    */
   /* @Bean
    InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin").password("admin123").authorities("ADMIN").build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user").password("user123").authorities("USER").build();
        inMemoryUserDetailsManager.createUser(admin);
        inMemoryUserDetailsManager.createUser(user);
        return inMemoryUserDetailsManager;
    }*/

    /*
    #Approach 2
    @Description: We have InMemoryUserDetailsManager class in spring where we can define the multiple
    users with authorities. In this method we are not using the DefaultPassword Encoder, for that
    we have create the Bean for Password Encoder below this method.
    @Note: Both the below users can access the /hello request mapping because we haven't set
    any specific user/authority to any user for /hello request mapping.
    */
    @Bean
    InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User
                .withUsername("admin").password("admin123").authorities("ADMIN").build();
        UserDetails user = User
                .withUsername("user").password("user123").authorities("USER").build();
        return new InMemoryUserDetailsManager(admin,user);
    }

    /*
    @Description: This method is used to encode the password and this method is not for production
    purpose (because this password Encoder use password as plain text) as this Password Encoder
    is Deprecated by Spring.
    */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
