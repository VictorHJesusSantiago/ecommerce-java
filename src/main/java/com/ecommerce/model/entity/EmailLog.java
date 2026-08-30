package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String toEmail;

    @Column(nullable = false, length = 200)
    private String fromEmail;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(length = 100)
    private String templateName;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String referenceType;

    private Long referenceId;

    @Column(length = 1000)
    private String errorMessage;

    private LocalDateTime sentAt;
    private LocalDateTime openedAt;
    private LocalDateTime clickedAt;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
