package com.ecommerce.orderservice.service;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.model.User;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.product_service.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
    	logger.info("Creating order for user ID: {}", orderRequest.getUserId());
    	
    	// 1. Validate user
        ResponseEntity<User> userResponse = restTemplate.getForEntity(
                "http://localhost:8080/users/" + orderRequest.getUserId(), User.class);
        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            throw new RuntimeException("Invalid user ID");
        }

        // 2. Validate product and stock
        ResponseEntity<Product> productResponse = restTemplate.getForEntity(
                "http://localhost:8080/products/" + orderRequest.getProductId(), Product.class);
        if (!productResponse.getStatusCode().is2xxSuccessful() || productResponse.getBody() == null) {
            throw new RuntimeException("Invalid product ID");
        }

        Product product = productResponse.getBody();
        if (product.getStock() < orderRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // 3. Reduce product stock
        product.setStock(product.getStock() - orderRequest.getQuantity());
        restTemplate.put("http://localhost:8080/products/" + product.getId(), product);

        // 4. Save order
        Order order = new Order();
        order.setId(orderRequest.getUserId());
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // 5. Build responseDTO
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(savedOrder.getUserId());  // Corrected from userId to orderId
        responseDTO.setUser(userResponse.getBody());
        responseDTO.setProduct(product);
        responseDTO.setQuantity(savedOrder.getQuantity());
        responseDTO.setOrderDate(savedOrder.getOrderDate());
        
        
        
        logger.info("Order created successfully for user ID: {}", orderRequest.getUserId());

        return responseDTO;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> {
            // Fetch user
            ResponseEntity<User> userResponse = restTemplate.getForEntity(
                    "http://localhost:8080/users/" + order.getUserId(), User.class);
            // Fetch product
            ResponseEntity<Product> productResponse = restTemplate.getForEntity(
                    "http://localhost:8080/products/" + order.getProductId(), Product.class);

            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getUserId());  // Corrected here too
            dto.setUser(userResponse.getBody());
            dto.setProduct(productResponse.getBody());
            dto.setQuantity(order.getQuantity());
            dto.setOrderDate(order.getOrderDate());

            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();

        // Fetch user
        ResponseEntity<User> userResponse = restTemplate.getForEntity(
            "http://localhost:8080/users/" + order.getUserId(), User.class);
        
        // Fetch product
        ResponseEntity<Product> productResponse = restTemplate.getForEntity(
            "http://localhost:8080/products/" + order.getProductId(), Product.class);

        dto.setOrderId(order.getId()); // <- use getId() here, not getUserId()
        dto.setUser(userResponse.getBody());
        dto.setProduct(productResponse.getBody()); // <- fix here
        dto.setQuantity(order.getQuantity());
        dto.setOrderDate(order.getOrderDate());

        return dto;
    }

    public void deleteOrder(Long id) {
        logger.info("Cancelling order with ID: {}", id);
        orderRepository.deleteById(id);
        logger.info("Order cancelled: {}", id);
    }
}
