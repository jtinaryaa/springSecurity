package com.spring.security.spring_security_basic.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.spring_security_basic.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author Jatin Arora
 * <p>This class we have created to show that if any 403 forbidden error or any error
 * in Authorization flow comes
 * then it will go to the {@link AccessDeniedHandler} .
 * </p>
 * <p>So We have created our own Class for AccessDeniedHandler.</p>
 */
public class EmployeeAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String errorMessage = (accessDeniedException!=null && accessDeniedException.getMessage()!=null ?
                accessDeniedException.getMessage() : "Access Denied !");
        assert accessDeniedException != null;
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .httpCode(HttpStatus.FORBIDDEN.value())
                .errorMessage(errorMessage)
                .errorDescription(ExceptionUtils.getStackTrace(accessDeniedException))
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiErrorResponse));
    }
}
