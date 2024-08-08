package com.coffee.coffee_store.controller;


import com.coffee.coffee_store.model.CoffeeDTO;
import com.coffee.coffee_store.service.CoffeeService;
import lombok.AllArgsConstructor;
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
    public CoffeeDTO createCoffee(@RequestBody CoffeeDTO coffeeDTO) {
        return coffeeService.createCoffee(coffeeDTO);
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
