package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Table(name = "tax_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class TaxRate extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal rate;

    @Column(name = "is_compound", nullable = false)
    @Builder.Default
    private Boolean isCompound = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "priority")
    @Builder.Default
    private Integer priority = 0;

    @Column(name = "description", length = 200)
    private String description;
}
