package com.coffee.coffee_store.service;

import com.coffee.coffee_store.execption.AddCoffeeException;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.domain.OrderEntity;
import com.coffee.coffee_store.execption.OrderNotFoundException;
import com.coffee.coffee_store.kafka.KafkaProducer;
import com.coffee.coffee_store.model.OrderDTO;
import com.coffee.coffee_store.model.OrderStatus;
import com.coffee.coffee_store.repository.CoffeeRepository;
import com.coffee.coffee_store.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CoffeeRepository coffeeRepository;

    private final KafkaProducer kafkaProducer;

    public List<OrderDTO> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size)).stream()
                .map(OrderEntity::toDTO)
                .toList();
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderEntity::toDTO)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderDTO.getId()));

        orderEntity.setCustomerName(orderDTO.getCustomerName());
        orderEntity.setCoffeeEntities(orderDTO.getCoffees().stream().map(coffeeDTO -> coffeeRepository.findByName(coffeeDTO.getName()).get()).toList());
        orderEntity.populateCoffeeDetails(coffeeRepository);
        orderEntity.setTotalItems(orderEntity.calculateTotalItems());
        orderEntity.setTotalPrice(orderEntity.calculateTotalPrice());

        OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);
        return updatedOrderEntity.toDTO();
    }

    public void deleteOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(orderEntity);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        if (orderDTO.getCoffees().isEmpty()) {
            throw new AddCoffeeException();
        }
        try {
            OrderEntity orderEntity = OrderEntity.builder()
                    .customerName(orderDTO.getCustomerName())
                    .coffeeEntities(orderDTO.getCoffees() != null ? orderDTO.getCoffees().stream().map(coffeeDTO -> coffeeRepository.findByName(coffeeDTO.getName()).get()).toList() : List.of())
                    .build();

            orderEntity.populateCoffeeDetails(coffeeRepository);
            orderEntity.setTotalItems(orderEntity.calculateTotalItems());
            orderEntity.setTotalPrice(orderEntity.calculateTotalPrice());
            orderEntity.setStatus(OrderStatus.PENDING);

            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            kafkaProducer.sendMessage(savedOrderEntity);
            return savedOrderEntity.toDTO();
        } catch (CoffeeNotFoundException ex) {
            throw new CoffeeNotFoundException(ex.getMessage());
        }
    }

}
