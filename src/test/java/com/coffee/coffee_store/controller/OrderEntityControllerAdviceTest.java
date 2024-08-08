package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.controllerAdvice.OrderControllerAdvice;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderEntityControllerAdviceTest {

    @InjectMocks
    private OrderControllerAdvice orderControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleCoffeeNotFoundException_ReturnsBadRequest() {
        CoffeeNotFoundException exception = new CoffeeNotFoundException("Coffee not found");

        ResponseEntity<Map<String, String>> response = orderControllerAdvice.handleCoffeeNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Coffee not found", response.getBody().get("error"));
    }
}