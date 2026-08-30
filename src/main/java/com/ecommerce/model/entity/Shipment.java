package com.ecommerce.model.entity;

import com.ecommerce.model.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shipments", indexes = {
    @Index(name = "idx_shipment_order", columnList = "order_id"),
    @Index(name = "idx_shipment_tracking", columnList = "tracking_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Shipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "shipment_number", nullable = false, unique = true, length = 30)
    private String shipmentNumber;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "carrier", length = 50)
    private String carrier;

    @Column(name = "carrier_service", length = 100)
    private String carrierService;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private ShippingStatus status = ShippingStatus.PENDING;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "delivery_signature", length = 200)
    private String deliverySignature;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "weight_unit", length = 10)
    @Builder.Default
    private String weightUnit = "kg";

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private java.math.BigDecimal shippingCost;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "warehouse_name", length = 100)
    private String warehouseName;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ShipmentItem> items = new HashSet<>();

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TrackingEvent> trackingEvents = new HashSet<>();
}
