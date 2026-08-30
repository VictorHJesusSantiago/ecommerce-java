package com.ecommerce.repository;

import com.ecommerce.model.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

    Optional<GiftCard> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT gc FROM GiftCard gc WHERE gc.code = :code AND gc.isActive = true AND gc.currentBalance > 0")
    Optional<GiftCard> findActiveByCode(@org.springframework.data.repository.query.Param("code") String code);

    @Query("SELECT SUM(gc.currentBalance) FROM GiftCard gc WHERE gc.isActive = true")
    BigDecimal sumActiveBalances();
}
