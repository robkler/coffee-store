package com.coffee.store.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoffeeDTO {
    private Long id;
    private String name;
    private Double price;
}
