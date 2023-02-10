package com.cinema.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleElementNotFoundException(ElementNotFoundException ex) {
        return createResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleElementFoundException(ElementFoundException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleElementNotModifiedException(ElementNotModifiedException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleArgumentNotFoundException(ArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        errorResponse.setValidationErrors(ex.getViolations());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleProcessingException(ProcessingException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(HttpStatus status, String exceptionMessage) {
        ErrorResponse errorResponse = new ErrorResponse(exceptionMessage);
        return new ResponseEntity<>(errorResponse, status);
    }


    // alternative for ArgumentNotValidException
    // exception MethodArgumentNotValidException is thrown if @Valid is used in Controller with @RequestBody
    // restriction annotations would have to be set in dto classes

//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)  {
//        ErrorResponse errorResponse = new ErrorResponse("Not valid data provided");
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errorResponse.getValidationErrors().put(fieldName, message);
//        });
//        return new ResponseEntity<>(errorResponse, status);
//    }

}
