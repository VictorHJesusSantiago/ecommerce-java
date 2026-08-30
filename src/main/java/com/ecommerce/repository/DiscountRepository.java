package com.ecommerce.repository;

import com.ecommerce.model.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findByCode(String code);

    List<Discount> findByIsActiveTrue();

    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND d.isAutomatic = true")
    List<Discount> findAutomaticDiscounts();

    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND (d.startsAtEnabled = false OR d.startsAt <= :now) AND (d.expiresAtEnabled = false OR d.expiresAt >= :now)")
    List<Discount> findActiveDiscounts(@Param("now") LocalDateTime now);

    @Query("SELECT d FROM Discount d WHERE d.promotion.id = :promotionId")
    List<Discount> findByPromotionId(@Param("promotionId") Long promotionId);

    long countByIsActiveTrue();
}
