package com.example.Admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Map<String,String>> handleException(InvalidDateException ex){
        Map<String,String> mp = new HashMap<>();
        mp.put("Mesaage",ex.getMessage());
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePlanEx(PlanNotFoundException ex){
        Map<String,String> mp = new HashMap<>();
        mp.put("Message",ex.getMessage());
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePlanEx(CategoryNotFoundException ex){
        Map<String,String> mp = new HashMap<>();
        mp.put("Message",ex.getMessage());
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }
}
