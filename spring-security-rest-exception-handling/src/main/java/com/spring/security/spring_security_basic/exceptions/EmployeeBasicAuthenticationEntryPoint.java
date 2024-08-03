package com.spring.security.spring_security_basic.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.spring_security_basic.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class EmployeeBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Commences an authentication scheme.
     * <p>
     * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
     * attribute named
     * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
     * with the requested target URL before calling this method.
     * <p>
     * Implementations should modify the headers on the <code>ServletResponse</code> as
     * necessary to commence the authentication process.
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = (authException!=null && authException.getMessage()!=null ?
                authException.getMessage() : "UnAuthorized");
        assert authException != null;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .httpCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(errorMessage)
                .errorDescription(ExceptionUtils.getStackTrace(authException))
               .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiErrorResponse));
    }
}
