package com.spring.boot.jwt.serviceImpl;

import com.spring.boot.jwt.Constants;
import com.spring.boot.jwt.modal.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService implements Constants {

    public ResponseEntity<LoginResponse> getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .issuer(JWT_ISSUER).subject(JWT_ISSUER_SUBJECT)
                .claim(JWT_CLAIMS_USERNAME,authentication.getName())
                //We are getting all the authorities value in String format separated by comma.
                .claim(JWT_CLAIMS_AUTHORITIES,authentication.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority
                ).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+300000))
                .signWith(key)
                .compact();
        return ResponseEntity.ok(LoginResponse.builder().id_token(jwt).jwt_token(jwt).expiryTime(System.currentTimeMillis()+300000).build());
    }
}
