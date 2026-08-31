package com.ecommerce.service;

import com.ecommerce.model.entity.Wishlist;
import com.ecommerce.model.entity.WishlistItem;

import java.util.List;

public interface WishlistService {
    Wishlist getWishlist(Long userId);
    void addItem(Long userId, Long productId);
    void removeItem(Long userId, Long productId);
    void clearWishlist(Long userId);
    void moveToCart(Long userId, Long productId);
    boolean isInWishlist(Long userId, Long productId);
    long getCount(Long userId);
}
