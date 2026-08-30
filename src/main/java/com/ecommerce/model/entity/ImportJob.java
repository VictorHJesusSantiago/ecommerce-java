package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "import_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 200)
    private String fileName;

    @Column(nullable = false, length = 50)
    private String status;

    private int totalRows;
    private int processedRows;
    private int successfulRows;
    private int failedRows;

    @Column(length = 2000)
    private String errorLog;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long durationMs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
