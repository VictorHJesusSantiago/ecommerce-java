package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_collections_mapping")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollectionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "is_featured")
    private boolean isFeatured;

    @Column(name = "position")
    private String position;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
