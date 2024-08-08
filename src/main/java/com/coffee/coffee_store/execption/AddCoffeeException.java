package com.coffee.coffee_store.execption;

public class AddCoffeeException extends RuntimeException {
    public AddCoffeeException() {
        super("It is needs at least one coffee to create an order");
    }
}