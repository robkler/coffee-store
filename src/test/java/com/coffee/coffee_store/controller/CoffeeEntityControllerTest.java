package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.domain.CoffeeEntity;
import com.coffee.coffee_store.model.CoffeeDTO;
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

class CoffeeEntityControllerTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private CoffeeController coffeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void createCoffee_CoffeeWithSameNameExists_ReturnsConflict() {
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setName("Espresso");
//
//        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.of(coffeeEntity));
//
//        ResponseEntity<?> response = coffeeController.createCoffee(coffeeEntity);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Coffee with name Espresso already exists", ((Map) response.getBody()).get("error"));
//    }
//
//    @Test
//    void createCoffee_NewCoffee_ReturnsCreated() {
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setName("Latte");
//
//        when(coffeeRepository.findByName("Latte")).thenReturn(Optional.empty());
//        when(coffeeRepository.save(coffeeEntity)).thenReturn(coffeeEntity);
//
//        ResponseEntity<?> response = coffeeController.createCoffee(coffeeEntity);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(coffeeEntity, response.getBody());
//    }

//    @Test
//    void getCoffeeById_CoffeeExists_ReturnsCoffee() {
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setId(1L);
//        coffeeEntity.setName("Cappuccino");
//
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffeeEntity));
//
//        ResponseEntity<CoffeeEntity> response = coffeeController.getCoffeeById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(coffeeEntity, response.getBody());
//    }
//
//    @Test
//    void getCoffeeById_CoffeeDoesNotExist_ReturnsNotFound() {
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<CoffeeEntity> response = coffeeController.getCoffeeById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

//    @Test
//    void updateCoffee_CoffeeExists_ReturnsUpdatedCoffee() {
//        CoffeeEntity existingCoffeeEntity = new CoffeeEntity();
//        existingCoffeeEntity.setId(1L);
//        existingCoffeeEntity.setName("Mocha");
//
//        CoffeeEntity updatedDetails = new CoffeeEntity();
//        updatedDetails.setName("Mocha Updated");
//        updatedDetails.setPrice(5.0);
//
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(existingCoffeeEntity));
//        when(coffeeRepository.save(existingCoffeeEntity)).thenReturn(existingCoffeeEntity);
//
//        ResponseEntity<CoffeeEntity> response = coffeeController.updateCoffee(1L, updatedDetails);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(existingCoffeeEntity, response.getBody());
//    }

//    @Test
//    void updateCoffee_CoffeeDoesNotExist_ReturnsNotFound() {
//        CoffeeEntity updatedDetails = new CoffeeEntity();
//        updatedDetails.setName("Mocha Updated");
//        updatedDetails.setPrice(5.0);
//
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        CoffeeDTO response = coffeeController.updateCoffee(1L, updatedDetails.toDTO());
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

//    @Test
//    void deleteCoffee_CoffeeExists_ReturnsNoContent() {
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setId(1L);
//        coffeeEntity.setName("Americano");
//
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffeeEntity));
//
//        ResponseEntity<Void> response = coffeeController.deleteCoffee(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(coffeeRepository, times(1)).delete(coffeeEntity);
//    }
//
//    @Test
//    void deleteCoffee_CoffeeDoesNotExist_ReturnsNotFound() {
//        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<Void> response = coffeeController.deleteCoffee(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}