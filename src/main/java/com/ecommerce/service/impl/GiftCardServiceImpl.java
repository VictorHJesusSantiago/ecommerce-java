package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.GiftCard;
import com.ecommerce.repository.GiftCardRepository;
import com.ecommerce.service.GiftCardService;
import com.ecommerce.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCardServiceImpl implements GiftCardService {

    private final GiftCardRepository giftCardRepository;

    @Override
    @Transactional(readOnly = true)
    public GiftCard getGiftCardByCode(String code) {
        return giftCardRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("GiftCard", "code", code));
    }

    @Override
    @Transactional
    public GiftCard createGiftCard(BigDecimal amount, Long createdBy) {
        GiftCard giftCard = GiftCard.builder()
                .code(CodeUtil.generateGiftCardCode())
                .initialBalance(amount)
                .currentBalance(amount)
                .isActive(true)
                .build();
        GiftCard saved = giftCardRepository.save(giftCard);
        log.info("Gift card created: {} with amount {}", saved.getCode(), amount);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateGiftCard(String code) {
        return giftCardRepository.findByCode(code)
                .map(gc -> gc.isActive() && gc.getCurrentBalance().compareTo(BigDecimal.ZERO) > 0)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getGiftCardBalance(String code) {
        return giftCardRepository.findByCode(code)
                .map(GiftCard::getCurrentBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public void redeemGiftCard(String code, BigDecimal amount) {
        GiftCard giftCard = getGiftCardByCode(code);
        if (giftCard.getCurrentBalance().compareTo(amount) < 0) {
            throw new com.ecommerce.exception.BadRequestException("Insufficient gift card balance");
        }
        giftCard.setCurrentBalance(giftCard.getCurrentBalance().subtract(amount));
        giftCardRepository.save(giftCard);
        log.info("Gift card {} redeemed: {} (remaining: {})", code, amount, giftCard.getCurrentBalance());
    }
}
