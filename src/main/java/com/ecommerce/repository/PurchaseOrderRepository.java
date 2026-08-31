package com.ecommerce.repository;

import com.ecommerce.model.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Optional<PurchaseOrder> findByPoNumber(String poNumber);

    Page<PurchaseOrder> findByStatus(String status, Pageable pageable);

    Page<PurchaseOrder> findBySupplierId(Long supplierId, Pageable pageable);
}
