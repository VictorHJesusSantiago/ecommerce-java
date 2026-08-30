package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String slug;

    @Column(name = "meta_title", length = 200)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;
}
