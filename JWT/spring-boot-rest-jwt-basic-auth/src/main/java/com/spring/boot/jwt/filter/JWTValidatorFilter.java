package com.spring.boot.jwt.filter;

import com.spring.boot.jwt.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JWTValidatorFilter extends OncePerRequestFilter {
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        List<String> requestMatcher = new ArrayList<>();
        requestMatcher.add("/register");
        requestMatcher.add("/hello");
        requestMatcher.add("/getToken");
        return requestMatcher.contains(servletPath);
    }
}
