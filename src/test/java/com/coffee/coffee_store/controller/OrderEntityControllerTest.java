package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.domain.OrderEntity;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.kafka.KafkaProducer;
import com.coffee.coffee_store.domain.CoffeeEntity;
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


class OrderEntityControllerTest {

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

//    @Test
//    void createOrder_ReturnsCreated() throws CoffeeNotFoundException {
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setCoffeeEntities(Collections.emptyList());
//        OrderEntity savedOrderEntity = new OrderEntity();
//        savedOrderEntity.setId(1L);
//
//        when(orderRepository.save(orderEntity)).thenReturn(savedOrderEntity);
//
//        ResponseEntity<?> response = orderController.createOrder(orderEntity);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(savedOrderEntity, response.getBody());
//        verify(kafkaProducer).sendMessage(savedOrderEntity);
//    }
//
//    @Test
//    void createOrder_CoffeeNotFoundException_ReturnsBadRequest() throws CoffeeNotFoundException {
//        OrderEntity orderEntity = new OrderEntity();
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setName("Espresso");
//        orderEntity.setCoffeeEntities(Collections.singletonList(coffeeEntity));
//        when(coffeeRepository.findByName(coffeeEntity.getName())).thenThrow(new CoffeeNotFoundException("Coffee not found"));
//        ResponseEntity<?> response = orderController.createOrder(orderEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Coffee not found", ((Map<String, String>) response.getBody()).get("error"));
//    }
//
//    @Test
//    void getAllOrders_ReturnsOrderList() {
//        List<OrderEntity> orderEntities = Arrays.asList(new OrderEntity(), new OrderEntity());
//        when(orderRepository.findAll()).thenReturn(orderEntities);
//
//        List<OrderEntity> response = orderController.getAllOrders();
//
//        assertEquals(orderEntities, response);
//    }
//
//    @Test
//    void getOrderById_ReturnsOrder() {
//        OrderEntity orderEntity = new OrderEntity();
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
//
//        ResponseEntity<OrderEntity> response = orderController.getOrderById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(orderEntity, response.getBody());
//    }
//
//    @Test
//    void getOrderById_OrderNotFound_ReturnsNotFound() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<OrderEntity> response = orderController.getOrderById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void updateOrder_ReturnsUpdatedOrder() throws CoffeeNotFoundException {
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setCoffeeEntities(Collections.emptyList());
//        OrderEntity updatedOrderEntity = new OrderEntity();
//        updatedOrderEntity.setId(1L);
//
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
//        when(orderRepository.save(orderEntity)).thenReturn(updatedOrderEntity);
//
//        ResponseEntity<?> response = orderController.updateOrder(1L, orderEntity);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(updatedOrderEntity, response.getBody());
//    }
//
//    @Test
//    void updateOrder_CoffeeNotFoundException_ReturnsBadRequest() throws CoffeeNotFoundException {
//        OrderEntity orderEntity = new OrderEntity();
//        CoffeeEntity coffeeEntity = new CoffeeEntity();
//        coffeeEntity.setName("Espresso");
//        orderEntity.setCoffeeEntities(Collections.singletonList(coffeeEntity));
//
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
//        when(coffeeRepository.findByName(coffeeEntity.getName())).thenThrow(new CoffeeNotFoundException("Coffee not found"));
//
//        ResponseEntity<?> response = orderController.updateOrder(1L, orderEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Coffee not found", ((Map<String, String>) response.getBody()).get("error"));
//    }
//
//    @Test
//    void updateOrder_OrderNotFound_ReturnsNotFound() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = orderController.updateOrder(1L, new OrderEntity());
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void deleteOrder_ReturnsNoContent() {
//        OrderEntity orderEntity = new OrderEntity();
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
//
//        ResponseEntity<Void> response = orderController.deleteOrder(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(orderRepository).delete(orderEntity);
//    }
//
//    @Test
//    void deleteOrder_OrderNotFound_ReturnsNotFound() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<Void> response = orderController.deleteOrder(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}
