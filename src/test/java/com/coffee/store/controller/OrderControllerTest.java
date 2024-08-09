package com.coffee.store.controller;

import com.coffee.store.model.OrderDTO;
import com.coffee.store.model.execption.OrderNotFoundException;
import com.coffee.store.model.request.OrderCreate;
import com.coffee.store.model.request.OrderUpdate;
import com.coffee.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOrders_ReturnsOrderList() throws Exception {
        List<OrderDTO> orderDTOs = Arrays.asList(new OrderDTO(), new OrderDTO());
        when(orderService.getAllOrders(0, 3)).thenReturn(orderDTOs);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getOrderById_OrderExists_ReturnsOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.getOrderById(1L)).thenReturn(orderDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()));
    }

    @Test
    void getOrderById_OrderDoesNotExist_ReturnsNotFound() throws Exception {
        when(orderService.getOrderById(999L)).thenThrow(new OrderNotFoundException("name"));

        mockMvc.perform(get("/orders/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_ValidOrder_ReturnsCreatedOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.createOrder(any(OrderCreate.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content("{\"name\": \"New Order\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()));
    }

    @Test
    void updateOrder_ValidOrder_ReturnsUpdatedOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        when(orderService.updateOrder(any(OrderUpdate.class))).thenReturn(orderDTO);

        mockMvc.perform(put("/orders")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\": \"Updated Order\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()));
    }

    @Test
    void deleteOrder_OrderExists_ReturnsNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder_OrderDoesNotExist_ReturnsNotFound() throws Exception {
        doThrow(new OrderNotFoundException("name")).when(orderService).deleteOrder(999L);

        mockMvc.perform(delete("/orders/999"))
                .andExpect(status().isNotFound());
    }

}
