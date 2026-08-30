package com.ecommerce.model.entity;

import com.ecommerce.model.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_info", indexes = {
    @Index(name = "idx_ship_order", columnList = "order_id", unique = true),
    @Index(name = "idx_ship_tracking", columnList = "trackingNumber")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingStatus status = ShippingStatus.PENDING;

    @Column(length = 100)
    private String trackingNumber;

    private String trackingUrl;

    private String carrierName;

    private String carrierCode;

    private String serviceType;

    private String labelUrl;

    @Column(columnDefinition = "TEXT")
    private String shippingAddressJson;

    private String recipientName;

    private String recipientPhone;

    private String recipientEmail;

    private Double shippingWeight;

    private String weightUnit;

    private Double shippingLength;

    private Double shippingWidth;

    private Double shippingHeight;

    private String dimensionUnit;

    private String shippingCost;

    private String estimatedDeliveryDate;

    private String actualDeliveryDate;

    private String deliveryProofUrl;

    private String failedDeliveryReason;

    private int deliveryAttemptCount = 0;

    private int maxDeliveryAttempts = 3;

    private boolean requiresSignature = false;

    private boolean isInsured = false;

    private String insuranceValue;

    private String customsDeclaredValue;

    private String customsDeclarationNumber;

    @Column(columnDefinition = "TEXT")
    private String shipmentEventsJson;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
