package com.coffee.store.model.execption;

public class CoffeeNotFoundException extends RuntimeException {
    public CoffeeNotFoundException(String name) {
        super("Coffee not found: " + name);
    }

    public CoffeeNotFoundException(Long id) {
        super("Coffee " + id + " not found");
    }
}