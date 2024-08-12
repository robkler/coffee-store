package com.coffee.store.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private List<CoffeeDTO> coffees = List.of();
    private String customerName;
    private int totalItems;
    private Double totalPrice;
    private OrderStatus status;
}
