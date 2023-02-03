package com.cinema.cinema.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleElementFoundException(ElementFoundException foundException, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return createResponseEntity(request.getRequestURI(), status, foundException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleElementNotFoundException(ElementNotFoundException notFoundException, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return createResponseEntity(request.getRequestURI(), status, notFoundException.getMessage());
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(String request, HttpStatus status, String exceptionMessage) {
        ErrorResponse errorResponse = new ErrorResponse(request, status.value(), status, exceptionMessage);
        return new ResponseEntity<>(errorResponse, status);
    }

}
