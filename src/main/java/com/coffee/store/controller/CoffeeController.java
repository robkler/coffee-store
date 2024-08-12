package com.coffee.store.controller;


import com.coffee.store.model.CoffeeDTO;
import com.coffee.store.model.request.CoffeeCreate;
import com.coffee.store.model.request.CoffeeUpdate;
import com.coffee.store.service.CoffeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping
    public List<CoffeeDTO> getAllCoffees(
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(name = "size", defaultValue = "3") @Min(1) int size) {
        return coffeeService.getAllCoffees(page, size);
    }

    @GetMapping("/{id}")
    public CoffeeDTO getCoffeeById(@PathVariable @Min(1) Long id) {
        return coffeeService.getCoffeeById(id);
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> createCoffee(@Valid @RequestBody CoffeeCreate coffeeCreate) {
            return ResponseEntity.status(HttpStatus.CREATED).body(coffeeService.createCoffee(coffeeCreate));

    }

    @PutMapping
    public CoffeeDTO updateCoffee(@Valid @RequestBody CoffeeUpdate coffeeUpdate) {
        return coffeeService.updateCoffee(coffeeUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable @Min(1) Long id) {
        coffeeService.deleteCoffee(id);
    }
}
