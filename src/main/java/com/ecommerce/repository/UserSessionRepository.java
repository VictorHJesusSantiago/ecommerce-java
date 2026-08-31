package com.ecommerce.repository;

import com.ecommerce.model.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUserIdAndIsActiveTrue(Long userId);

    Optional<UserSession> findByToken(String token);

    @Query("SELECT us FROM UserSession us WHERE us.isActive = true AND us.expiresAt < :now")
    List<UserSession> findExpiredSessions(LocalDateTime now);

    void deleteByUserId(Long userId);
}
