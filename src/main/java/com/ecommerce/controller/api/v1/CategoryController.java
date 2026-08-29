package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.CategoryResponse;
import com.ecommerce.model.dto.response.BrandResponse;
import com.ecommerce.model.dto.request.CategoryRequest;
import com.ecommerce.model.dto.request.BrandRequest;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Category and Brand APIs")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getAllActiveCategories()));
    }

    @GetMapping("/categories/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryById(id)));
    }

    @GetMapping("/categories/{id}/subcategories")
    @Operation(summary = "Get subcategories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getSubcategories(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getSubcategories(id)));
    }

    @GetMapping("/categories/root")
    @Operation(summary = "Get root categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRootCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getRootCategories()));
    }

    @GetMapping("/categories/tree")
    @Operation(summary = "Get category tree")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoryTree() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryTree()));
    }

    @GetMapping("/categories/{id}/products")
    @Operation(summary = "Get category products")
    public ResponseEntity<ApiResponse<PaginatedResponse<?>>> getCategoryProducts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryProducts(id, PageRequest.of(page, size))));
    }

    @GetMapping("/categories/slug/{slug}")
    @Operation(summary = "Get category by slug")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryBySlug(slug)));
    }

    @GetMapping("/categories/{id}/breadcrumbs")
    @Operation(summary = "Get category breadcrumbs")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getBreadcrumbs(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getBreadcrumbs(id)));
    }

    @GetMapping("/categories/featured")
    @Operation(summary = "Get featured categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getFeaturedCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getFeaturedCategories()));
    }

    @GetMapping("/categories/popular")
    @Operation(summary = "Get popular categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getPopularCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getPopularCategories()));
    }

    @GetMapping("/categories/{id}/product-count")
    @Operation(summary = "Get category product count")
    public ResponseEntity<ApiResponse<Long>> getCategoryProductCount(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryProductCount(id)));
    }
}
