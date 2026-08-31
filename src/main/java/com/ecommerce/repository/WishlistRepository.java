package com.ecommerce.repository;

import com.ecommerce.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUserIdAndIsDefaultTrue(Long userId);

    List<Wishlist> findByUserId(Long userId);

    Optional<Wishlist> findByShareToken(String shareToken);

    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.isPublic = true")
    List<Wishlist> findPublicWishlistsByUserId(@Param("userId") Long userId);
}
