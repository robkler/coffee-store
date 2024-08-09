package com.coffee.store.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoffeeOrder {
    @NotEmpty
    @Size(max = 255)
    private String name;
}
