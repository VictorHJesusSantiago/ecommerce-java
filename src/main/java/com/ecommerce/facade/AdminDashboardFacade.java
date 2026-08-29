package com.ecommerce.facade;

import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminDashboardFacade {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final TransactionRepository transactionRepository;
    private final RefundRepository refundRepository;
    private final CartRepository cartRepository;
    private final NewsletterRepository newsletterRepository;
    private final CouponRepository couponRepository;
    private final InventoryService inventoryService;

    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getDashboardData() {
        java.util.Map<String, Object> data = new java.util.HashMap<>();

        data.put("totalOrders", orderRepository.count());
        data.put("totalRevenue", orderRepository.sumRevenueSince(java.time.LocalDateTime.now().minusDays(30)));
        data.put("averageOrderValue", orderRepository.averageOrderValueSince(java.time.LocalDateTime.now().minusDays(30)));
        data.put("totalCustomers", userRepository.countActiveUsers());
        data.put("newCustomersToday", userRepository.countNewUsersSince(java.time.LocalDateTime.now().minusDays(1)));
        data.put("totalProducts", productRepository.countByIsActiveAndIsDeleted(false, false));
        data.put("totalReviews", reviewRepository.count());
        data.put("pendingReviews", reviewRepository.countPendingReviews().size());
        data.put("activeCarts", cartRepository.countActiveCarts());
        data.put("newsletterSubscribers", newsletterRepository.countByIsActiveTrueAndIsConfirmedTrue());
        data.put("activeCoupons", couponRepository.countByIsActiveTrue());
        data.put("pendingRefunds", refundRepository.countPendingRefunds());
        data.put("totalTransactions", transactionRepository.count());

        return data;
    }
}
