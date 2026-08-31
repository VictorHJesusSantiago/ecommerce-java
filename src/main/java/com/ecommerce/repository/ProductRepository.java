package com.ecommerce.repository;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findBySlug(String slug);

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    boolean existsBySlug(String slug);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findByIsActive(boolean isActive, Pageable pageable);

    Page<Product> findByCategory_id(Long categoryId, Pageable pageable);

    Page<Product> findByBrand_id(Long brandId, Pageable pageable);

    Page<Product> findByIsFeaturedTrue(Pageable pageable);

    Page<Product> findByIsDeletedFalse(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Product> findActiveProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.isDeleted = false AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:query% OR p.description LIKE %:query% OR p.sku LIKE %:query%")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.id = :id")
    Optional<Product> findByIdWithImages(@Param("id") Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.variants WHERE p.id = :id")
    Optional<Product> findByIdWithVariants(@Param("id") Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.reviews WHERE p.id = :id")
    Optional<Product> findByIdWithReviews(@Param("id") Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Product> findByIdWithTags(@Param("id") Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.collections WHERE p.id = :id")
    Optional<Product> findByIdWithCollections(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.totalSold > 0 ORDER BY p.totalSold DESC")
    List<Product> findBestSellers(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.averageRating >= :minRating AND p.reviewCount >= :minReviews")
    List<Product> findTopRated(@Param("minRating") Double minRating, @Param("minReviews") Long minReviews, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.brand.id = :brandId")
    Page<Product> findActiveByBrand(@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.category.id IN :categoryIds")
    Page<Product> findActiveByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.brand.id IN :brandIds")
    Page<Product> findActiveByBrandIds(@Param("brandIds") List<Long> brandIds, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.tags.id IN :tagIds")
    Page<Product> findActiveByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.collections.id IN :collectionIds")
    Page<Product> findActiveByCollectionIds(@Param("collectionIds") List<Long> collectionIds, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' ORDER BY p.viewCount DESC")
    List<Product> findMostViewed(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.createdAt >= :since")
    Page<Product> findNewArrivals(@Param("since") java.time.LocalDateTime since, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' AND p.compareAtPrice IS NOT NULL AND p.compareAtPrice > p.price")
    Page<Product> findOnSale(Pageable pageable);

    long countByIsActiveAndIsDeleted(boolean isActive, boolean isDeleted);

    long countByStatus(ProductStatus status);

    @Query("SELECT p.category.id, COUNT(p) FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' GROUP BY p.category.id")
    List<Object[]> countProductsByCategory();

    @Query("SELECT p.brand.id, COUNT(p) FROM Product p WHERE p.isDeleted = false AND p.status = 'ACTIVE' GROUP BY p.brand.id")
    List<Object[]> countProductsByBrand();
}
