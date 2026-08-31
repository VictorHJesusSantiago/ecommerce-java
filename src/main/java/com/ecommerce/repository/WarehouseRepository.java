package com.ecommerce.repository;

import com.ecommerce.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByCode(String code);

    boolean existsByCode(String code);

    List<Warehouse> findByIsActiveTrue();

    Optional<Warehouse> findByIsDefaultTrue();

    List<Warehouse> findByIsActiveTrueAndIsDefaultFalse();
}
