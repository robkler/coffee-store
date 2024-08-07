package com.coffee.coffee_store;

import org.springframework.boot.SpringApplication;

public class TestCoffeeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(CoffeeStoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
