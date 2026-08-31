package com.ecommerce.service;

import com.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SearchService {
    Page<Product> search(String query, Pageable pageable);
    Page<Product> search(String query, Long categoryId, Long brandId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String sortBy, String sortDir, Pageable pageable);
    List<String> getAutocompleteSuggestions(String prefix);
    Map<String, Object> getSearchFacets(String query);
    List<Product> getRelatedProducts(Long productId, int limit);
    List<Product> getFrequentlyBoughtTogether(Long productId, int limit);
    List<Product> getRecentlyViewed(Long userId, int limit);
    List<Product> getPopularProducts(int limit);
    List<Product> getTrendingProducts(int limit);
    List<Product> getNewArrivals(int limit);
    List<Product> getBestSellers(int limit);
    List<Product> getOnSaleProducts(int limit);
    List<Product> getFeaturedProducts(int limit);
    Map<String, Object> getSearchStats(String query);
}
