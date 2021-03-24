package com.accounting.merchandiseAccounting.exceptions.customExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.management.RuntimeErrorException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomInternalError extends RuntimeErrorException {
    public CustomInternalError(Error e) {
        super(e);
    }
}
