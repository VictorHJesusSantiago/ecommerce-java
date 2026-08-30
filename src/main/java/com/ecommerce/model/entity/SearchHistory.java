package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Table(name = "product_search_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class SearchHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "query", nullable = false, length = 300)
    private String query;

    @Column(name = "result_count")
    private Integer resultCount;

    @Column(name = "clicked_product_id")
    private Long clickedProductId;

    @Column(name = "filters_used", length = 500)
    private String filtersUsed;

    @Column(name = "sort_used", length = 50)
    private String sortUsed;
}
