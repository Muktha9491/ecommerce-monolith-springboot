package com.ecommerce.orderservice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.ecommerce.model.User;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapping user relationship only once
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long productId;
    private Integer quantity;
    private LocalDateTime orderDate;

    // Set order date before persisting
    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }

    // Constructors
    public Order() {}

    public Order(Long id, User user, Long productId, Integer quantity, LocalDateTime orderDate) {
        this.id = id;
        this.user = user;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    // Convenience method if you still want userId directly
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
