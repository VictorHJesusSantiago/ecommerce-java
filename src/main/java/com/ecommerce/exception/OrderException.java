package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class OrderException extends RuntimeException {

    private final String errorCode;

    public OrderException(String message) {
        super(message);
        this.errorCode = "ORDER_ERROR";
    }

    public OrderException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
