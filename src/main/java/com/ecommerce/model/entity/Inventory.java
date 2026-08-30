package com.ecommerce.model.entity;

import com.ecommerce.model.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "idx_inventory_product", columnList = "product_id"),
    @Index(name = "idx_inventory_variant", columnList = "variant_id"),
    @Index(name = "idx_inventory_warehouse", columnList = "warehouse_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Inventory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "reserved_quantity", nullable = false)
    @Builder.Default
    private Integer reservedQuantity = 0;

    @Column(name = "low_stock_threshold")
    @Builder.Default
    private Integer lowStockThreshold = 5;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private InventoryStatus status = InventoryStatus.IN_STOCK;

    @Column(name = "bin_location", length = 50)
    private String binLocation;

    @Column(name = "last_counted_at")
    private LocalDateTime lastCountedAt;

    @Column(name = "last_received_at")
    private LocalDateTime lastReceivedAt;

    @Column(name = "lot_number", length = 50)
    private String lotNumber;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Transient
    public int getAvailableQuantity() {
        return quantity - reservedQuantity;
    }
}
