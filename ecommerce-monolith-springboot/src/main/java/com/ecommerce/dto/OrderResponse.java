package com.ecommerce.dto;

import com.ecommerce.orderservice.model.Order;

public class OrderResponse {
    private Order order;
    private Userdto user;
    private Product product;

    // Getters and setters

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Userdto getUser() {
        return user;
    }

    public void setUser(Userdto user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
