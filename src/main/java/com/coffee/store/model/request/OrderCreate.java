package com.coffee.store.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderCreate {
    @NotEmpty
    private List<CoffeeOrder> coffees = List.of();

    @NotEmpty
    @Size(max = 255)
    private String customerName;
}
