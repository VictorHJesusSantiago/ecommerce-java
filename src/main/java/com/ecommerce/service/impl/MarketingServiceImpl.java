package com.ecommerce.service.impl;

import com.ecommerce.model.dto.request.marketing.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.marketing.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.MarketingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketingServiceImpl implements MarketingService {

    private final CouponRepository couponRepository;
    private final DiscountRepository discountRepository;
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CouponResponse createCoupon(CreateCouponRequest request) {
        Coupon coupon = Coupon.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .type(com.ecommerce.model.enums.CouponType.valueOf(request.getType()))
                .value(request.getValue())
                .minimumOrderAmount(request.getMinimumOrderAmount())
                .maximumDiscountAmount(request.getMaximumDiscountAmount())
                .usageLimit(request.getUsageLimit())
                .usageLimitPerUser(request.getUsageLimitPerUser())
                .isActive(request.isIsActive())
                .isAutomatic(request.isAutomatic())
                .combineWithOtherCoupons(request.isCombineWithOtherCoupons())
                .combineWithProductDiscounts(request.isCombineWithProductDiscounts())
                .appliesToAllProducts(request.isAppliesToAllProducts())
                .buyXQuantity(request.getBuyXQuantity())
                .getYQuantity(request.getYQuantity())
                .getYDiscount(request.getYDiscount())
                .startsAt(request.getStartsAt())
                .expiresAt(request.getExpiresAt())
                .build();

        if (request.getStartsAt() != null) coupon.setStartsAtEnabled(true);
        if (request.getExpiresAt() != null) coupon.setExpiresAtEnabled(true);

        Coupon saved = couponRepository.save(coupon);
        log.info("Coupon created: {}", saved.getCode());

        return mapToCouponResponse(saved);
    }

    @Override
    @Transactional
    public CouponResponse updateCoupon(Long id, CreateCouponRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Coupon", "id", id));
        if (request.getDescription() != null) coupon.setDescription(request.getDescription());
        if (request.getValue() != null) coupon.setValue(request.getValue());
        Coupon saved = couponRepository.save(coupon);
        return mapToCouponResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Coupon", "id", id));
        return mapToCouponResponse(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Coupon", "code", code));
        return mapToCouponResponse(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<CouponResponse> getAllCoupons(Pageable pageable) {
        Page<Coupon> coupons = couponRepository.findAll(pageable);
        return PaginatedResponse.of(
                coupons.getContent().stream().map(this::mapToCouponResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), coupons.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleCouponActive(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Coupon", "id", id));
        coupon.setActive(!coupon.isActive());
        couponRepository.save(coupon);
    }

    @Override
    @Transactional
    public DiscountResponse createDiscount(CreateDiscountRequest request) {
        Discount discount = Discount.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .type(com.ecommerce.model.enums.DiscountType.valueOf(request.getType()))
                .value(request.getValue())
                .minimumPurchaseAmount(request.getMinimumPurchaseAmount())
                .maximumDiscountAmount(request.getMaximumDiscountAmount())
                .buyQuantity(request.getBuyQuantity())
                .getQuantity(request.getGetQuantity())
                .isActive(request.isActive())
                .isAutomatic(request.isAutomatic())
                .useCouponCode(request.isUseCouponCode())
                .appliesToAllProducts(request.isAppliesToAllProducts())
                .startsAt(request.getStartsAt())
                .expiresAt(request.getExpiresAt())
                .priority(request.getPriority())
                .build();
        Discount saved = discountRepository.save(discount);
        return mapToDiscountResponse(saved);
    }

    @Override
    @Transactional
    public DiscountResponse updateDiscount(Long id, CreateDiscountRequest request) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Discount", "id", id));
        if (request.getName() != null) discount.setName(request.getName());
        if (request.getValue() != null) discount.setValue(request.getValue());
        Discount saved = discountRepository.save(discount);
        return mapToDiscountResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountResponse getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Discount", "id", id));
        return mapToDiscountResponse(discount);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<DiscountResponse> getAllDiscounts(Pageable pageable) {
        Page<Discount> discounts = discountRepository.findAll(pageable);
        return PaginatedResponse.of(
                discounts.getContent().stream().map(this::mapToDiscountResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), discounts.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PromotionResponse createPromotion(CreatePromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .bannerUrl(request.getBannerUrl())
                .isActive(request.isActive())
                .startsAt(request.getStartsAt())
                .expiresAt(request.getExpiresAt())
                .priority(request.getPriority())
                .build();
        Promotion saved = promotionRepository.save(promotion);
        return mapToPromotionResponse(saved);
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long id, CreatePromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Promotion", "id", id));
        if (request.getName() != null) promotion.setName(request.getName());
        if (request.getDescription() != null) promotion.setDescription(request.getDescription());
        Promotion saved = promotionRepository.save(promotion);
        return mapToPromotionResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponse getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new com.ecommerce.exception.ResourceNotFoundException("Promotion", "id", id));
        return mapToPromotionResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<PromotionResponse> getAllPromotions(Pageable pageable) {
        Page<Promotion> promotions = promotionRepository.findAll(pageable);
        return PaginatedResponse.of(
                promotions.getContent().stream().map(this::mapToPromotionResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), promotions.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponResponse> getValidCouponsForCart(Long cartId) {
        return couponRepository.findAutomaticCoupons().stream()
                .filter(Coupon::isValid)
                .map(this::mapToCouponResponse)
                .collect(Collectors.toList());
    }

    private CouponResponse mapToCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .description(coupon.getDescription())
                .type(coupon.getType().name())
                .value(coupon.getValue())
                .minimumOrderAmount(coupon.getMinimumOrderAmount())
                .maximumDiscountAmount(coupon.getMaximumDiscountAmount())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .isActive(coupon.isActive())
                .isAutomatic(coupon.isAutomatic())
                .appliesToAllProducts(coupon.isAppliesToAllProducts())
                .currentUsageCount(coupon.getCurrentUsageCount())
                .startsAt(coupon.getStartsAt())
                .expiresAt(coupon.getExpiresAt())
                .isValid(coupon.isValid())
                .createdAt(coupon.getCreatedAt())
                .build();
    }

    private DiscountResponse mapToDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .name(discount.getName())
                .code(discount.getCode())
                .description(discount.getDescription())
                .type(discount.getType().name())
                .value(discount.getValue())
                .minimumPurchaseAmount(discount.getMinimumPurchaseAmount())
                .maximumDiscountAmount(discount.getMaximumDiscountAmount())
                .isActive(discount.isActive())
                .isAutomatic(discount.isAutomatic())
                .startsAt(discount.getStartsAt())
                .expiresAt(discount.getExpiresAt())
                .priority(discount.getPriority())
                .createdAt(discount.getCreatedAt())
                .build();
    }

    private PromotionResponse mapToPromotionResponse(Promotion promotion) {
        return PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .slug(promotion.getSlug())
                .description(promotion.getDescription())
                .imageUrl(promotion.getImageUrl())
                .bannerUrl(promotion.getBannerUrl())
                .isActive(promotion.isActive())
                .startsAt(promotion.getStartsAt())
                .expiresAt(promotion.getExpiresAt())
                .priority(promotion.getPriority())
                .createdAt(promotion.getCreatedAt())
                .build();
    }
}
