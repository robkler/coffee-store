package com.coffee.store.service;

import com.coffee.store.model.execption.AddCoffeeException;
import com.coffee.store.domain.OrderEntity;
import com.coffee.store.model.execption.OrderNotFoundException;
import com.coffee.store.kafka.KafkaProducer;
import com.coffee.store.model.OrderDTO;
import com.coffee.store.model.OrderStatus;
import com.coffee.store.model.request.OrderCreate;
import com.coffee.store.model.request.OrderUpdate;
import com.coffee.store.repository.CoffeeRepository;
import com.coffee.store.repository.OrderRepository;
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

    public OrderDTO updateOrder(OrderUpdate orderDTO) {
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

    public OrderDTO createOrder(OrderCreate orderCreate) {
        if (orderCreate.getCoffees().isEmpty()) {
            throw new AddCoffeeException();
        }
        OrderEntity orderEntity = OrderEntity.builder()
                .customerName(orderCreate.getCustomerName())
                .coffeeEntities(orderCreate.getCoffees() != null ? orderCreate.getCoffees().stream().map(coffeeDTO -> coffeeRepository.findByName(coffeeDTO.getName()).get()).toList() : List.of())
                .build();

        orderEntity.populateCoffeeDetails(coffeeRepository);
        orderEntity.setTotalItems(orderEntity.calculateTotalItems());
        orderEntity.setTotalPrice(orderEntity.calculateTotalPrice());
        orderEntity.setStatus(OrderStatus.PENDING);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        kafkaProducer.sendMessage(savedOrderEntity);
        return savedOrderEntity.toDTO();

    }

}
