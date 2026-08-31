package com.ecommerce.repository;

import com.ecommerce.model.entity.Transaction;
import com.ecommerce.model.enums.PaymentStatus;
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
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByOrderId(Long orderId, Pageable pageable);

    Page<Transaction> findByStatus(PaymentStatus status, Pageable pageable);

    Optional<Transaction> findByTransactionNumber(String transactionNumber);

    Optional<Transaction> findByGatewayTransactionId(String gatewayTransactionId);

    @Query("SELECT t FROM Transaction t WHERE t.order.id = :orderId AND t.status = 'COMPLETED'")
    List<Transaction> findCompletedTransactionsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 'COMPLETED' AND t.createdAt >= :since")
    BigDecimal sumCompletedTransactionsSince(@Param("since") LocalDateTime since);

    @Query("SELECT t.paymentGateway, COUNT(t), SUM(t.amount) FROM Transaction t WHERE t.status = 'COMPLETED' AND t.createdAt >= :since GROUP BY t.paymentGateway")
    List<Object[]> getTransactionsByGatewaySince(@Param("since") LocalDateTime since);

    long countByStatus(PaymentStatus status);

    @Query("SELECT t FROM Transaction t WHERE t.status IN ('PENDING', 'PROCESSING') AND t.createdAt < :cutoffDate")
    List<Transaction> findStuckTransactionsBefore(@Param("cutoffDate") LocalDateTime cutoffDate);
}
