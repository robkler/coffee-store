package com.coffee.coffee_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    private int totalItems;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}