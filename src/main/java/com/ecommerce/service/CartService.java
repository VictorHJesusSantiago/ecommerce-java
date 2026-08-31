package com.ecommerce.service;

import com.ecommerce.model.dto.request.cart.*;
import com.ecommerce.model.dto.response.cart.CartResponse;
import com.ecommerce.model.entity.Cart;

public interface CartService {

    CartResponse getCart(String sessionId);

    CartResponse getCartByUser(Long userId);

    CartResponse addToCart(String sessionId, Long userId, AddToCartRequest request);

    CartResponse updateCartItem(String sessionId, Long userId, UpdateCartItemRequest request);

    CartResponse removeFromCart(String sessionId, Long userId, Long cartItemId);

    CartResponse clearCart(String sessionId, Long userId);

    CartResponse applyCoupon(String sessionId, Long userId, String couponCode);

    CartResponse removeCoupon(String sessionId, Long userId);

    CartResponse moveToWishlist(String sessionId, Long userId, Long cartItemId, Long wishlistId);

    Cart mergeCarts(String sessionId, Long userId);

    void convertCartToOrder(Cart cart);

    CartResponse getCartSummary(String sessionId, Long userId);
}
