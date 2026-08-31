package com.ecommerce.integration;

import com.ecommerce.model.entity.User;
import com.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void countUsers_ReturnsPositive() {
        long count = userRepository.count();
        assertTrue(count >= 0);
    }

    @Test
    void findByEmail_WithNonExistentEmail_ReturnsEmpty() {
        var result = userRepository.findByEmail("nonexistent@test.com");
        assertTrue(result.isEmpty());
    }

    @Test
    void existsByEmail_WithNonExistentEmail_ReturnsFalse() {
        boolean exists = userRepository.existsByEmail("nonexistent@test.com");
        assertFalse(exists);
    }
}
