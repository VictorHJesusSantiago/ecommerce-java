package com.ecommerce.service;

import com.ecommerce.model.dto.response.CategoryResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllActiveCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse getCategoryBySlug(String slug);
    List<CategoryResponse> getSubcategories(Long parentId);
    List<CategoryResponse> getRootCategories();
    List<CategoryResponse> getCategoryTree();
    PaginatedResponse<?> getCategoryProducts(Long categoryId, Pageable pageable);
    List<CategoryResponse> getBreadcrumbs(Long categoryId);
    List<CategoryResponse> getFeaturedCategories();
    List<CategoryResponse> getPopularCategories();
    long getCategoryProductCount(Long categoryId);
}
