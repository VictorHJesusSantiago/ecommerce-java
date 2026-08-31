package com.ecommerce.repository;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findRecentOrdersByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByStatusAndDateRange(@Param("status") OrderStatus status,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :since")
    long countOrdersSince(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status AND o.createdAt >= :since")
    long countByStatusSince(@Param("status") OrderStatus status, @Param("since") LocalDateTime since);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') AND o.createdAt >= :since")
    BigDecimal sumRevenueSince(@Param("since") LocalDateTime since);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') AND o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumRevenueBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT AVG(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') AND o.createdAt >= :since")
    BigDecimal averageOrderValueSince(@Param("since") LocalDateTime since);

    @Query("SELECT FUNCTION('DATE', o.createdAt), COUNT(o), SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') AND o.createdAt >= :since GROUP BY FUNCTION('DATE', o.createdAt) ORDER BY FUNCTION('DATE', o.createdAt)")
    List<Object[]> getDailySalesReport(@Param("since") LocalDateTime since);

    @Query("SELECT o.user.id, COUNT(o), SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') AND o.createdAt >= :since GROUP BY o.user.id ORDER BY SUM(o.total) DESC")
    List<Object[]> getTopCustomersByRevenue(@Param("since") LocalDateTime since, Pageable pageable);

    Optional<Order> findByPaymentTransactionId(String transactionId);

    @Query("SELECT o FROM Order o WHERE o.isPaid = false AND o.createdAt < :cutoffDate")
    List<Order> findUnpaidOrdersBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT o FROM Order o WHERE o.status = 'PROCESSING' AND o.shippedAt IS NULL")
    List<Order> findPendingShipments();

    long countByIsPaidTrue();

    long countByIsFulfilledTrue();
}
