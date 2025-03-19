package com.example.football_organizer.exception;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException  {
  private final static Logger logger =LoggerFactory.getLogger(GlobalExceptionHandler.class);

  //Exception handle for requestBody(@NotNull)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + errors);
  }
  //Exception handle for requestParam and Email
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex){
    String errors = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath()+ ": "+violation.getMessage())
            .collect(Collectors.joining(", "));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: "+ errors);
  }

  //Handle Exception for runtime match not found
  @ExceptionHandler(RuntimeException.class)
  public  ResponseEntity<String> handleRuntimeException(RuntimeException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: "+ ex.getMessage());
  }

  @ExceptionHandler(MailException.class)
  public ResponseEntity<String> handleMailException(MailException ex){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email error: "+ ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex){
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: "+ex.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthentication(AuthenticationException ex){
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed: "+ex.getMessage());
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: "+ex.getMessage());
  }

}
