package com.ecommerce.repository;

import com.ecommerce.model.entity.Return;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {

    Optional<Return> findByReturnNumber(String returnNumber);

    Page<Return> findByOrderId(Long orderId, Pageable pageable);

    Page<Return> findByUserId(Long userId, Pageable pageable);

    Page<Return> findByStatus(String status, Pageable pageable);
}
