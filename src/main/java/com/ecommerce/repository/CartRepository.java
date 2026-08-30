package com.ecommerce.repository;

import com.ecommerce.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndIsActiveTrue(Long userId);

    Optional<Cart> findBySessionIdAndIsActiveTrue(String sessionId);

    List<Cart> findByIsActiveTrueAndExpiresAtBefore(LocalDateTime now);

    @Query("SELECT c FROM Cart c WHERE c.isActive = true AND c.isConverted = false AND c.updatedAt < :cutoffDate")
    List<Cart> findAbandonedCartsBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.isActive = true AND c.isConverted = false")
    long countActiveCarts();

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.isActive = true AND c.isConverted = false AND c.updatedAt < :since")
    long countAbandonedSince(@Param("since") LocalDateTime since);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.isConverted = true ORDER BY c.createdAt DESC")
    List<Cart> findConvertedCartsByUserId(@Param("userId") Long userId);
}
