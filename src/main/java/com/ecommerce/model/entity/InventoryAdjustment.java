package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_adjustments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adjustment_number")
    private String adjustmentNumber;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private String status;

    @Column(name = "total_items")
    private int totalItems;

    @Column(name = "total_value_change", precision = 12, scale = 2)
    private BigDecimal totalValueChange;

    @Column(name = "performed_by")
    private Long performedBy;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
