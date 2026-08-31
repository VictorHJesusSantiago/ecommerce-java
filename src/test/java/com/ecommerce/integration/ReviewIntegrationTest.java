package com.ecommerce.integration;

import com.ecommerce.model.entity.Review;
import com.ecommerce.model.enums.ReviewStatus;
import com.ecommerce.repository.ReviewRepository;
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
class ReviewIntegrationTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void countByProductId_ReturnsZero() {
        long count = reviewRepository.countByProductIdAndStatus(999L, ReviewStatus.APPROVED);
        assertEquals(0, count);
    }

    @Test
    void getAverageRating_ReturnsZero() {
        Double avg = reviewRepository.getAverageRating();
        assertNotNull(avg);
    }
}
