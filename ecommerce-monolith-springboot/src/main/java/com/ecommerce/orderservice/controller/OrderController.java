package com.ecommerce.orderservice.controller;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Order Controller", description = "Manage orders")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO requestDTO) {
        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order cancelled successfully.");
    }
}
