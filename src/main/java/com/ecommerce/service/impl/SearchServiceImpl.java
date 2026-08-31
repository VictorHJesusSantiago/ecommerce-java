package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.Category;
import com.ecommerce.model.entity.Brand;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> search(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> search(String query, Long categoryId, Long brandId,
                                BigDecimal minPrice, BigDecimal maxPrice,
                                String sortBy, String sortDir, Pageable pageable) {
        return productRepository.searchWithFilters(query, categoryId, brandId,
                minPrice, maxPrice, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAutocompleteSuggestions(String prefix) {
        if (prefix == null || prefix.length() < 2) return Collections.emptyList();
        return productRepository.findBySlugContaining(prefix).stream()
                .map(Product::getName)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSearchFacets(String query) {
        Map<String, Object> facets = new HashMap<>();
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<Brand> brands = brandRepository.findByIsActiveTrue();
        facets.put("categories", categories);
        facets.put("brands", brands);
        return facets;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getRelatedProducts(Long productId, int limit) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return productRepository.findRelatedProducts(productId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getFrequentlyBoughtTogether(Long productId, int limit) {
        return productRepository.findFrequentlyBoughtTogether(productId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getRecentlyViewed(Long userId, int limit) {
        return productRepository.findRecentlyViewedByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getPopularProducts(int limit) {
        return productRepository.findPopularProducts(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getTrendingProducts(int limit) {
        return productRepository.findTrendingProducts(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getNewArrivals(int limit) {
        return productRepository.findNewArrivals(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getBestSellers(int limit) {
        return productRepository.findBestSellers(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getOnSaleProducts(int limit) {
        return productRepository.findOnSaleProducts(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getFeaturedProducts(int limit) {
        return productRepository.findFeaturedProducts(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSearchStats(String query) {
        Map<String, Object> stats = new HashMap<>();
        long count = productRepository.countBySearchQuery(query);
        stats.put("totalResults", count);
        stats.put("query", query);
        return stats;
    }
}
