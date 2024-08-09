package com.coffee.store.domain;

import com.coffee.store.model.CoffeeDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeEntityTest {

    @Test
    void toDTO_returnsCoffeeDTO_withCorrectValues() {
        CoffeeEntity coffeeEntity = CoffeeEntity.builder()
                .id(1L)
                .name("Espresso")
                .price(5.0)
                .build();

        CoffeeDTO coffeeDTO = coffeeEntity.toDTO();

        assertThat(coffeeDTO).isNotNull();
        assertThat(coffeeDTO.getId()).isEqualTo(1L);
        assertThat(coffeeDTO.getName()).isEqualTo("Espresso");
        assertThat(coffeeDTO.getPrice()).isEqualTo(5.0);
    }

    @Test
    void toDTO_returnsCoffeeDTO_withNullValues_whenEntityFieldsAreNull() {
        CoffeeEntity coffeeEntity = CoffeeEntity.builder()
                .id(null)
                .name(null)
                .price(0.0)
                .build();

        CoffeeDTO coffeeDTO = coffeeEntity.toDTO();

        assertThat(coffeeDTO).isNotNull();
        assertThat(coffeeDTO.getId()).isNull();
        assertThat(coffeeDTO.getName()).isNull();
        assertThat(coffeeDTO.getPrice()).isEqualTo(0.0);
    }
}