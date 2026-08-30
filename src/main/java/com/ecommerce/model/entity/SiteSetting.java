package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "site_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class SiteSetting extends BaseEntity {

    @Column(name = "setting_key", nullable = false, unique = true, length = 100)
    private String settingKey;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;

    @Column(name = "setting_group", length = 50)
    @Builder.Default
    private String settingGroup = "general";

    @Column(name = "value_type", length = 20)
    @Builder.Default
    private String valueType = "string";

    @Column(name = "label", length = 200)
    private String label;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;
}
