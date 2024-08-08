package com.coffee.coffee_store.domain;

import com.coffee.coffee_store.converter.OrderStatusConverter;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.model.OrderDTO;
import com.coffee.coffee_store.model.OrderStatus;
import com.coffee.coffee_store.repository.CoffeeRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "coffee_orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "order_coffees",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "coffee_id")
    )
    private List<CoffeeEntity> coffeeEntities;

    @Column
    private String customerName;

    @Column
    private int totalItems;

    @Column
    private double totalPrice;

    @Column
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    public int calculateTotalItems() {
        return coffeeEntities.size();
    }

    public double calculateTotalPrice() {
        return coffeeEntities.stream().mapToDouble(CoffeeEntity::getPrice).sum();
    }

    public void populateCoffeeDetails(CoffeeRepository coffeeRepository) {
        this.coffeeEntities = this.coffeeEntities.stream()
                .map(coffee -> coffeeRepository.findByName(coffee.getName())
                        .orElseThrow(() -> new CoffeeNotFoundException(coffee.getName())))
                .collect(Collectors.toList());
    }

    public OrderDTO toDTO() {
        return OrderDTO.builder()
                .id(this.id)
                .coffees(this.coffeeEntities != null ? this.coffeeEntities.stream().map(CoffeeEntity::toDTO).collect(Collectors.toList()) : List.of())
                .customerName(this.customerName)
                .totalItems(this.totalItems)
                .totalPrice(this.totalPrice)
                .status(this.status)
                .build();
    }

}