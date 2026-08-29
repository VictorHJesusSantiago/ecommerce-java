package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
