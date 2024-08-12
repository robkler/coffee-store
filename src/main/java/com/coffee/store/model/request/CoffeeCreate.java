package com.coffee.store.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoffeeCreate {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @PositiveOrZero
    private Double price;
}
