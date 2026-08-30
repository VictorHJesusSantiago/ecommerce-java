package com.ecommerce.repository;

import com.ecommerce.model.entity.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByProductId(Long productId);

    List<InventoryItem> findByVariantId(Long variantId);

    Optional<InventoryItem> findBySkuAndWarehouseId(String sku, Long warehouseId);

    List<InventoryItem> findByWarehouseId(Long warehouseId);

    @Query("SELECT ii FROM InventoryItem ii WHERE ii.warehouse.id = :warehouseId AND ii.quantity - ii.reservedQuantity - ii.committedQuantity <= ii.reorderPoint")
    List<InventoryItem> findLowStockItems(@Param("warehouseId") Long warehouseId);

    @Query("SELECT ii FROM InventoryItem ii WHERE ii.quantity - ii.reservedQuantity - ii.committedQuantity <= 0")
    List<InventoryItem> findOutOfStockItems();

    @Query("SELECT ii FROM InventoryItem ii WHERE ii.product.id = :productId AND ii.quantity - ii.reservedQuantity > 0")
    List<InventoryItem> findAvailableStockByProductId(@Param("productId") Long productId);

    @Query("SELECT SUM(ii.quantity - ii.reservedQuantity - ii.committedQuantity) FROM InventoryItem ii WHERE ii.product.id = :productId")
    Integer getTotalAvailableQuantity(@Param("productId") Long productId);

    @Query("SELECT ii FROM InventoryItem ii WHERE ii.expiryDate IS NOT NULL AND ii.expiryDate <= :date")
    List<InventoryItem> findExpiringItems(@Param("date") java.time.LocalDateTime date);

    Page<InventoryItem> findByWarehouseIdAndSkuContaining(Long warehouseId, String sku, Pageable pageable);
}
