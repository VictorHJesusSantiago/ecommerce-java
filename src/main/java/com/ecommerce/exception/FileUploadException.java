package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends RuntimeException {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
