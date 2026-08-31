package com.ecommerce.integration;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.enums.ProductStatus;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .name("Integration Test Product")
                .slug("integration-test-product-" + System.currentTimeMillis())
                .sku("INT-TEST-" + System.currentTimeMillis())
                .price(BigDecimal.valueOf(49.99))
                .status(ProductStatus.ACTIVE)
                .isActive(true)
                .isDeleted(false)
                .isFeatured(true)
                .build();
        productRepository.save(product);
    }

    @Test
    void createProduct_PersistsCorrectly() {
        Product product = Product.builder()
                .name("New Product")
                .slug("new-product-" + System.currentTimeMillis())
                .sku("NEW-" + System.currentTimeMillis())
                .price(BigDecimal.valueOf(29.99))
                .status(ProductStatus.ACTIVE)
                .isActive(true)
                .isDeleted(false)
                .build();

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("New Product", saved.getName());
        assertEquals(BigDecimal.valueOf(29.99), saved.getPrice());
    }

    @Test
    void findBySlug_Exists_ReturnsProduct() {
        Product product = productRepository.findAll().stream().findFirst().orElse(null);
        if (product != null) {
            var found = productRepository.findBySlug(product.getSlug());
            assertTrue(found.isPresent());
            assertEquals(product.getName(), found.get().getName());
        }
    }

    @Test
    void searchProducts_ReturnsResults() {
        var results = productRepository.searchProducts("Integration", PageRequest.of(0, 10));
        assertFalse(results.isEmpty());
    }

    @Test
    void countActiveProducts_ReturnsPositive() {
        long count = productRepository.countByIsActiveAndIsDeleted(true, false);
        assertTrue(count > 0);
    }
}
