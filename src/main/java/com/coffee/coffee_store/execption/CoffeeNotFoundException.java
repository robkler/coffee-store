package com.coffee.coffee_store.execption;

public class CoffeeNotFoundException extends RuntimeException {
    public CoffeeNotFoundException(String message) {
        super(message);
    }
}