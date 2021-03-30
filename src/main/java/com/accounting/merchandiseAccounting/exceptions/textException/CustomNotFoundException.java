package com.accounting.merchandiseAccounting.exceptions.textException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class CustomNotFoundException extends RuntimeException{

    public CustomNotFoundException(String message) {
        super(message);
    }

    public CustomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomNotFoundException(Throwable cause) {
        super(cause);
    }
}
