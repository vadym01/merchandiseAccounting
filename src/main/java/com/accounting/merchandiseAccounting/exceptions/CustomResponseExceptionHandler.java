package com.accounting.merchandiseAccounting.exceptions;

import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.BadRequestException;
import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.BadRequestExceptionResponse;
import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(IdNotFoundException exception, WebRequest request){
        IdNotFoundExceptionResponse exceptionResponse = new IdNotFoundExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> badRequestException(BadRequestException exception, WebRequest request){
        BadRequestExceptionResponse exceptionResponse = new BadRequestExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
