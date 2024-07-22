package com.spring.security.spring_security_basic.filter;

import com.spring.security.spring_security_basic.contants.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * This class as a Filter we have created to generate the JWT Token.
 */
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    /**
     *
     * @param request - by which we can get any parameter from the request
     * @param response - by which we can set any parameter in the response
     * @param filterChain - by which we can call the next filter whatever is available
     * @throws ServletException
     * @throws IOException
     *
     * Here in the below method we have to write the code to generate the token. So below are the
     * Steps which we have to follow to Generate the Token.

     * 1. First I have to read the current authentication details, that we can do via SecurityContextHolder.
     *    SecurityContextHolder is a Spring class which is having all the details related to authentication
     *    like username, authorities and various methods by which we can get all the user details.
     *    The Authentication object we are getting from SecurityContextHolder, if Authentication
     *    Object is not null that means Authentication is successfully.

     *    {@code
     *      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     *    }
     *

     * 2. To generate the JWT Token we have to maintain a secret key somewhere. Currently, we are maintaining
     *    it inside our code but for production use we must maintain it inside our Environment Variable
     *    or somewhere in the secret vault.
     *    This key we have to make our SecretKey, so we have to use the {@link Keys} class
     *    jjwt library and put it inside the {@link SecretKey} class of javax.crypto via below code.

     *      {@code
     *          SecretKey key = Keys.hmacShaKeyFor(Constants.JWT_SECRET_KEY.getBytes());
     *      }
     *
     * 3. Now after creating our key as Secret Key we have to generate the Token using {@link io.jsonwebtoken.Jwts}
     *    class, where we have to set many properties like
     *    --> issuer (name of which organization is issuing the token)
     *    --> subject of issuer (token is all about)
     *    --> claim (where we
     *        can send the username, authorities and other details by which we can verify the toke for
     *        particular user.)
     *    -->issuedAt
     *    --> expiration Date
     *    --> sign with secret key.
     *
     * 4. Now we need this token in String format for that we need to call the compact() method of
     *    JWTs Builder.
     * 5. Now we will send this token as response header.
     *    Note : Depending on the requirement we can send this token as response of any request mapping.
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTTokenGeneratorFilter :: doFilterInternal()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null!= authentication) {
            SecretKey key = Keys.hmacShaKeyFor(Constants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer(Constants.JWT_ISSUER).subject(Constants.JWT_ISSUER_SUBJECT)
                    .claim(Constants.JWT_CLAIMS_USERNAME,authentication.getName())
                    //We are getting all the authorities value in String format separated by comma.
                    .claim(Constants.JWT_CLAIMS_AUTHORITIES,authentication.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority
                    ).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis()+300000))
                    .signWith(key)
                    .compact();
            response.setHeader(Constants.JWT_HEADER,jwt);

        }

        filterChain.doFilter(request,response);
    }

    /**
     *
     * @param request current HTTP request
     * @return
     * @throws ServletException
     * This below method is used to configure that the filter we have created should be called
     * or not. If from the below filter we are returning false then this filter will call and if
     * we are returning true that means this filter will not call.

     * So the requirement is when we call this /jwt request mapping then this filter should call and generate the
     * JWT token, so we have created a /jwt POST request mapping to get the JWT Token.

     * So how we configure this, if /jwt request comes then this filter will call otherwise this
     * filter will not call.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("JWTTokenGeneratorFilter :: shouldNotFilter()");
        boolean isFirstRequest = request.getServletPath().equals("/jwt");
        return !isFirstRequest;
    }
}
