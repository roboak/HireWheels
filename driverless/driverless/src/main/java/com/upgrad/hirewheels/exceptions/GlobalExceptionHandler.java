package com.upgrad.hirewheels.exceptions;


import com.upgrad.hirewheels.responsemodel.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.sql.SQLException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
public class GlobalExceptionHandler extends Exception{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRunTimeException(RuntimeException ex, WebRequest request) {
        return error(BAD_REQUEST, ex , request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return error(NOT_FOUND, ex, request);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<Object> handleUserNotFoundException(APIException ex, WebRequest request) {
        return error(BAD_REQUEST, ex, request);
    }

    @ExceptionHandler({SQLException.class, NullPointerException.class})
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        logger.error("Exception : ", ex);
        return error(BAD_REQUEST, ex, request);
    }

    private ResponseEntity error(HttpStatus status, Exception ex, WebRequest request) {
        logger.error("Exception : ", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), status.value());
        return new ResponseEntity(exceptionResponse, status);
    }

}
