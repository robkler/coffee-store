package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCoffeeNotFoundException(CoffeeNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}