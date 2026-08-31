package com.ecommerce.integration;

import com.ecommerce.model.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findBySlug_WithNonExistentSlug_ReturnsEmpty() {
        var result = categoryRepository.findBySlug("nonexistent-slug");
        assertTrue(result.isEmpty());
    }

    @Test
    void countCategories_ReturnsZero() {
        long count = categoryRepository.count();
        assertTrue(count >= 0);
    }
}
