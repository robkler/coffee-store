package com.coffee.coffee_store.execption;

public class AddCoffeeFoundException extends RuntimeException {
    public AddCoffeeFoundException() {
        super("It is needs at least one coffee to create an order");
    }
}