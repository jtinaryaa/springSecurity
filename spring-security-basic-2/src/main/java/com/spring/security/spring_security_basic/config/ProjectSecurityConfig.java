package com.spring.security.spring_security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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

    /*@Description: The below object of SecurityFilterChain is used to permit all the requests
    * means out Spring Application will permit all the request without authentication */
    /*@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }*/

    /*@Description: The below object of SecurityFilterChain is used to Deny all the requests
                    means all the request will not work means our Spring application will deny
                    all the request to identify.
                    @Note: Login Page will come but after putting the credentials it will not show
                          anything.
    */
   /* @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .anyRequest().denyAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }*/



}
