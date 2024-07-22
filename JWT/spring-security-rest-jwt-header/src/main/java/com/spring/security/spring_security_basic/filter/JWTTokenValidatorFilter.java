package com.spring.security.spring_security_basic.filter;

import com.spring.security.spring_security_basic.contants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * This class as a Filter we have created to validate the JWT Token.
 */
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //First we have to get the jwtToken Coming in the request as header.
        String jwtToken = request.getHeader(Constants.JWT_HEADER);
        if(!jwtToken.isEmpty()) {
            try {
                //Create a secret key verify the coming token with the same secret key.
                SecretKey key = Keys.hmacShaKeyFor(Constants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                                .verifyWith(key)
                                  .build()
                                    .parseSignedClaims(jwtToken)
                                     .getPayload();
                /*
                 * Get the username and authorities which we set during token generation and
                 * create A Authentication Object and set into the SecurityContextHolder.
                 * We are using UsernamePasswordAuthenticationToken because when Spring create the
                 * object of UsernamePasswordAuthenticationToken that time it set the setAuthenticated(true)
                 * that means it will not authenticate the user with username and password again.
                 *
                 * Refer the UsernamePasswordAuthenticationToken class constructor.
                 *
                 */
                String username = String.valueOf(claims.get(Constants.JWT_CLAIMS_USERNAME));
                String authorities = String.valueOf(claims.get(Constants.JWT_CLAIMS_AUTHORITIES));
                Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token Received ! ");
            }
        }
        filterChain.doFilter(request,response);
        System.out.println("JWTTokenValidatorFilter :: doFilterInternal()");
    }

    /**
     *
     * @param request current HTTP request
     * @return
     * @throws ServletException
     * This below method is used to configure that the filter we have created should be called
     * or not. If from the below filter we are returning false then this filter will call and if
     * we are returning true that means this filter will not call.

     * So the requirement is that we have to validate the JWT Token in all the authenticated requests
     * but we have to make sure that this validation will happen only after we receive the token
     * from /jwt request mapping.

     * So how we configure this, if /jwt request comes then this JWT Validator filter will not call
     * otherwise this
     * filter will call.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("JWTTokenValidatorFilter :: shouldNotFilter()");
        return request.getServletPath().equals("/jwt");
    }
}
