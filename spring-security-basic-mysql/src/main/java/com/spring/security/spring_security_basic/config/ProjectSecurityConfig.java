package com.spring.security.spring_security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

    @Bean
    public UserDetailsService userDetailsService(DataSource source) {
       return new JdbcUserDetailsManager(source);
    }

    /*
       @Description: The below bean we have created to tell spring that we are using
       password encoder but the password encoder we are using will save the password
       in Plain text.
       @Note: This is for Non-Prod Env.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
