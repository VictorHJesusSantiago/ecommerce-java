package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.dto.response.CategoryResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.entity.Category;
import com.ecommerce.model.entity.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final EntityMapper entityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrderAsc().stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return entityMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "slug", slug));
        return entityMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubcategories(Long parentId) {
        return categoryRepository.findByParentIdOrderBySortOrderAsc(parentId).stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentIsNullOrderBySortOrderAsc().stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        List<Category> roots = categoryRepository.findByParentIsNullOrderBySortOrderAsc();
        return roots.stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<?> getCategoryProducts(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryIdAndIsDeleted(categoryId, false, pageable);
        return PaginatedResponse.of(
                products.getContent(), pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getBreadcrumbs(Long categoryId) {
        List<CategoryResponse> breadcrumbs = new ArrayList<>();
        Optional<Category> current = categoryRepository.findById(categoryId);
        while (current.isPresent()) {
            breadcrumbs.add(0, entityMapper.toCategoryResponse(current.get()));
            current = current.get().getParent() != null
                    ? categoryRepository.findById(current.get().getParent().getId())
                    : Optional.empty();
        }
        return breadcrumbs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getFeaturedCategories() {
        return categoryRepository.findByIsFeaturedTrueAndIsActiveTrue().stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getPopularCategories() {
        return categoryRepository.findPopularCategories().stream()
                .map(entityMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getCategoryProductCount(Long categoryId) {
        return productRepository.countByCategoryIdAndIsDeleted(categoryId, false);
    }
}
