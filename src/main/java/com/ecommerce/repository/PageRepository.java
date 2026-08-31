package com.ecommerce.repository;

import com.ecommerce.model.entity.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Page<Page> findByIsPublishedTrue(Pageable pageable);

    Page<Page> findByContentType(String contentType, Pageable pageable);

    @Query("SELECT p FROM Page p WHERE p.isPublished = true AND p.isFeatured = true")
    List<Page> findFeaturedPages();

    @Query("SELECT p FROM Page p WHERE p.title LIKE %:query% OR p.content LIKE %:query%")
    Page<Page> searchPages(@Param("query") String query, Pageable pageable);
}
