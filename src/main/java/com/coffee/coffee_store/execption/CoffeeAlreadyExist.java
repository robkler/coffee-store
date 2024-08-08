package com.coffee.coffee_store.execption;

public class CoffeeAlreadyExist extends RuntimeException {
    public CoffeeAlreadyExist(String name) {
        super("Coffee already exist: " + name);
    }
}
