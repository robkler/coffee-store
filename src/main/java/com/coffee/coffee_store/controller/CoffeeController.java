package com.coffee.coffee_store.controller;


import com.coffee.coffee_store.model.Coffee;
import com.coffee.coffee_store.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffeeById(@PathVariable Long id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        return coffee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCoffee(@RequestBody Coffee coffee) {
        if (coffeeRepository.findByName(coffee.getName()).isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Coffee with name " + coffee.getName() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        Coffee savedCoffee = coffeeRepository.save(coffee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCoffee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable Long id, @RequestBody Coffee coffeeDetails) {
        Optional<Coffee> coffeeOptional = coffeeRepository.findById(id);
        if (coffeeOptional.isPresent()) {
            Coffee coffee = coffeeOptional.get();
            coffee.setName(coffeeDetails.getName());
            coffee.setPrice(coffeeDetails.getPrice());
            Coffee updatedCoffee = coffeeRepository.save(coffee);
            return ResponseEntity.ok(updatedCoffee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable Long id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        if (coffee.isPresent()) {
            coffeeRepository.delete(coffee.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
