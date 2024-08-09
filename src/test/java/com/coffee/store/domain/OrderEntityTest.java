package com.coffee.store.domain;

import com.coffee.store.model.OrderDTO;
import com.coffee.store.model.OrderStatus;
import com.coffee.store.repository.CoffeeRepository;
import com.coffee.store.model.execption.CoffeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrderEntityTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDTO_returnsOrderDTO_whenCoffeeEntitiesIsNotNull() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .coffeeEntities(List.of(coffeeEntity))
                .customerName("John Doe")
                .totalItems(1)
                .totalPrice(10.0)
                .status(OrderStatus.PENDING)
                .build();

        OrderDTO result = orderEntity.toDTO();

        assertThat(result).isNotNull();
        assertThat(result.getCoffees()).isNotEmpty();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getTotalItems()).isEqualTo(1);
        assertThat(result.getTotalPrice()).isEqualTo(10.0);
    }

    @Test
    void toDTO_returnsOrderDTOWithEmptyCoffees_whenCoffeeEntitiesIsNull() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .coffeeEntities(null)
                .customerName("John Doe")
                .totalItems(1)
                .totalPrice(10.0)
                .status(OrderStatus.PENDING)
                .build();

        OrderDTO result = orderEntity.toDTO();

        assertThat(result).isNotNull();
        assertThat(result.getCoffees()).isEmpty();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getTotalItems()).isEqualTo(1);
        assertThat(result.getTotalPrice()).isEqualTo(10.0);
    }

    @Test
    void calculateTotalItems_returnsCorrectTotalItems() {
        CoffeeEntity coffeeEntity1 = new CoffeeEntity();
        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        OrderEntity orderEntity = OrderEntity.builder()
                .coffeeEntities(List.of(coffeeEntity1, coffeeEntity2))
                .build();

        int totalItems = orderEntity.calculateTotalItems();

        assertThat(totalItems).isEqualTo(2);
    }

    @Test
    void calculateTotalPrice_returnsCorrectTotalPrice() {
        CoffeeEntity coffeeEntity1 = new CoffeeEntity();
        coffeeEntity1.setPrice(5.0);
        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        coffeeEntity2.setPrice(10.0);
        OrderEntity orderEntity = OrderEntity.builder()
                .coffeeEntities(List.of(coffeeEntity1, coffeeEntity2))
                .build();

        double totalPrice = orderEntity.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo(15.0);
    }

    @Test
    void populateCoffeeDetails_populatesCoffeeEntities() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        CoffeeEntity coffeeEntity2 = new CoffeeEntity();
        coffeeEntity2.setPrice(5.0);
        coffeeEntity2.setName("Espresso");

        coffeeEntity.setName("Espresso");
        OrderEntity orderEntity = OrderEntity.builder()
                .coffeeEntities(List.of(coffeeEntity))
                .build();
        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.of(coffeeEntity2));

        orderEntity.populateCoffeeDetails(coffeeRepository);

        assertThat(orderEntity.getCoffeeEntities()).isNotEmpty();
        assertThat(orderEntity.getCoffeeEntities().get(0).getPrice()).isEqualTo(5.0);
    }

    @Test
    void populateCoffeeDetails_throwsCoffeeNotFoundException_whenCoffeeNotFound() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        OrderEntity orderEntity = OrderEntity.builder()
                .coffeeEntities(List.of(coffeeEntity))
                .build();
        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.empty());

        assertThrows(CoffeeNotFoundException.class, () -> orderEntity.populateCoffeeDetails(coffeeRepository));
    }
}