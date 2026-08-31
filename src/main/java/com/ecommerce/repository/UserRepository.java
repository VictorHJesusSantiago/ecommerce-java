package com.ecommerce.repository;

import com.ecommerce.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u WHERE u.isEnabled = true AND u.isEmailVerified = false AND u.createdAt < :cutoffDate")
    List<User> findUnverifiedUsersBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT u FROM User u WHERE u.isAccountLocked = true AND u.accountLockedUntil < :now")
    List<User> findLockedUsersReadyForUnlock(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :since")
    long countNewUsersSince(@Param("since") LocalDateTime since);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:query% OR u.username LIKE %:query% OR u.firstName LIKE %:query% OR u.lastName LIKE %:query%")
    Page<User> searchUsers(@Param("query") String query, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.lastLoginAt < :cutoffDate AND u.isEnabled = true")
    List<User> findInactiveUsers(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.isEnabled = true")
    long countActiveUsers();
}
