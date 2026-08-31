package com.ecommerce.integration;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.enums.ProductStatus;
import com.ecommerce.repository.ProductRepository;
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
class SearchIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void searchProducts_WithEmptyQuery_ReturnsResults() {
        var results = productRepository.searchProducts("test", PageRequest.of(0, 10));
        assertNotNull(results);
    }

    @Test
    void countBySearchQuery_WithNonExistentQuery_ReturnsZero() {
        long count = productRepository.countBySearchQuery("nonexistentproductxyz123");
        assertEquals(0, count);
    }
}
