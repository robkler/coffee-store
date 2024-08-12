package com.coffee.store.util;

import com.coffee.store.model.execption.AddCoffeeException;
import com.coffee.store.model.execption.CoffeeAlreadyExist;
import com.coffee.store.model.execption.CoffeeNotFoundException;
import com.coffee.store.model.execption.OrderNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e) {
        return handleException(e ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CoffeeAlreadyExist.class)
    public ResponseEntity<Map<String, String>> handleCoffeeNotFoundException(CoffeeAlreadyExist ex) {
        return handleException(ex,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCoffeeNotFoundException(CoffeeNotFoundException ex) {
        return handleException(ex,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex) {
        return handleException(ex,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddCoffeeException.class)
    public ResponseEntity<Map<String, String>> handleAddCoffeeFoundException(AddCoffeeException ex) {
        return handleException(ex,HttpStatus.BAD_REQUEST);
    }

    private <T extends RuntimeException> ResponseEntity<Map<String, String>> handleException(T ex, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
}