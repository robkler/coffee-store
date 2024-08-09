package com.coffee.store.model.execption;

public class CoffeeAlreadyExist extends RuntimeException {
    public CoffeeAlreadyExist(String name) {
        super("Coffee already exist: " + name);
    }
}
