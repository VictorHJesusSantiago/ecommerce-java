package com.ecommerce.repository;

import com.ecommerce.model.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByWishlistIdOrderByCreatedAtDesc(Long wishlistId);

    Optional<WishlistItem> findByWishlistIdAndProductId(Long wishlistId, Long productId);

    boolean existsByWishlistIdAndProductId(Long wishlistId, Long productId);

    @Query("SELECT wi FROM WishlistItem wi WHERE wi.notifyOnPriceDrop = true AND wi.priceAtAdd > :currentPrice")
    List<WishlistItem> findPriceDropNotifications(@Param("currentPrice") java.math.BigDecimal currentPrice);

    @Query("SELECT wi FROM WishlistItem wi WHERE wi.notifyOnBackInStock = true AND wi.product.id = :productId")
    List<WishlistItem> findBackInStockNotifications(@Param("productId") Long productId);

    long countByWishlistId(Long wishlistId);
}
