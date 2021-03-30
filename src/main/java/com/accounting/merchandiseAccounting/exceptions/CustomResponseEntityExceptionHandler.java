package com.accounting.merchandiseAccounting.exceptions;

import com.accounting.merchandiseAccounting.repository.repositoryImpl.EmployeeRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @ExceptionHandler(IdNotFoundException.class)
    public final ResponseEntity<Object> idNotFoundExceptionHandler(IdNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestExceptionHandler.class)
    public ResponseEntity<Object> handleAllUncaughtException(BadRequestExceptionHandler exception, WebRequest request) {
        exception.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
