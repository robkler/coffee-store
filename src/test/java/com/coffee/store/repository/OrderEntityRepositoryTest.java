package com.coffee.store.repository;


import com.coffee.store.domain.CoffeeEntity;
import com.coffee.store.domain.OrderEntity;
import com.coffee.store.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderEntityRepositoryTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3-alpine")
            .withDatabaseName("coffee-store")
            .withUsername("user-coffee-store")
            .withPassword("password-coffee-store");
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void testCreateOrder() {
        CoffeeEntity coffeeEntity1 = new CoffeeEntity();
        coffeeEntity1.setName("Order-Espresso");
        coffeeEntity1.setPrice(2.50);
        CoffeeEntity savedCoffeeEntity1 = coffeeRepository.save(coffeeEntity1);

        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        coffeeEntity2.setName("Order-Latte");
        coffeeEntity2.setPrice(3.50);
        CoffeeEntity savedCoffeeEntity2 = coffeeRepository.save(coffeeEntity2);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCoffeeEntities(Arrays.asList(savedCoffeeEntity1, savedCoffeeEntity2));
        orderEntity.setTotalItems(2);
        orderEntity.setTotalPrice(6.00);
        orderEntity.setStatus(OrderStatus.PENDING);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        assertThat(savedOrderEntity).isNotNull();
        assertThat(savedOrderEntity.getId()).isGreaterThan(0);
    }


    @Test
    public void testFindOrderById() {
        CoffeeEntity coffeeEntity1 = new CoffeeEntity();
        coffeeEntity1.setName("Order-Espresso2");
        coffeeEntity1.setPrice(2.50);
        CoffeeEntity savedCoffeeEntity1 = coffeeRepository.save(coffeeEntity1);

        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        coffeeEntity2.setName("Order-Latte2");
        coffeeEntity2.setPrice(3.50);
        CoffeeEntity savedCoffeeEntity2 = coffeeRepository.save(coffeeEntity2);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCoffeeEntities(Arrays.asList(savedCoffeeEntity1, savedCoffeeEntity2));
        orderEntity.setTotalItems(2);
        orderEntity.setTotalPrice(6.00);
        orderEntity.setStatus(OrderStatus.PENDING);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        Long id = savedOrderEntity.getId();
        Optional<OrderEntity> getOrder = orderRepository.findById(id);

        assertThat(getOrder).isPresent();
        assertThat(getOrder.get().getId()).isEqualTo(id);
    }

    @Test
    public void testFindAllOrders() {
        CoffeeEntity coffeeEntity1 = new CoffeeEntity();
        coffeeEntity1.setName("Order-Espresso3");
        coffeeEntity1.setPrice(2.50);
        CoffeeEntity savedCoffeeEntity1 = coffeeRepository.save(coffeeEntity1);

        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        coffeeEntity2.setName("Order-Latte3");
        coffeeEntity2.setPrice(3.50);
        CoffeeEntity savedCoffeeEntity2 = coffeeRepository.save(coffeeEntity2);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCoffeeEntities(Arrays.asList(savedCoffeeEntity1, savedCoffeeEntity2));
        orderEntity.setTotalItems(2);
        orderEntity.setTotalPrice(6.00);
        orderEntity.setStatus(OrderStatus.PENDING);

        orderRepository.save(orderEntity);

        List<OrderEntity> getOrderEntities = orderRepository.findAll();

        assertThat(getOrderEntities).isNotEmpty();
    }

}