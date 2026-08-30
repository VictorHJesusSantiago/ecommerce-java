package com.ecommerce.repository;

import com.ecommerce.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findByUserId(Long userId, Pageable pageable);

    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId, Pageable pageable);

    Page<AuditLog> findByAction(String action, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.createdAt >= :since ORDER BY a.createdAt DESC")
    Page<AuditLog> findRecentLogs(@Param("since") LocalDateTime since, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.user.id = :userId AND a.createdAt >= :since")
    List<AuditLog> findByUserIdAndDateRange(@Param("userId") Long userId, @Param("since") LocalDateTime since);

    @Query("SELECT a.module, COUNT(a) FROM AuditLog a WHERE a.createdAt >= :since GROUP BY a.module")
    List<Object[]> getActivityByModuleSince(@Param("since") LocalDateTime since);

    void deleteByCreatedAtBefore(LocalDateTime cutoffDate);
}
