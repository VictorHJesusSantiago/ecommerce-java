package com.ecommerce.repository;

import com.ecommerce.model.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    Page<Brand> findByIsActiveTrueOrderBySortOrder(Pageable pageable);

    List<Brand> findByIsFeaturedTrueAndIsActiveTrueOrderBySortOrder();

    @Query("SELECT b FROM Brand b WHERE b.name LIKE %:query%")
    Page<Brand> searchBrands(@Param("query") String query, Pageable pageable);

    @Query("SELECT b FROM Brand b WHERE b.isActive = true AND b.categories.id IN :categoryIds")
    List<Brand> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
}
