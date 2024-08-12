package com.coffee.store.model.request;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 255)
    private String customerName;
}
