package com.coffee.store.service;

import com.coffee.store.domain.CoffeeEntity;
import com.coffee.store.model.execption.CoffeeAlreadyExist;
import com.coffee.store.model.execption.CoffeeNotFoundException;
import com.coffee.store.model.CoffeeDTO;
import com.coffee.store.model.request.CoffeeCreate;
import com.coffee.store.model.request.CoffeeUpdate;
import com.coffee.store.repository.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CoffeeServiceTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private CoffeeService coffeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCoffees_returnsListOfCoffeeDTOs() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        Page<CoffeeEntity> coffeePage = new PageImpl<>(List.of(coffeeEntity));
        when(coffeeRepository.findAll(PageRequest.of(0, 10))).thenReturn(coffeePage);

        List<CoffeeDTO> result = coffeeService.getAllCoffees(0, 10);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("Espresso");
    }

    @Test
    void getCoffeeById_returnsCoffeeDTO_whenCoffeeExists() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffeeEntity));

        CoffeeDTO result = coffeeService.getCoffeeById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Espresso");
    }

    @Test
    void getCoffeeById_throwsCoffeeNotFoundException_whenCoffeeDoesNotExist() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CoffeeNotFoundException.class, () -> coffeeService.getCoffeeById(1L));
    }

    @Test
    void createCoffee_createsAndReturnsCoffeeDTO_whenCoffeeDoesNotExist() {
        CoffeeCreate coffeeCreate = new CoffeeCreate();
        coffeeCreate.setName("Espresso");
        coffeeCreate.setPrice(2.50);
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.empty());
        when(coffeeRepository.save(any(CoffeeEntity.class))).thenReturn(coffeeEntity);

        CoffeeDTO result = coffeeService.createCoffee(coffeeCreate);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Espresso");
    }

    @Test
    void createCoffee_throwsCoffeeAlreadyExist_whenCoffeeAlreadyExists() {
        CoffeeCreate coffeeCreate = new CoffeeCreate();
        coffeeCreate.setName("Espresso");
        coffeeCreate.setPrice(2.50);
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(coffeeRepository.findByName("Espresso")).thenReturn(Optional.of(coffeeEntity));

        assertThrows(CoffeeAlreadyExist.class, () -> coffeeService.createCoffee(coffeeCreate));
    }

    @Test
    void updateCoffee_updatesAndReturnsCoffeeDTO_whenCoffeeExists() {
        CoffeeUpdate coffeeUpdate = new CoffeeUpdate();
        coffeeUpdate.setId(1L);
        coffeeUpdate.setName("Espresso");
        coffeeUpdate.setPrice(3.00);
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffeeEntity));
        when(coffeeRepository.save(any(CoffeeEntity.class))).thenReturn(coffeeEntity);

        CoffeeDTO result = coffeeService.updateCoffee(coffeeUpdate);

        assertThat(result).isNotNull();
        assertThat(result.getPrice()).isEqualTo(3.00);
    }

    @Test
    void updateCoffee_throwsCoffeeNotFoundException_whenCoffeeDoesNotExist() {
        CoffeeUpdate coffeeUpdate = new CoffeeUpdate();
        coffeeUpdate.setId(1L);
        coffeeUpdate.setName("Espresso");
        coffeeUpdate.setPrice(3.00);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CoffeeNotFoundException.class, () -> coffeeService.updateCoffee(coffeeUpdate));
    }

    @Test
    void deleteCoffee_deletesCoffee_whenCoffeeExists() {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName("Espresso");
        coffeeEntity.setPrice(2.50);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffeeEntity));

        coffeeService.deleteCoffee(1L);

        verify(coffeeRepository).deleteById(1L);
    }

    @Test
    void deleteCoffee_throwsCoffeeNotFoundException_whenCoffeeDoesNotExist() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CoffeeNotFoundException.class, () -> coffeeService.deleteCoffee(1L));
    }
}