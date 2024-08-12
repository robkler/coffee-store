package com.coffee.store.controller;

import com.coffee.store.model.OrderDTO;
import com.coffee.store.model.request.OrderCreate;
import com.coffee.store.model.request.OrderUpdate;
import com.coffee.store.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(name = "size", defaultValue = "3") @Min(1) int size) {
        return orderService.getAllOrders(page, size);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable @Min(1) Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@Valid @RequestBody OrderCreate orderCreate) {
        return orderService.createOrder(orderCreate);
    }

    @PutMapping
    public OrderDTO updateOrder(@Valid @RequestBody OrderUpdate orderUpdate) {
        return orderService.updateOrder(orderUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable @Min(1) Long id) {
        orderService.deleteOrder(id);
    }
}