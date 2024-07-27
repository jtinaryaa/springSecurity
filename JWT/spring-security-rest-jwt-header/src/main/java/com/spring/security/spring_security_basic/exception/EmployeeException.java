package com.spring.security.spring_security_basic.exception;

import com.spring.security.spring_security_basic.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EmployeeException extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception exception) {
        ApiResponse error = ApiResponse.builder()
                .message(exception.getMessage())
                .errorCode(exception.getMessage())
                .errorDescription(exception.getCause())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
                String fieldName = ((FieldError)error).getField();
                String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName,errorMessage);
            });
        return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
    }
}
