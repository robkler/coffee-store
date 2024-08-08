package com.coffee.coffee_store.controllerAdvice;

import com.coffee.coffee_store.execption.AddCoffeeException;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.execption.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerAdviceTest {

    @InjectMocks
    private OrderControllerAdvice orderControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleCoffeeNotFoundException_ReturnsNotFound() {
        CoffeeNotFoundException exception = new CoffeeNotFoundException("Espresso");

        ResponseEntity<Map<String, String>> response = orderControllerAdvice.handleCoffeeNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Coffee not found: Espresso", response.getBody().get("error"));
    }

    @Test
    void handleOrderNotFoundException_ReturnsNotFound() {
        OrderNotFoundException exception = new OrderNotFoundException("name");

        ResponseEntity<Map<String, String>> response = orderControllerAdvice.handleOrderNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found: name", response.getBody().get("error"));
    }

    @Test
    void handleAddCoffeeException_ReturnsBadRequest() {
        AddCoffeeException exception = new AddCoffeeException();

        ResponseEntity<Map<String, String>> response = orderControllerAdvice.handleAddCoffeeFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("It is needs at least one coffee to create an order", response.getBody().get("error"));
    }
}