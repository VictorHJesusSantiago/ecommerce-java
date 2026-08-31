package com.ecommerce.service;

import com.ecommerce.model.entity.LoyaltyAccount;
import com.ecommerce.model.entity.LoyaltyTransaction;

import java.util.List;

public interface LoyaltyService {
    LoyaltyAccount getOrCreateAccount(Long customerId);
    LoyaltyAccount getAccount(Long customerId);
    void earnPoints(Long customerId, Long orderId, long points, String description);
    void redeemPoints(Long customerId, long points, String description);
    List<LoyaltyTransaction> getTransactionHistory(Long customerId);
    long getPointsBalance(Long customerId);
    String calculateTier(Long customerId);
}
