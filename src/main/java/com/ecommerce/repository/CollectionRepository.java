package com.ecommerce.repository;

import com.ecommerce.model.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Optional<Collection> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Page<Collection> findByIsFeaturedTrue(Pageable pageable);
}
