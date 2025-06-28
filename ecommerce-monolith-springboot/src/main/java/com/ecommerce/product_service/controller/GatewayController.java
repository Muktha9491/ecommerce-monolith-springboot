package com.ecommerce.product_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @GetMapping("/gateway/products/test")
    public String testGateway() {
        return "Product Service API Gateway is working!";
    }
}

