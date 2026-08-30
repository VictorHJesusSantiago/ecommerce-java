package com.ecommerce.repository;

import com.ecommerce.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Category> findByParentIdIsNullAndIsActiveTrueOrderBySortOrder();

    List<Category> findByParentIdAndIsActiveTrueOrderBySortOrder(Long parentId);

    List<Category> findByIsActiveTrueOrderBySortOrder();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findChildCategories(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.level = :level AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findByLevel(@Param("level") int level);

    @Query("SELECT c FROM Category c WHERE c.path LIKE CONCAT(:path, '%')")
    List<Category> findByPathStartsWith(@Param("path") String path);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:query% OR c.description LIKE %:query%")
    List<Category> searchCategories(@Param("query") String query);

    Optional<Category> findBySlugAndIsActiveTrue(String slug);

    List<Category> findByIsVisibleTrueAndIsActiveTrueOrderBySortOrder();

    long countByIsActiveTrue();

    long countByParentIdAndIsActiveTrue(Long parentId);
}
