package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.request.cart.*;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.cart.CartResponse;
import com.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart endpoints")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get current cart")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId) {
        CartResponse cart;
        if (userId != null) {
            cart = cartService.getCartByUser(userId);
        } else {
            cart = cartService.getCart(sessionId);
        }
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId,
            @Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item added to cart",
                cartService.addToCart(sessionId, userId, request)));
    }

    @PutMapping("/items")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cart updated",
                cartService.updateCartItem(sessionId, userId, request)));
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ApiResponse<CartResponse>> removeFromCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId,
            @PathVariable Long cartItemId) {
        return ResponseEntity.ok(ApiResponse.success("Item removed",
                cartService.removeFromCart(sessionId, userId, cartItemId)));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    public ResponseEntity<ApiResponse<CartResponse>> clearCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Cart cleared",
                cartService.clearCart(sessionId, userId)));
    }

    @PostMapping("/coupon")
    @Operation(summary = "Apply coupon to cart")
    public ResponseEntity<ApiResponse<CartResponse>> applyCoupon(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId,
            @RequestBody ApplyCouponRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Coupon applied",
                cartService.applyCoupon(sessionId, userId, request.getCouponCode())));
    }

    @DeleteMapping("/coupon")
    @Operation(summary = "Remove coupon from cart")
    public ResponseEntity<ApiResponse<CartResponse>> removeCoupon(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Coupon removed",
                cartService.removeCoupon(sessionId, userId)));
    }

    @PostMapping("/move-to-wishlist")
    @Operation(summary = "Move cart item to wishlist")
    public ResponseEntity<ApiResponse<CartResponse>> moveToWishlist(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestAttribute(value = "userId", required = false) Long userId,
            @RequestBody MoveToWishlistRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item moved to wishlist",
                cartService.moveToWishlist(sessionId, userId, request.getCartItemId(), request.getWishlistId())));
    }
}
