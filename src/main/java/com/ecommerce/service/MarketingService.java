package com.ecommerce.service;

import com.ecommerce.model.dto.request.marketing.*;
import com.ecommerce.model.dto.response.marketing.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MarketingService {

    CouponResponse createCoupon(CreateCouponRequest request);

    CouponResponse updateCoupon(Long id, CreateCouponRequest request);

    CouponResponse getCouponById(Long id);

    CouponResponse getCouponByCode(String code);

    PaginatedResponse<CouponResponse> getAllCoupons(Pageable pageable);

    void deleteCoupon(Long id);

    void toggleCouponActive(Long id);

    DiscountResponse createDiscount(CreateDiscountRequest request);

    DiscountResponse updateDiscount(Long id, CreateDiscountRequest request);

    DiscountResponse getDiscountById(Long id);

    PaginatedResponse<DiscountResponse> getAllDiscounts(Pageable pageable);

    void deleteDiscount(Long id);

    PromotionResponse createPromotion(CreatePromotionRequest request);

    PromotionResponse updatePromotion(Long id, CreatePromotionRequest request);

    PromotionResponse getPromotionById(Long id);

    PaginatedResponse<PromotionResponse> getAllPromotions(Pageable pageable);

    void deletePromotion(Long id);

    List<CouponResponse> getValidCouponsForCart(Long cartId);
}
