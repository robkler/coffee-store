package com.coffee.store.model.execption;

public class AddCoffeeException extends RuntimeException {
    public AddCoffeeException() {
        super("It is needs at least one coffee to create an order");
    }
}