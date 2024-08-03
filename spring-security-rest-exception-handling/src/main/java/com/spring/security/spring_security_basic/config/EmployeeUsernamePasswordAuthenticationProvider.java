package com.spring.security.spring_security_basic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Jatin Arora
 * <p>Check Spring Class {@link AuthenticationProvider}</p>
 * <p>Check Spring Class {@link UsernamePasswordAuthenticationToken}</p>
 *
 *
 *
 * <p>1. This below class we have created to show that we can also create our own Authentication Provider.</p>
 *
 * <p>2. So Now thw question is why we need our own Authentication Provider when we have spring default
 * Authentication Provided which is {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}
 * and it is working perfectly fine.</p>
 *
 * <p>3. So it is very small application which we are creating so for the big enterprises applications
 * there may be the requirement that user can login by its own Username/password and it can also
 * login via OAuth2/Google SSO and or many other ways so for that we need our own
 * Authentication Provider.</p>
 *
 * <p>4. {@link com.spring.security.spring_security_basic.service.EmployeeService} Class we have created
 * where we are implementing the method of UserDetailsService so that class is the end part of
 * AuthenticationProvider.<p/>
 *
 * <p>5. We have lot of Authenitcation Providers in Spring and All Authenitcation Provider have
 * two parts. One is {@link org.springframework.security.provisioning.UserDetailsManager} or
 * {@link UserDetailsService} and second part is
 * {@link PasswordEncoder}</p>
 *
 * <p>6. As you can see below in this class we have two methods which we need to implement as part
 * of AuthenticationProvider class. One is authenticate() method in which we have to write our
 * code to authenticate or we can also call UserDetailsService to authenticate the username and
 * Second method is support() , via this method we will tel spring that which AuthenticationToken
 * we are using in SPring. So As of now we are using {@link  UsernamePasswordAuthenticationToken}
 *  to Authenticaion. We also have many AuthenticationToken which we can use.</p>
 *
 * <p>7. Flow of Authentication in this case</p>
 * <p>Filters --> AuthenticationManager --> ProviderManager --> AuthenticationProvider --> UserDetailsService/Manager and Password Encoders</p>
 */
@Component
@RequiredArgsConstructor
public class EmployeeUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    /**
     * In the below spring will auto insert the EmployeeService class object.
     * Because EmployeeService class is annotated with @Service so spring will create the object
     * of EmployeeService, and we can insert it in its implementation Object without using @Autowired
     * annotation because we are using @RequiredArgsConstructor annotation from lombok dependency and
     * it will create the Argument Constructor for this.
     */
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication,"Authentication must not be null");
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserDetails userDetails =userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(pwd,userDetails.getPassword())) {
           return new UsernamePasswordAuthenticationToken(username,pwd,userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Password is Invalid !");
        }
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
