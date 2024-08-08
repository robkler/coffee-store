package com.coffee.coffee_store.controller;


import com.coffee.coffee_store.execption.CoffeeAlreadyExist;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.model.CoffeeDTO;
import com.coffee.coffee_store.service.CoffeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping
    public List<CoffeeDTO> getAllCoffees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return coffeeService.getAllCoffees(page, size);
    }

    @GetMapping("/{id}")
    public CoffeeDTO getCoffeeById(@PathVariable Long id) {
        return coffeeService.getCoffeeById(id);
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> createCoffee(@RequestBody CoffeeDTO coffeeDTO) {
        try {
            CoffeeDTO createdCoffee = coffeeService.createCoffee(coffeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCoffee);
        } catch (CoffeeAlreadyExist e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (CoffeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public CoffeeDTO updateCoffee(@PathVariable Long id, @RequestBody CoffeeDTO coffeeDTODetails) {
        coffeeDTODetails.setId(id);
        return coffeeService.updateCoffee(coffeeDTODetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
    }
}
