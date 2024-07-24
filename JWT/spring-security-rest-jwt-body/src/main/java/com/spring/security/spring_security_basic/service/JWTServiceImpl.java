package com.spring.security.spring_security_basic.service;

import com.spring.security.spring_security_basic.contants.Constants;
import com.spring.security.spring_security_basic.dto.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl {

    public ResponseEntity<LoginResponse> generateJwtToken(Authentication authentication) {
        Assert.notNull(authentication,"Authentication object is null");
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
        LoginResponse response = new LoginResponse(HttpStatus.OK,jwt,System.currentTimeMillis()+300000);
        return ResponseEntity.ok(response);
    }
}
