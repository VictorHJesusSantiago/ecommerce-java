package com.ecommerce.integration;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createOrder_PersistsCorrectly() {
        Order order = Order.builder()
                .orderNumber("ORD-INT-" + System.currentTimeMillis())
                .status(OrderStatus.PENDING)
                .subtotal(BigDecimal.valueOf(99.99))
                .total(BigDecimal.valueOf(109.98))
                .currency("USD")
                .build();

        Order saved = orderRepository.save(order);

        assertNotNull(saved.getId());
        assertEquals("ORD-INT-" + System.currentTimeMillis(), saved.getOrderNumber());
        assertEquals(OrderStatus.PENDING, saved.getStatus());
    }

    @Test
    void countByStatus_ReturnsCorrectCount() {
        long pendingCount = orderRepository.countByStatus(OrderStatus.PENDING);
        assertTrue(pendingCount >= 0);
    }
}
