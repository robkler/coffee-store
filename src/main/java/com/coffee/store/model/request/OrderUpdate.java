package com.coffee.store.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderUpdate {

    @NotNull
    @Positive
    private Long id;

    @NotEmpty
    private List<CoffeeOrder> coffees = List.of();

    @NotBlank
    @Size(max = 255)
    private String customerName;
}
