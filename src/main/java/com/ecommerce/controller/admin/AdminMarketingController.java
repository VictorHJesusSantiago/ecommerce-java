package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.request.marketing.*;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.marketing.*;
import com.ecommerce.service.MarketingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/marketing")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Marketing", description = "Admin marketing management APIs")
public class AdminMarketingController {

    private final MarketingService marketingService;

    // Coupons
    @PostMapping("/coupons")
    @Operation(summary = "Create coupon")
    public ResponseEntity<ApiResponse<CouponResponse>> createCoupon(@Valid @RequestBody CreateCouponRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Coupon created", marketingService.createCoupon(request)));
    }

    @PutMapping("/coupons/{id}")
    @Operation(summary = "Update coupon")
    public ResponseEntity<ApiResponse<CouponResponse>> updateCoupon(
            @PathVariable Long id, @Valid @RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Coupon updated", marketingService.updateCoupon(id, request)));
    }

    @GetMapping("/coupons/{id}")
    @Operation(summary = "Get coupon by ID")
    public ResponseEntity<ApiResponse<CouponResponse>> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getCouponById(id)));
    }

    @GetMapping("/coupons")
    @Operation(summary = "Get all coupons")
    public ResponseEntity<ApiResponse<PaginatedResponse<CouponResponse>>> getAllCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getAllCoupons(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @DeleteMapping("/coupons/{id}")
    @Operation(summary = "Delete coupon")
    public ResponseEntity<ApiResponse<Void>> deleteCoupon(@PathVariable Long id) {
        marketingService.deleteCoupon(id);
        return ResponseEntity.ok(ApiResponse.success("Coupon deleted"));
    }

    @PutMapping("/coupons/{id}/toggle")
    @Operation(summary = "Toggle coupon active status")
    public ResponseEntity<ApiResponse<Void>> toggleCouponActive(@PathVariable Long id) {
        marketingService.toggleCouponActive(id);
        return ResponseEntity.ok(ApiResponse.success("Coupon status updated"));
    }

    // Discounts
    @PostMapping("/discounts")
    @Operation(summary = "Create discount")
    public ResponseEntity<ApiResponse<DiscountResponse>> createDiscount(@Valid @RequestBody CreateDiscountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Discount created", marketingService.createDiscount(request)));
    }

    @PutMapping("/discounts/{id}")
    @Operation(summary = "Update discount")
    public ResponseEntity<ApiResponse<DiscountResponse>> updateDiscount(
            @PathVariable Long id, @Valid @RequestBody CreateDiscountRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Discount updated", marketingService.updateDiscount(id, request)));
    }

    @GetMapping("/discounts/{id}")
    @Operation(summary = "Get discount by ID")
    public ResponseEntity<ApiResponse<DiscountResponse>> getDiscountById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getDiscountById(id)));
    }

    @GetMapping("/discounts")
    @Operation(summary = "Get all discounts")
    public ResponseEntity<ApiResponse<PaginatedResponse<DiscountResponse>>> getAllDiscounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getAllDiscounts(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @DeleteMapping("/discounts/{id}")
    @Operation(summary = "Delete discount")
    public ResponseEntity<ApiResponse<Void>> deleteDiscount(@PathVariable Long id) {
        marketingService.deleteDiscount(id);
        return ResponseEntity.ok(ApiResponse.success("Discount deleted"));
    }

    // Promotions
    @PostMapping("/promotions")
    @Operation(summary = "Create promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> createPromotion(@Valid @RequestBody CreatePromotionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Promotion created", marketingService.createPromotion(request)));
    }

    @PutMapping("/promotions/{id}")
    @Operation(summary = "Update promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> updatePromotion(
            @PathVariable Long id, @Valid @RequestBody CreatePromotionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Promotion updated", marketingService.updatePromotion(id, request)));
    }

    @GetMapping("/promotions/{id}")
    @Operation(summary = "Get promotion by ID")
    public ResponseEntity<ApiResponse<PromotionResponse>> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getPromotionById(id)));
    }

    @GetMapping("/promotions")
    @Operation(summary = "Get all promotions")
    public ResponseEntity<ApiResponse<PaginatedResponse<PromotionResponse>>> getAllPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(marketingService.getAllPromotions(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @DeleteMapping("/promotions/{id}")
    @Operation(summary = "Delete promotion")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(@PathVariable Long id) {
        marketingService.deletePromotion(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion deleted"));
    }
}
