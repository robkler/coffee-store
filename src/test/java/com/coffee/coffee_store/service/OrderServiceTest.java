package com.coffee.coffee_store.service;

import com.coffee.coffee_store.domain.CoffeeEntity;
import com.coffee.coffee_store.domain.OrderEntity;
import com.coffee.coffee_store.execption.AddCoffeeException;
import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.execption.OrderNotFoundException;
import com.coffee.coffee_store.kafka.KafkaProducer;
import com.coffee.coffee_store.model.CoffeeDTO;
import com.coffee.coffee_store.model.OrderDTO;
import com.coffee.coffee_store.repository.CoffeeRepository;
import com.coffee.coffee_store.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_returnsListOfOrderDTOs() {
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(orderEntity)));

        List<OrderDTO> result = orderService.getAllOrders(0, 10);

        assertThat(result).isNotEmpty();
    }

    @Test
    void getOrderById_returnsOrderDTO_whenOrderExists() {
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

        OrderDTO result = orderService.getOrderById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    void getOrderById_throwsOrderNotFoundException_whenOrderDoesNotExist() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void updateOrder_updatesAndReturnsOrderDTO_whenOrderExists() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderDTO result = orderService.updateOrder(orderDTO);

        assertThat(result).isNotNull();
    }

    @Test
    void updateOrder_throwsOrderNotFoundException_whenOrderDoesNotExist() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderDTO));
    }

    @Test
    void deleteOrder_deletesOrder_whenOrderExists() {
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

        orderService.deleteOrder(1L);

        verify(orderRepository).delete(orderEntity);
    }

    @Test
    void deleteOrder_throwsOrderNotFoundException_whenOrderDoesNotExist() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void createOrder_createsAndReturnsOrderDTO_whenValidOrder() {
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        coffeeDTO.setName("Espresso");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("John Doe");
        orderDTO.setCoffees(List.of(coffeeDTO));
        OrderEntity orderEntity = new OrderEntity();
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(coffeeRepository.findByName(coffeeDTO.getName())).thenReturn(Optional.of(coffeeEntity));

        OrderDTO result = orderService.createOrder(orderDTO);

        assertThat(result).isNotNull();
        verify(orderRepository).save(any(OrderEntity.class));
        verify(kafkaProducer).sendMessage(any(OrderEntity.class));
    }

    @Test
    void createOrder_throwsAddCoffeeException_whenNoCoffeesInOrder() {
        OrderDTO orderDTO = new OrderDTO();

        assertThrows(AddCoffeeException.class, () -> orderService.createOrder(orderDTO));
    }


    @Test
    void createOrder_throwsCoffeeNotFoundException_whenCoffeeNotFound() {
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        coffeeDTO.setName("Espresso");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("John Doe");
        orderDTO.setCoffees(List.of(coffeeDTO));
        when(coffeeRepository.findByName(anyString())).thenThrow(CoffeeNotFoundException.class);

        assertThrows(CoffeeNotFoundException.class, () -> orderService.createOrder(orderDTO));
    }
}