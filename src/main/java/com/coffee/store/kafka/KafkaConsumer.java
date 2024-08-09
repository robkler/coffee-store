package com.coffee.store.kafka;

import com.coffee.store.domain.OrderEntity;
import com.coffee.store.model.OrderStatus;
import com.coffee.store.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    public KafkaConsumer(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "coffee-orders", groupId = "coffee-store-group")
    public void consume(String message) {
        try {
            OrderEntity orderEntity = objectMapper.readValue(message, OrderEntity.class);
            orderEntity.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(orderEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
