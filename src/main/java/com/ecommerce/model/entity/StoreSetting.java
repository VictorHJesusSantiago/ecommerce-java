package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_settings", indexes = {
    @Index(name = "idx_ss_key", columnList = "settingKey", unique = true),
    @Index(name = "idx_ss_group", columnList = "settingGroup")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StoreSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String settingKey;

    @Column(columnDefinition = "TEXT")
    private String settingValue;

    @Column(length = 50)
    private String settingGroup;

    @Column(length = 50)
    private String settingType = "TEXT";

    private String description;

    private String defaultValue;

    private String options;

    @Column(nullable = false)
    private boolean isPublic = false;

    @Column(nullable = false)
    private boolean isEncrypted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
