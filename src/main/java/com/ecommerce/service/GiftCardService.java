package com.ecommerce.service;

import com.ecommerce.model.entity.GiftCard;

import java.math.BigDecimal;

public interface GiftCardService {
    GiftCard getGiftCardByCode(String code);
    GiftCard createGiftCard(BigDecimal amount, Long createdBy);
    boolean validateGiftCard(String code);
    BigDecimal getGiftCardBalance(String code);
    void redeemGiftCard(String code, BigDecimal amount);
}
