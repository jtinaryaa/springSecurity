package com.spring.security.spring_security_basic.config;

import com.spring.security.spring_security_basic.exception.CustomBasicAuthenticationEntryPoint;
import com.spring.security.spring_security_basic.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Jatin Arora
*/
@Configuration
public class EmployeeSecurityConfig {

    /*
       @Description: The below object of SecurityFilterChain is used to permit /welcome request without Authentication
       and asked the authentication credentials  for /hello request.

       We have added the two Filter one is responsible to GenerateJWTToken and other one is ValidateJWTToken
       So the business logic is JWT Generation Filter should call once the Spring Security Basic Authentication
       is done by BasicAuthenticationFilter and JWT Validator should call before the BasicAuthenticationFilter once the
       JWT Token is generated.

       On which condition these filters should call that also we have configured inside the Filter
       shouldNotFilter method.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/welcome").authenticated()
                .requestMatchers("/register","/jwt-token").permitAll()
                .and().formLogin()
                //As we using the Spring Basic Auth So whenever we are putting Bad credentials
                //then this AAuthentication Entry Point will handle it.
                .and().httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

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

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        EmployeeAuthenticationProvider employeeAuthenticationProvider = new EmployeeAuthenticationProvider(userDetailsService,passwordEncoder());
        ProviderManager providerManager = new ProviderManager(employeeAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

}
