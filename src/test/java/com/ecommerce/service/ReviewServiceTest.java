package com.ecommerce.service;

import com.ecommerce.model.entity.Review;
import com.ecommerce.model.enums.ReviewStatus;
import com.ecommerce.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private com.ecommerce.repository.ProductRepository productRepository;

    @Mock
    private com.ecommerce.repository.UserRepository userRepository;

    @InjectMocks
    private com.ecommerce.service.impl.ReviewServiceImpl reviewService;

    private Review testReview;

    @BeforeEach
    void setUp() {
        testReview = Review.builder()
                .id(1L)
                .rating(4)
                .title("Great product")
                .comment("Really enjoyed this product")
                .status(ReviewStatus.PENDING)
                .helpfulCount(5)
                .build();
    }

    @Test
    void approveReview_Success() {
        when(reviewRepository.findById(1L)).thenReturn(java.util.Optional.of(testReview));
        when(reviewRepository.save(any())).thenReturn(testReview);

        reviewService.approveReview(1L);

        assertEquals(ReviewStatus.APPROVED, testReview.getStatus());
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void rejectReview_Success() {
        when(reviewRepository.findById(1L)).thenReturn(java.util.Optional.of(testReview));
        when(reviewRepository.save(any())).thenReturn(testReview);

        reviewService.rejectReview(1L);

        assertEquals(ReviewStatus.REJECTED, testReview.getStatus());
    }

    @Test
    void getReviewById_ReturnsReview() {
        when(reviewRepository.findById(1L)).thenReturn(java.util.Optional.of(testReview));

        var response = reviewService.getReviewById(1L);

        assertNotNull(response);
        assertEquals(4, response.getRating());
    }
}
