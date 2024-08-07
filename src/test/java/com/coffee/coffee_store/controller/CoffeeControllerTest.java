package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.model.Coffee;
import com.coffee.coffee_store.repository.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoffeeControllerTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private CoffeeController coffeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCoffee_CoffeeWithSameNameExists_ReturnsConflict() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");

        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.of(coffee));

        ResponseEntity<?> response = coffeeController.createCoffee(coffee);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Coffee with name Espresso already exists", ((Map) response.getBody()).get("error"));
    }

    @Test
    void createCoffee_NewCoffee_ReturnsCreated() {
        Coffee coffee = new Coffee();
        coffee.setName("Latte");

        when(coffeeRepository.findByName("Latte")).thenReturn(Optional.empty());
        when(coffeeRepository.save(coffee)).thenReturn(coffee);

        ResponseEntity<?> response = coffeeController.createCoffee(coffee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(coffee, response.getBody());
    }

    @Test
    void getCoffeeById_CoffeeExists_ReturnsCoffee() {
        Coffee coffee = new Coffee();
        coffee.setId(1L);
        coffee.setName("Cappuccino");

        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        ResponseEntity<Coffee> response = coffeeController.getCoffeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coffee, response.getBody());
    }

    @Test
    void getCoffeeById_CoffeeDoesNotExist_ReturnsNotFound() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Coffee> response = coffeeController.getCoffeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateCoffee_CoffeeExists_ReturnsUpdatedCoffee() {
        Coffee existingCoffee = new Coffee();
        existingCoffee.setId(1L);
        existingCoffee.setName("Mocha");

        Coffee updatedDetails = new Coffee();
        updatedDetails.setName("Mocha Updated");
        updatedDetails.setPrice(5.0);

        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(existingCoffee));
        when(coffeeRepository.save(existingCoffee)).thenReturn(existingCoffee);

        ResponseEntity<Coffee> response = coffeeController.updateCoffee(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCoffee, response.getBody());
    }

    @Test
    void updateCoffee_CoffeeDoesNotExist_ReturnsNotFound() {
        Coffee updatedDetails = new Coffee();
        updatedDetails.setName("Mocha Updated");
        updatedDetails.setPrice(5.0);

        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Coffee> response = coffeeController.updateCoffee(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteCoffee_CoffeeExists_ReturnsNoContent() {
        Coffee coffee = new Coffee();
        coffee.setId(1L);
        coffee.setName("Americano");

        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        ResponseEntity<Void> response = coffeeController.deleteCoffee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(coffeeRepository, times(1)).delete(coffee);
    }

    @Test
    void deleteCoffee_CoffeeDoesNotExist_ReturnsNotFound() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = coffeeController.deleteCoffee(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}