package com.ecommerce.repository;

import com.ecommerce.model.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Promotion> findByIsActiveTrue();

    @Query("SELECT p FROM Promotion p WHERE p.isActive = true AND (p.startsAtEnabled = false OR p.startsAt <= :now) AND (p.expiresAtEnabled = false OR p.expiresAt >= :now)")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM Promotion p WHERE p.isActive = true AND p.isFeatured = true ORDER BY p.priority DESC")
    List<Promotion> findFeaturedPromotions();
}
