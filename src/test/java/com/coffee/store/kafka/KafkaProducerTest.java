package com.coffee.store.kafka;

import com.coffee.store.domain.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_sendsMessage_whenOrderEntityIsValid() throws JsonProcessingException {
        OrderEntity orderEntity = new OrderEntity();
        String message = "{\"id\":1,\"status\":\"PENDING\"}";
        when(objectMapper.writeValueAsString(orderEntity)).thenReturn(message);

        kafkaProducer.sendMessage(orderEntity);

        verify(kafkaTemplate).send("coffee-orders", message);
    }

    @Test
    void sendMessage_doesNotSendMessage_whenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        OrderEntity orderEntity = new OrderEntity();
        when(objectMapper.writeValueAsString(orderEntity)).thenThrow(new JsonProcessingException("error") {});

        kafkaProducer.sendMessage(orderEntity);

        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }
}