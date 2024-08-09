package com.coffee.store.model.execption;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String name) {
        super("Order not found: " + name);
    }

    public OrderNotFoundException(Long id) {
        super("Order " + id + " not found");
    }
}