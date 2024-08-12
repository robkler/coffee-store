package com.coffee.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoffeeOrder {
    @NotBlank
    @Size(max = 255)
    private String name;
}
