package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Order;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private com.ecommerce.repository.OrderItemRepository orderItemRepository;

    @Mock
    private com.ecommerce.repository.CartRepository cartRepository;

    @Mock
    private com.ecommerce.repository.CartItemRepository cartItemRepository;

    @Mock
    private com.ecommerce.repository.UserRepository userRepository;

    @Mock
    private com.ecommerce.repository.AddressRepository addressRepository;

    @InjectMocks
    private com.ecommerce.service.impl.OrderServiceImpl orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
                .id(1L)
                .orderNumber("ORD-001")
                .status(OrderStatus.PENDING)
                .subtotal(BigDecimal.valueOf(99.99))
                .taxAmount(BigDecimal.valueOf(8.00))
                .total(BigDecimal.valueOf(107.99))
                .currency("USD")
                .isPaid(false)
                .isFulfilled(false)
                .build();
    }

    @Test
    void getOrderById_Exists_ReturnsOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        var response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals("ORD-001", response.getOrderNumber());
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void getOrderById_NotExists_ThrowsException() {
        when(orderRepository.findById(999L)).thenThrow(
                new ResourceNotFoundException("Order", "id", 999L));

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(999L));
    }

    @Test
    void cancelOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any())).thenReturn(testOrder);

        var response = orderService.cancelOrder(1L, "Changed my mind");

        assertNotNull(response);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void getOrderbyNumber_Exists_ReturnsOrder() {
        when(orderRepository.findByOrderNumber("ORD-001")).thenReturn(Optional.of(testOrder));

        var response = orderService.getOrderbyNumber("ORD-001");

        assertNotNull(response);
        assertEquals("ORD-001", response.getOrderNumber());
    }
}
