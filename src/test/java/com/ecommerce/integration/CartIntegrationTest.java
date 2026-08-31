package com.ecommerce.integration;

import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.entity.CartItem;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
class CartIntegrationTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .name("Cart Test Product")
                .slug("cart-test-" + System.currentTimeMillis())
                .sku("CART-" + System.currentTimeMillis())
                .price(BigDecimal.valueOf(19.99))
                .isActive(true)
                .isDeleted(false)
                .inStock(true)
                .build();
        productRepository.save(testProduct);
    }

    @Test
    void createCart_WithItems_PersistsCorrectly() {
        Cart cart = Cart.builder()
                .subtotal(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();
        Cart savedCart = cartRepository.save(cart);

        CartItem item = CartItem.builder()
                .cart(savedCart)
                .product(testProduct)
                .quantity(2)
                .unitPrice(testProduct.getPrice())
                .totalPrice(testProduct.getPrice().multiply(BigDecimal.valueOf(2)))
                .build();
        savedCart.getItems().add(item);
        cartRepository.save(savedCart);

        Cart found = cartRepository.findById(savedCart.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(1, found.getItems().size());
    }
}
