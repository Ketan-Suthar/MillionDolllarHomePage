package com.mdhp.error;

import com.mdhp.exceptions.AlreadyBought;
import com.mdhp.exceptions.BadRequest;
import com.mdhp.pojo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// This handles exceptions for all @RestController classes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions (e.g., any exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyBought.class)
    public ResponseEntity<Object> handlePixelsAlreadyBought(AlreadyBought ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), AlreadyBought.MESSAGE), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Object> handleBadRequest(BadRequest ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getDefaultMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}


