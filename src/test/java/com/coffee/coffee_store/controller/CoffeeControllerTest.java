package com.coffee.coffee_store.controller;

import com.coffee.coffee_store.execption.CoffeeNotFoundException;
import com.coffee.coffee_store.model.CoffeeDTO;
import com.coffee.coffee_store.service.CoffeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
class CoffeeControllerTest {
    @MockBean
    private CoffeeService coffeeService;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createCoffee_ValidCoffee_ReturnsCreated() throws Exception {
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        coffeeDTO.setName("Latte");

        when(coffeeService.createCoffee(any(CoffeeDTO.class))).thenReturn(coffeeDTO);

        mockMvc.perform(post("/coffee")
                        .contentType("application/json")
                        .content("{\"name\": \"Latte\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Latte"));
    }

    @Test
    void getAllCoffees_ReturnsListOfCoffees() throws Exception {
        List<CoffeeDTO> coffeeList = List.of(
                new CoffeeDTO(1L, "Espresso",2.5),
                new CoffeeDTO(2L, "Latte",3.0),
                new CoffeeDTO(3L, "Cappuccino",4.5)
        );

        when(coffeeService.getAllCoffees(0, 3)).thenReturn(coffeeList);

        mockMvc.perform(get("/coffee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Espresso"))
                .andExpect(jsonPath("$[1].name").value("Latte"))
                .andExpect(jsonPath("$[2].name").value("Cappuccino"));
    }

    @Test
    void getCoffeeById_CoffeeExists_ReturnsCoffee() throws Exception {
        CoffeeDTO coffeeDTO = new CoffeeDTO(1L, "Espresso",5.0);

        when(coffeeService.getCoffeeById(1L)).thenReturn(coffeeDTO);

        mockMvc.perform(get("/coffee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Espresso"));
    }

    @Test
    void getCoffeeById_CoffeeDoesNotExist_ReturnsNotFound() throws Exception {
        when(coffeeService.getCoffeeById(999L)).thenThrow(new CoffeeNotFoundException(999L));

        mockMvc.perform(get("/coffee/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCoffee_ValidCoffee_ReturnsUpdatedCoffee() throws Exception {
        CoffeeDTO coffeeDTO = new CoffeeDTO(1L, "Latte Updated",5.0);

        when(coffeeService.updateCoffee(any(CoffeeDTO.class))).thenReturn(coffeeDTO);

        mockMvc.perform(put("/coffee/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Latte Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Latte Updated"));
    }

    @Test
    void deleteCoffee_CoffeeExists_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/coffee/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCoffee_CoffeeDoesNotExist_ReturnsNotFound() throws Exception {
        doThrow(new CoffeeNotFoundException(999L)).when(coffeeService).deleteCoffee(999L);

        mockMvc.perform(delete("/coffee/999"))
                .andExpect(status().isNotFound());
    }
}