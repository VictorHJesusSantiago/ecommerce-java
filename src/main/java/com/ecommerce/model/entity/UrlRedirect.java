package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_redirects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlRedirect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String fromUrl;

    @Column(nullable = false, length = 500)
    private String toUrl;

    @Column(length = 20)
    private String type;

    @Column(nullable = false)
    private boolean isActive;

    private long hitCount;

    private LocalDateTime lastHitAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
