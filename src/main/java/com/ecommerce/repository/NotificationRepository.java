package com.ecommerce.repository;

import com.ecommerce.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> findUnreadByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    long countUnreadByUserId(@Param("userId") Long userId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    Page<Notification> findUnreadPageByUserId(@Param("userId") Long userId, Pageable pageable);

    void deleteByUserIdAndIsReadTrue(Long userId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.referenceType = :referenceType AND n.referenceId = :referenceId")
    List<Notification> findByUserIdAndReference(@Param("userId") Long userId, @Param("referenceType") String referenceType, @Param("referenceId") Long referenceId);
}
