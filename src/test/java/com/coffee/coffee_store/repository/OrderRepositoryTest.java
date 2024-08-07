package com.coffee.coffee_store.repository;


import com.coffee.coffee_store.model.Coffee;
import com.coffee.coffee_store.model.Order;
import com.coffee.coffee_store.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void testCreateOrder() {
        Coffee coffee1 = new Coffee();
        coffee1.setName("Espresso");
        coffee1.setPrice(2.50);
        Coffee savedCoffee1 = coffeeRepository.save(coffee1);

        Coffee coffee2 = new Coffee();
        coffee2.setName("Latte");
        coffee2.setPrice(3.50);
        Coffee savedCoffee2 = coffeeRepository.save(coffee2);

        Order order = new Order();
        order.setCoffees(Arrays.asList(savedCoffee1, savedCoffee2));
        order.setTotalItems(2);
        order.setTotalPrice(6.00);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }


    @Test
    public void testFindOrderById() {
        Coffee coffee1 = new Coffee();
        coffee1.setName("Espresso");
        coffee1.setPrice(2.50);
        Coffee savedCoffee1 = coffeeRepository.save(coffee1);

        Coffee coffee2 = new Coffee();
        coffee2.setName("Latte");
        coffee2.setPrice(3.50);
        Coffee savedCoffee2 = coffeeRepository.save(coffee2);

        Order order = new Order();
        order.setCoffees(Arrays.asList(savedCoffee1, savedCoffee2));
        order.setTotalItems(2);
        order.setTotalPrice(6.00);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        Long id = savedOrder.getId();
        Optional<Order> getOrder = orderRepository.findById(id);

        assertThat(getOrder).isPresent();
        assertThat(getOrder.get().getId()).isEqualTo(id);
    }

    @Test
    public void testFindAllOrders() {
        Coffee coffee1 = new Coffee();
        coffee1.setName("Espresso");
        coffee1.setPrice(2.50);
        Coffee savedCoffee1 = coffeeRepository.save(coffee1);

        Coffee coffee2 = new Coffee();
        coffee2.setName("Latte");
        coffee2.setPrice(3.50);
        Coffee savedCoffee2 = coffeeRepository.save(coffee2);

        Order order = new Order();
        order.setCoffees(Arrays.asList(savedCoffee1, savedCoffee2));
        order.setTotalItems(2);
        order.setTotalPrice(6.00);
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        List<Order> getOrders = orderRepository.findAll();

        assertThat(getOrders).isNotEmpty();
    }

    @Test
    public void testUpdateOrder() {
        Coffee coffee1 = new Coffee();
        coffee1.setName("Espresso");
        coffee1.setPrice(2.50);
        Coffee savedCoffee1 = coffeeRepository.save(coffee1);

        Coffee coffee2 = new Coffee();
        coffee2.setName("Latte");
        coffee2.setPrice(3.50);
        Coffee savedCoffee2 = coffeeRepository.save(coffee2);

        Order order = new Order();
        order.setCoffees(Arrays.asList(savedCoffee1, savedCoffee2));
        order.setTotalItems(2);
        order.setTotalPrice(6.00);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        Long id = savedOrder.getId();
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order getOrder = orderOptional.get();
        getOrder.setStatus(OrderStatus.COMPLETED);
        getOrder.setCoffees(List.of());

        Order updatedOrder = orderRepository.save(getOrder);

        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(updatedOrder.getCoffees().size()).isEqualTo(2);
    }
}