package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "newsletters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Newsletter extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "is_subscribed", nullable = false)
    @Builder.Default
    private Boolean isSubscribed = true;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt;

    @Column(name = "unsubscribed_at")
    private LocalDateTime unsubscribedAt;

    @Column(name = "unsubscribe_token", unique = true, length = 100)
    private String unsubscribeToken;

    @Column(name = "source", length = 50)
    private String source;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;
}
