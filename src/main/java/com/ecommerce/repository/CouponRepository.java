package com.ecommerce.repository;

import com.ecommerce.model.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.isActive = true AND (c.startsAtEnabled = false OR c.startsAt <= :now) AND (c.expiresAtEnabled = false OR c.expiresAt >= :now)")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code, @Param("now") LocalDateTime now);

    List<Coupon> findByIsActiveTrue();

    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.expiresAtEnabled = true AND c.expiresAt BETWEEN :now AND :deadline")
    List<Coupon> findExpiringSoon(@Param("now") LocalDateTime now, @Param("deadline") LocalDateTime deadline);

    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.usageLimit > 0 AND c.currentUsageCount >= c.usageLimit")
    List<Coupon> findFullyUsedCoupons();

    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.isAutomatic = true")
    List<Coupon> findAutomaticCoupons();

    long countByIsActiveTrue();

    @Query("SELECT c FROM Coupon c WHERE c.code LIKE %:query% OR c.description LIKE %:query%")
    List<Coupon> searchCoupons(@Param("query") String query);
}
