package com.spring.security.spring_security_basic.util;

import org.springframework.http.ResponseEntity;

public class ValidationUtility {

    public static boolean isBlankOrNull(String object) {
        return object == null || object.isBlank();
    }


}
