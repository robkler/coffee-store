package com.coffee.coffee_store.controllerAdvice;

import com.coffee.coffee_store.execption.AddCoffeeFoundException;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.execption.OrderNotFoundException;
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
        return handleNotFoundException(ex,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex) {
        return handleNotFoundException(ex,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddCoffeeFoundException.class)
    public ResponseEntity<Map<String, String>> handleAddCoffeeFoundException(AddCoffeeFoundException ex) {
        return handleNotFoundException(ex,HttpStatus.BAD_REQUEST);
    }

    private <T extends RuntimeException> ResponseEntity<Map<String, String>> handleNotFoundException(T ex,HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
}