package com.ecommerce.orderservice;

import com.ecommerce.dto.OrderRequestDTO;
import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.model.User;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate; // ✅ Correct dependency to mock

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder_Success() {
        // Prepare input
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setProductId(101L);
        request.setQuantity(2);

        // Mock user
        User user = new User();
        user.setId(1L);
        user.setName("John");

        // Mock product
        Product product = new Product();
        product.setId(101L);
        product.setName("Phone");
        product.setStock(10);

        // Mock external service calls via RestTemplate
        when(restTemplate.getForEntity("http://localhost:8080/users/1", User.class))
                .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        when(restTemplate.getForEntity("http://localhost:8080/products/101", Product.class))
                .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        doNothing().when(restTemplate).put(eq("http://localhost:8080/products/101"), any(Product.class));

        // Mock save order
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(999L); // Mock order ID
                    return order;
                });

        // Call service
        OrderResponseDTO response = orderService.createOrder(request);

        // Assertions
        assertNotNull(response);
        assertEquals(1L, response.getUser().getId());
        assertEquals(101L, response.getProduct().getId());
        assertEquals(2, response.getQuantity());
    }
}
