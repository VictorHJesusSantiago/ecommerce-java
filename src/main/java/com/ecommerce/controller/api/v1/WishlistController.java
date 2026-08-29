package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.entity.Wishlist;
import com.ecommerce.model.entity.WishlistItem;
import com.ecommerce.service.WishlistService;
import com.ecommerce.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Wishlist APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    @Operation(summary = "Get current user's wishlist")
    public ResponseEntity<ApiResponse<Wishlist>> getWishlist() {
        Long userId = SecurityUtils.getCurrentUserId();
        Wishlist wishlist = wishlistService.getWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success(wishlist));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to wishlist")
    public ResponseEntity<ApiResponse<Void>> addItem(@RequestBody Map<String, Long> body) {
        Long userId = SecurityUtils.getCurrentUserId();
        Long productId = body.get("productId");
        wishlistService.addItem(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Added to wishlist"));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from wishlist")
    public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable Long productId) {
        Long userId = SecurityUtils.getCurrentUserId();
        wishlistService.removeItem(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Removed from wishlist"));
    }

    @DeleteMapping
    @Operation(summary = "Clear wishlist")
    public ResponseEntity<ApiResponse<Void>> clearWishlist() {
        Long userId = SecurityUtils.getCurrentUserId();
        wishlistService.clearWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist cleared"));
    }

    @PostMapping("/items/{productId}/move-to-cart")
    @Operation(summary = "Move wishlist item to cart")
    public ResponseEntity<ApiResponse<Void>> moveToCart(@PathVariable Long productId) {
        Long userId = SecurityUtils.getCurrentUserId();
        wishlistService.moveToCart(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Moved to cart"));
    }

    @GetMapping("/check/{productId}")
    @Operation(summary = "Check if product is in wishlist")
    public ResponseEntity<ApiResponse<Boolean>> isInWishlist(@PathVariable Long productId) {
        Long userId = SecurityUtils.getCurrentUserId();
        boolean inWishlist = wishlistService.isInWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success(inWishlist));
    }

    @GetMapping("/count")
    @Operation(summary = "Get wishlist count")
    public ResponseEntity<ApiResponse<Long>> getCount() {
        Long userId = SecurityUtils.getCurrentUserId();
        long count = wishlistService.getCount(userId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
