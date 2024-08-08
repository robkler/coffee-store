package com.coffee.coffee_store.repository;

import com.coffee.coffee_store.domain.CoffeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
@Rollback(false)
public class CoffeeEntityRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3-alpine")
            .withDatabaseName("coffee-store")
            .withUsername("user-coffee-store")
            .withPassword("password-coffee-store");
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void testCreateCoffee() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);

        CoffeeEntity savedCoffeeEntity = coffeeRepository.save(coffeeEntity);

        assertThat(savedCoffeeEntity).isNotNull();
        assertThat(savedCoffeeEntity.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindCoffeeById() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso2");
        coffeeEntity.setPrice(2.50);

        CoffeeEntity savedCoffeeEntity = coffeeRepository.save(coffeeEntity);

        assertThat(savedCoffeeEntity).isNotNull();
        assertThat(savedCoffeeEntity.getId()).isGreaterThan(0);
        Long id = coffeeEntity.getId();

        Optional<CoffeeEntity> getCoffee = coffeeRepository.findById(id);

        assertThat(getCoffee).isPresent();
        assertThat(getCoffee.get().getId()).isEqualTo(id);
    }

    @Test
    public void testFindAllCoffees() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso3");
        coffeeEntity.setPrice(2.50);

        coffeeRepository.save(coffeeEntity);

        List<CoffeeEntity> coffeeEntities = coffeeRepository.findAll();

        assertThat(coffeeEntities).isNotEmpty();
    }

    @Test
    public void testUpdateCoffee() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso4");
        coffeeEntity.setPrice(2.50);

        CoffeeEntity savedCoffeeEntity = coffeeRepository.save(coffeeEntity);

        Long id = savedCoffeeEntity.getId();
        Optional<CoffeeEntity> coffeeOptional = coffeeRepository.findById(id);
        CoffeeEntity getCoffeeEntity = coffeeOptional.get();
        getCoffeeEntity.setPrice(3.00);

        CoffeeEntity updatedCoffeeEntity = coffeeRepository.save(coffeeEntity);

        assertThat(updatedCoffeeEntity.getPrice()).isEqualTo(3.00);
        assertThat(updatedCoffeeEntity.getId()).isEqualTo(savedCoffeeEntity.getId());
    }

    @Test
    public void testFindByName() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso5");
        coffeeEntity.setPrice(2.50);

        coffeeRepository.save(coffeeEntity);

        Optional<CoffeeEntity> coffeeOptional = coffeeRepository.findByName("Espresso5");
        CoffeeEntity getCoffeeEntity = coffeeOptional.get();
        assertThat(getCoffeeEntity.getPrice()).isEqualTo(2.5);
    }
}