package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.kafka.KafkaProducer;
import com.coffee.coffee_store.model.Order;
import com.coffee.coffee_store.model.OrderStatus;
import com.coffee.coffee_store.repository.CoffeeRepository;
import com.coffee.coffee_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            order.populateCoffeeDetails(coffeeRepository);
            order.setTotalItems(order.calculateTotalItems());
            order.setTotalPrice(order.calculateTotalPrice());
            order.setStatus(OrderStatus.PENDING);
            Order savedOrder = orderRepository.save(order);
            kafkaProducer.sendMessage(savedOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (CoffeeNotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            try {
                Order order = orderOptional.get();
                order.setCustomerName(orderDetails.getCustomerName());
                order.setCoffees(orderDetails.getCoffees());
                order.populateCoffeeDetails(coffeeRepository);
                order.setTotalItems(order.calculateTotalItems());
                order.setTotalPrice(order.calculateTotalPrice());
                Order savedOrder = orderRepository.save(order);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
            } catch (CoffeeNotFoundException ex) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", ex.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}