package com.ecommerce.repository;

import com.ecommerce.model.entity.Refund;
import com.ecommerce.model.enums.RefundStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByRefundNumber(String refundNumber);

    Page<Refund> findByOrderId(Long orderId, Pageable pageable);

    Page<Refund> findByStatus(RefundStatus status, Pageable pageable);

    @Query("SELECT SUM(r.amount) FROM Refund r WHERE r.status = 'COMPLETED' AND r.createdAt >= :since")
    BigDecimal sumCompletedRefundsSince(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(r) FROM Refund r WHERE r.status = 'PENDING'")
    long countPendingRefunds();

    @Query("SELECT r FROM Refund r WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Refund> findPendingRefunds();

    long countByStatus(RefundStatus status);
}
