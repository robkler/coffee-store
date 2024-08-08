package com.coffee.coffee_store.execption;

public class CoffeeNotFoundException extends RuntimeException {
    public CoffeeNotFoundException(String name) {
        super("Coffee not found: " + name);
    }

    public CoffeeNotFoundException(Long id) {
        super("Coffee " + id + " not found");
    }
}