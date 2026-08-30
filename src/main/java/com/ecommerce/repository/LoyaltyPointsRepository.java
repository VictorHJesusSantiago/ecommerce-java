package com.ecommerce.repository;

import com.ecommerce.model.entity.LoyaltyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    Optional<LoyaltyPoints> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
