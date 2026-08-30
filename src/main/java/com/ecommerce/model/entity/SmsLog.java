package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String toPhone;

    @Column(nullable = false, length = 200)
    private String message;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String referenceType;

    private Long referenceId;

    @Column(length = 1000)
    private String errorMessage;

    private Long providerMessageId;

    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
