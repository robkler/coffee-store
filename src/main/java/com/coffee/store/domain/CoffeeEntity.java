package com.coffee.store.domain;


import com.coffee.store.model.CoffeeDTO;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "coffees")
public class CoffeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private double price;

    public CoffeeDTO toDTO() {
        return CoffeeDTO.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }

}