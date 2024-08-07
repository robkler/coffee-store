package com.coffe.coffe_store;

import org.springframework.boot.SpringApplication;

public class TestCoffeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(CoffeStoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
