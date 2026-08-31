package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final LoyaltyPointsRepository loyaltyPointsRepository;

    @Override
    @Transactional
    public LoyaltyAccount getOrCreateAccount(Long customerId) {
        return loyaltyAccountRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    LoyaltyAccount account = LoyaltyAccount.builder()
                            .customerId(customerId)
                            .pointsBalance(0L)
                            .lifetimePoints(0L)
                            .tier("BRONZE")
                            .isActive(true)
                            .build();
                    return loyaltyAccountRepository.save(account);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public LoyaltyAccount getAccount(Long customerId) {
        return getOrCreateAccount(customerId);
    }

    @Override
    @Transactional
    public void earnPoints(Long customerId, Long orderId, long points, String description) {
        LoyaltyAccount account = getOrCreateAccount(customerId);
        account.setPointsBalance(account.getPointsBalance() + points);
        account.setLifetimePoints(account.getLifetimePoints() + points);
        account.setTier(calculateTier(customerId));
        loyaltyAccountRepository.save(account);

        LoyaltyPoints history = LoyaltyPoints.builder()
                .user(account)
                .points(points)
                .type("EARNED")
                .orderId(orderId)
                .description(description)
                .balanceAfter(account.getPointsBalance())
                .build();
        loyaltyPointsRepository.save(history);
        log.info("Customer {} earned {} loyalty points: {}", customerId, points, description);
    }

    @Override
    @Transactional
    public void redeemPoints(Long customerId, long points, String description) {
        LoyaltyAccount account = getOrCreateAccount(customerId);
        if (account.getPointsBalance() < points) {
            throw new com.ecommerce.exception.BadRequestException("Insufficient loyalty points");
        }
        account.setPointsBalance(account.getPointsBalance() - points);
        loyaltyAccountRepository.save(account);

        LoyaltyPoints history = LoyaltyPoints.builder()
                .user(account)
                .points(-points)
                .type("REDEEMED")
                .description(description)
                .balanceAfter(account.getPointsBalance())
                .build();
        loyaltyPointsRepository.save(history);
        log.info("Customer {} redeemed {} loyalty points: {}", customerId, points, description);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoyaltyPoints> getTransactionHistory(Long customerId) {
        LoyaltyAccount account = getOrCreateAccount(customerId);
        return loyaltyPointsRepository.findByUserIdOrderByCreatedAtDesc(account);
    }

    @Override
    @Transactional(readOnly = true)
    public long getPointsBalance(Long customerId) {
        return getOrCreateAccount(customerId).getPointsBalance();
    }

    @Override
    @Transactional(readOnly = true)
    public String calculateTier(Long customerId) {
        LoyaltyAccount account = getOrCreateAccount(customerId);
        long points = account.getLifetimePoints();
        if (points >= 10000) return "PLATINUM";
        if (points >= 5000) return "GOLD";
        if (points >= 1000) return "SILVER";
        return "BRONZE";
    }
}
