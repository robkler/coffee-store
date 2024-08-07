package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.kafka.KafkaProducer;
import com.coffee.coffee_store.model.Coffee;
import com.coffee.coffee_store.model.Order;
import com.coffee.coffee_store.repository.CoffeeRepository;
import com.coffee.coffee_store.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ReturnsCreated() throws CoffeeNotFoundException {
        Order order = new Order();
        order.setCoffees(Collections.emptyList());
        Order savedOrder = new Order();
        savedOrder.setId(1L);

        when(orderRepository.save(order)).thenReturn(savedOrder);

        ResponseEntity<?> response = orderController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedOrder, response.getBody());
        verify(kafkaProducer).sendMessage(savedOrder);
    }

    @Test
    void createOrder_CoffeeNotFoundException_ReturnsBadRequest() throws CoffeeNotFoundException {
        Order order = new Order();
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        order.setCoffees(Collections.singletonList(coffee));
        when(coffeeRepository.findByName(coffee.getName())).thenThrow(new CoffeeNotFoundException("Coffee not found"));
        ResponseEntity<?> response = orderController.createOrder(order);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Coffee not found", ((Map<String, String>) response.getBody()).get("error"));
    }

    @Test
    void getAllOrders_ReturnsOrderList() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> response = orderController.getAllOrders();

        assertEquals(orders, response);
    }

    @Test
    void getOrderById_ReturnsOrder() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void getOrderById_OrderNotFound_ReturnsNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateOrder_ReturnsUpdatedOrder() throws CoffeeNotFoundException {
        Order order = new Order();
        order.setCoffees(Collections.emptyList());
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(updatedOrder);

        ResponseEntity<?> response = orderController.updateOrder(1L, order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
    }

    @Test
    void updateOrder_CoffeeNotFoundException_ReturnsBadRequest() throws CoffeeNotFoundException {
        Order order = new Order();
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");
        order.setCoffees(Collections.singletonList(coffee));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(coffeeRepository.findByName(coffee.getName())).thenThrow(new CoffeeNotFoundException("Coffee not found"));

        ResponseEntity<?> response = orderController.updateOrder(1L, order);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Coffee not found", ((Map<String, String>) response.getBody()).get("error"));
    }

    @Test
    void updateOrder_OrderNotFound_ReturnsNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = orderController.updateOrder(1L, new Order());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteOrder_ReturnsNoContent() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_OrderNotFound_ReturnsNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
