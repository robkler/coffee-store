package com.coffee.store.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoffeeUpdate {
    @NotNull
    @Positive
    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotEmpty
    @PositiveOrZero
    private double price;

}
