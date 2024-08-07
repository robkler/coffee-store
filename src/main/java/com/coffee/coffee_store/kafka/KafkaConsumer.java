package com.coffee.coffee_store.kafka;

import com.coffee.coffee_store.model.Order;
import com.coffee.coffee_store.model.OrderStatus;
import com.coffee.coffee_store.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "coffee-orders", groupId = "coffee-store-group")
    public void consume(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
