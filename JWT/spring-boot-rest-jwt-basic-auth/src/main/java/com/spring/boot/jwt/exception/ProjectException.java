package com.spring.boot.jwt.exception;

import com.spring.boot.jwt.modal.ApiResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class ProjectException extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            errorMap.put("FIELD_NAME",((FieldError) error).getField());
            errorMap.put("MESSAGE",error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiResponse.builder()
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .errorMessage(errorMap.get("FIELD_NAME"))
                .errorDescription(errorMap.get("MESSAGE")).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptionsIfNotHandledExplicitly(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessage(e.getMessage())
                        .errorDescription(ExceptionUtils.getStackTrace(e.getCause())).build());
    }
}
