package com.coffee.coffee_store.model;

import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.repository.CoffeeRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "coffee_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(
            name = "order_coffees",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "coffee_id")
    )
    private List<Coffee> coffees;

    private String customerName;
    private int totalItems;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public int calculateTotalItems() {
        return coffees.size();
    }

    public double calculateTotalPrice() {
        return coffees.stream().mapToDouble(Coffee::getPrice).sum();
    }

    public void populateCoffeeDetails(CoffeeRepository coffeeRepository) {
        this.coffees = this.coffees.stream()
                .map(coffee -> coffeeRepository.findByName(coffee.getName())
                        .orElseThrow(() -> new CoffeeNotFoundException("Coffee not found: " + coffee.getName())))
                .collect(Collectors.toList());
    }
}