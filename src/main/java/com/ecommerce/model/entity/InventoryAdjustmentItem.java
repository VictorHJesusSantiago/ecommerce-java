package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_adjustment_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adjustment_id")
    private Long adjustmentId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "variant_id")
    private Long variantId;

    @Column(name = "previous_quantity")
    private int previousQuantity;

    @Column(name = "new_quantity")
    private int newQuantity;

    @Column(name = "adjustment_quantity")
    private int adjustmentQuantity;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_cost_change", precision = 10, scale = 2)
    private BigDecimal totalCostChange;

    @Column(name = "reason")
    private String reason;

    @Column(name = "notes")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
