package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class CouponException extends RuntimeException {

    public CouponException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
