package com.coffee.coffee_store.repository;

import com.coffee.coffee_store.model.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CoffeeRepositoryTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void testCreateCoffee() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        coffee.setPrice(2.50);

        Coffee savedCoffee = coffeeRepository.save(coffee);

        assertThat(savedCoffee).isNotNull();
        assertThat(savedCoffee.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindCoffeeById() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        coffee.setPrice(2.50);

        Coffee savedCoffee = coffeeRepository.save(coffee);

        assertThat(savedCoffee).isNotNull();
        assertThat(savedCoffee.getId()).isGreaterThan(0);
        Long id = coffee.getId();

        Optional<Coffee> getCoffee = coffeeRepository.findById(id);

        assertThat(getCoffee).isPresent();
        assertThat(getCoffee.get().getId()).isEqualTo(id);
    }

    @Test
    public void testFindAllCoffees() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        coffee.setPrice(2.50);

        coffeeRepository.save(coffee);

        List<Coffee> coffees = coffeeRepository.findAll();

        assertThat(coffees).isNotEmpty();
    }

    @Test
    public void testUpdateCoffee() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        coffee.setPrice(2.50);

        Coffee savedCoffee = coffeeRepository.save(coffee);

        Long id = savedCoffee.getId();
        Optional<Coffee> coffeeOptional = coffeeRepository.findById(id);
        Coffee getCoffee = coffeeOptional.get();
        getCoffee.setPrice(3.00);

        Coffee updatedCoffee = coffeeRepository.save(coffee);

        assertThat(updatedCoffee.getPrice()).isEqualTo(3.00);
        assertThat(updatedCoffee.getId()).isEqualTo(savedCoffee.getId());
    }
}