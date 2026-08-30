package com.ecommerce.repository;

import com.ecommerce.model.entity.Newsletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

    Optional<Newsletter> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Newsletter> findByIsActiveTrue(Pageable pageable);

    Page<Newsletter> findByIsConfirmedTrueAndIsActiveTrue(Pageable pageable);

    @Query("SELECT n FROM Newsletter n WHERE n.isActive = true AND n.email LIKE %:query%")
    Page<Newsletter> searchByEmail(@Param("query") String query, Pageable pageable);

    long countByIsActiveTrueAndIsConfirmedTrue();

    long countByIsActiveTrue();
}
