package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Wishlist;
import com.ecommerce.model.entity.WishlistItem;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.repository.WishlistItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.WishlistService;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @Override
    @Transactional(readOnly = true)
    public Wishlist getWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    Wishlist wishlist = Wishlist.builder().user(user).build();
                    return wishlistRepository.save(wishlist);
                });
    }

    @Override
    @Transactional
    public void addItem(Long userId, Long productId) {
        Wishlist wishlist = getWishlist(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        if (wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            throw new com.ecommerce.exception.ConflictException("Product already in wishlist");
        }

        WishlistItem item = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();
        wishlistItemRepository.save(item);
        log.info("Product {} added to wishlist for user {}", productId, userId);
    }

    @Override
    @Transactional
    public void removeItem(Long userId, Long productId) {
        Wishlist wishlist = getWishlist(userId);
        wishlistItemRepository.deleteByWishlistIdAndProductId(wishlist.getId(), productId);
        log.info("Product {} removed from wishlist for user {}", productId, userId);
    }

    @Override
    @Transactional
    public void clearWishlist(Long userId) {
        Wishlist wishlist = getWishlist(userId);
        wishlistItemRepository.deleteAllByWishlistId(wishlist.getId());
        log.info("Wishlist cleared for user {}", userId);
    }

    @Override
    @Transactional
    public void moveToCart(Long userId, Long productId) {
        removeItem(userId, productId);
        cartService.addItem(userId, productId, 1);
        log.info("Product {} moved from wishlist to cart for user {}", productId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInWishlist(Long userId, Long productId) {
        Wishlist wishlist = getWishlist(userId);
        return wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .map(w -> wishlistItemRepository.countByWishlistId(w.getId()))
                .orElse(0L);
    }
}
