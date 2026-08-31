package com.ecommerce.service;

import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.entity.Product;
import com.ecommerce.repository.*;
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
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private com.ecommerce.service.impl.CartServiceImpl cartService;

    private Cart testCart;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCart = Cart.builder()
                .id(1L)
                .sessionId("test-session")
                .subtotal(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .itemCount(0)
                .isActive(true)
                .build();

        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(BigDecimal.valueOf(29.99))
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    @Test
    void addToCart_NewItem_AddsSuccessfully() {
        when(cartRepository.findBySessionIdAndIsActiveTrue("test-session")).thenReturn(Optional.of(testCart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cartItemRepository.findByCartIdAndProductId(1L, 1L)).thenReturn(Optional.empty());
        when(cartItemRepository.findByCartIdAndIsActiveTrue(1L)).thenReturn(new java.util.ArrayList<>());
        when(cartItemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartRepository.save(any())).thenReturn(testCart);

        var request = new com.ecommerce.model.dto.request.cart.AddItemRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        var response = cartService.addToCart("test-session", null, request);

        assertNotNull(response);
        verify(cartItemRepository, times(1)).save(any());
    }

    @Test
    void clearCart_EmptiesCart() {
        when(cartRepository.findBySessionIdAndIsActiveTrue("test-session")).thenReturn(Optional.of(testCart));
        when(cartRepository.save(any())).thenReturn(testCart);

        var response = cartService.clearCart("test-session", null);

        assertNotNull(response);
        assertEquals(BigDecimal.ZERO, response.getTotal());
    }
}
