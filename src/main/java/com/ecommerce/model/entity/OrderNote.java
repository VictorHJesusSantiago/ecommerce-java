package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_notes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private String authorName;

    @Column(nullable = false)
    private boolean isCustomerVisible = false;

    @Column(nullable = false)
    private boolean isInternal = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
