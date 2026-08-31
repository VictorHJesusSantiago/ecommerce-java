package com.ecommerce.service;

import com.ecommerce.model.dto.request.product.*;
import com.ecommerce.model.dto.response.product.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse getProductBySlug(String slug);

    void deleteProduct(Long id);

    void restoreProduct(Long id);

    PaginatedResponse<ProductResponse> getAllProducts(Pageable pageable);

    PaginatedResponse<ProductResponse> searchProducts(String query, Pageable pageable);

    PaginatedResponse<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable);

    PaginatedResponse<ProductResponse> getProductsByBrand(Long brandId, Pageable pageable);

    PaginatedResponse<ProductResponse> getFeaturedProducts(Pageable pageable);

    PaginatedResponse<ProductResponse> getNewArrivals(Pageable pageable);

    PaginatedResponse<ProductResponse> getOnSaleProducts(Pageable pageable);

    List<ProductResponse> getBestSellers(int limit);

    List<ProductResponse> getTopRated(int limit);

    List<ProductResponse> getMostViewed(int limit);

    ProductResponse uploadProductImage(Long productId, MultipartFile file);

    void deleteProductImage(Long imageId);

    void reorderProductImages(Long productId, List<Long> imageIds);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse getCategoryBySlug(String slug);

    void deleteCategory(Long id);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getCategoryTree();

    BrandResponse createBrand(BrandRequest request);

    BrandResponse updateBrand(Long id, BrandRequest request);

    BrandResponse getBrandById(Long id);

    BrandResponse getBrandBySlug(String slug);

    void deleteBrand(Long id);

    PaginatedResponse<BrandResponse> getAllBrands(Pageable pageable);

    TagResponse createTag(String name, String color);

    void deleteTag(Long id);

    List<TagResponse> getAllTags();

    CollectionResponse createCollection(String name, String description);

    void deleteCollection(Long id);

    PaginatedResponse<CollectionResponse> getAllCollections(Pageable pageable);
}
