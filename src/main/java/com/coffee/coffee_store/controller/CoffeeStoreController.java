package com.coffee.coffee_store.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeStoreController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
