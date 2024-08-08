package com.coffee.coffee_store.kafka;

import com.coffee.coffee_store.domain.OrderEntity;
import com.coffee.coffee_store.model.OrderStatus;
import com.coffee.coffee_store.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class KafkaConsumerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consume_savesOrderEntity_whenMessageIsValid() throws IOException {
        String message = "{\"id\":1,\"status\":\"PENDING\"}";
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus(OrderStatus.PENDING);
        when(objectMapper.readValue(message, OrderEntity.class)).thenReturn(orderEntity);

        kafkaConsumer.consume(message);

        verify(orderRepository).save(orderEntity);
        assertThat(orderEntity.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}