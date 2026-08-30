package com.ecommerce.repository;

import com.ecommerce.model.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("SELECT b FROM Banner b WHERE b.isActive = true AND b.position = :position ORDER BY b.sortOrder ASC")
    List<Banner> findActiveByPosition(@Param("position") String position);

    @Query("SELECT b FROM Banner b WHERE b.isActive = true ORDER BY b.sortOrder ASC")
    List<Banner> findAllActive();

    @Query("SELECT b FROM Banner b WHERE b.isActive = true AND (b.startsAtEnabled = false OR b.startsAt <= :now) AND (b.expiresAtEnabled = false OR b.expiresAt >= :now) AND b.position = :position ORDER BY b.sortOrder ASC")
    List<Banner> findCurrentByPosition(@Param("position") String position, @Param("now") LocalDateTime now);

    long countByIsActiveTrue();
}
