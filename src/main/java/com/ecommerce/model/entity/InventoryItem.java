package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items", indexes = {
    @Index(name = "idx_inv_sku", columnList = "sku"),
    @Index(name = "idx_inv_product", columnList = "product_id"),
    @Index(name = "idx_inv_warehouse", columnList = "warehouse_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false, length = 100)
    private String sku;

    @Column(nullable = false)
    private int quantity = 0;

    @Column(nullable = false)
    private int reservedQuantity = 0;

    @Column(nullable = false)
    private int incomingQuantity = 0;

    @Column(nullable = false)
    private int committedQuantity = 0;

    private int reorderPoint = 5;

    private int reorderQuantity = 25;

    private int safetyStock = 2;

    @Column(precision = 12, scale = 2)
    private BigDecimal costPerUnit;

    private String locationBin;

    private String locationAisle;

    private String locationShelf;

    private String locationPosition;

    private String lotNumber;

    private LocalDateTime manufacturingDate;

    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String supplierSku;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public int getAvailableQuantity() {
        return quantity - reservedQuantity - committedQuantity;
    }

    public boolean needsReorder() {
        return getAvailableQuantity() <= reorderPoint;
    }
}
