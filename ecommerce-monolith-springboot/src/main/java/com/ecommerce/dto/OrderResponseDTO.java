package com.ecommerce.dto;

import com.ecommerce.model.User;
import com.ecommerce.product_service.model.Product;

import java.time.LocalDateTime;

public class OrderResponseDTO {
    private Long orderId;
    private User user;
    private Product product;
    private Integer quantity;
    private LocalDateTime orderDate;

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product2) {
        this.product = product2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


}
