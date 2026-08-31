package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.dto.request.product.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.product.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.ProductStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final CollectionRepository collectionRepository;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product with SKU '" + request.getSku() + "' already exists");
        }

        String slug = request.getSlug() != null ? request.getSlug() : SlugUtil.toSlug(request.getName());
        if (productRepository.existsBySlug(slug)) {
            slug = slug + "-" + System.currentTimeMillis();
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
        }

        Brand brand = null;
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
        }

        Product product = Product.builder()
                .name(request.getName())
                .slug(slug)
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .sku(request.getSku())
                .category(category)
                .brand(brand)
                .price(request.getPrice())
                .compareAtPrice(request.getCompareAtPrice())
                .costPrice(request.getCostPrice())
                .taxRate(request.getTaxRate() != null ? request.getTaxRate() : java.math.BigDecimal.ZERO)
                .isTaxable(request.getIsTaxable() != null ? request.getIsTaxable() : true)
                .status(request.getStatus() != null ? ProductStatus.valueOf(request.getStatus()) : ProductStatus.DRAFT)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false)
                .isDigital(request.getIsDigital() != null ? request.getIsDigital() : false)
                .requiresShipping(request.getRequiresShipping() != null ? request.getRequiresShipping() : true)
                .trackInventory(request.getTrackInventory() != null ? request.getTrackInventory() : true)
                .build();

        Product savedProduct = productRepository.save(product);

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            savedProduct.setTags(new java.util.HashSet<>(tags));
        }

        if (request.getCollectionIds() != null && !request.getCollectionIds().isEmpty()) {
            List<Collection> collections = collectionRepository.findAllById(request.getCollectionIds());
            savedProduct.setCollections(new java.util.HashSet<>(collections));
        }

        if (request.getVariants() != null) {
            for (ProductVariantRequest vr : request.getVariants()) {
                ProductVariant variant = ProductVariant.builder()
                        .product(savedProduct)
                        .name(vr.getName())
                        .sku(vr.getSku())
                        .price(vr.getPrice())
                        .compareAtPrice(vr.getCompareAtPrice())
                        .costPrice(vr.getCostPrice())
                        .stockQuantity(vr.getStockQuantity())
                        .isActive(vr.isActive())
                        .option1(vr.getOption1())
                        .option2(vr.getOption2())
                        .option3(vr.getOption3())
                        .build();
                savedProduct.getProductVariants().add(variant);
            }
        }

        savedProduct = productRepository.save(savedProduct);
        log.info("Product created: {} (SKU: {})", savedProduct.getName(), savedProduct.getSku());

        return mapToResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getShortDescription() != null) product.setShortDescription(request.getShortDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getCompareAtPrice() != null) product.setCompareAtPrice(request.getCompareAtPrice());
        if (request.getCostPrice() != null) product.setCostPrice(request.getCostPrice());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            product.setCategory(category);
        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
            product.setBrand(brand);
        }
        if (request.getStatus() != null) product.setStatus(ProductStatus.valueOf(request.getStatus()));
        if (request.getIsActive() != null) product.setActive(request.getIsActive());
        if (request.getIsFeatured() != null) product.setFeatured(request.getIsFeatured());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "slug", slug));
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setIsDeleted(true);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void restoreProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setIsDeleted(false);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> searchProducts(String query, Pageable pageable) {
        Page<Product> products = productRepository.searchProducts(query, pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategory_id(categoryId, pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getProductsByBrand(Long brandId, Pageable pageable) {
        Page<Product> products = productRepository.findByBrand_id(brandId, pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getFeaturedProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByIsFeaturedTrue(pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getNewArrivals(Pageable pageable) {
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        Page<Product> products = productRepository.findNewArrivals(since, pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ProductResponse> getOnSaleProducts(Pageable pageable) {
        Page<Product> products = productRepository.findOnSale(pageable);
        return PaginatedResponse.of(
                products.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getBestSellers(int limit) {
        return productRepository.findBestSellers(org.springframework.data.domain.PageRequest.of(0, limit))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getTopRated(int limit) {
        return productRepository.findTopRated(4.0, 5L, org.springframework.data.domain.PageRequest.of(0, limit))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getMostViewed(int limit) {
        return productRepository.findMostViewed(org.springframework.data.domain.PageRequest.of(0, limit))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse uploadProductImage(Long productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        String imageUrl = fileStorageService.storeProductImage(productId, file);

        ProductImage image = ProductImage.builder()
                .product(product)
                .url(imageUrl)
                .altText(product.getName())
                .isPrimary(product.getImages().isEmpty())
                .sortOrder(product.getImages().size())
                .build();

        product.getImages().add(image);
        productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    @Transactional
    public void deleteProductImage(Long imageId) {
        // Implementation for deleting a product image
    }

    @Override
    @Transactional
    public void reorderProductImages(Long productId, List<Long> imageIds) {
        // Implementation for reordering product images
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Category slug already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .slug(request.getSlug() != null ? request.getSlug() : SlugUtil.toSlug(request.getName()))
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .iconUrl(request.getIconUrl())
                .bannerUrl(request.getBannerUrl())
                .color(request.getColor())
                .isActive(request.isActive())
                .isVisible(request.isVisible())
                .sortOrder(request.getSortOrder())
                .build();

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getParentId()));
            category.setParent(parent);
            category.setLevel(parent.getLevel() + 1);
        }

        Category saved = categoryRepository.save(category);
        return mapToCategoryResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        if (request.getName() != null) category.setName(request.getName());
        if (request.getDescription() != null) category.setDescription(request.getDescription());
        Category saved = categoryRepository.save(category);
        return mapToCategoryResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapToCategoryResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "slug", slug));
        return mapToCategoryResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrder().stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        return categoryRepository.findRootCategories().stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BrandResponse createBrand(BrandRequest request) {
        Brand brand = Brand.builder()
                .name(request.getName())
                .slug(request.getSlug() != null ? request.getSlug() : SlugUtil.toSlug(request.getName()))
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .bannerUrl(request.getBannerUrl())
                .websiteUrl(request.getWebsiteUrl())
                .isActive(request.isActive())
                .isFeatured(request.isFeatured())
                .sortOrder(request.getSortOrder())
                .build();
        Brand saved = brandRepository.save(brand);
        return mapToBrandResponse(saved);
    }

    @Override
    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
        if (request.getName() != null) brand.setName(request.getName());
        if (request.getDescription() != null) brand.setDescription(request.getDescription());
        Brand saved = brandRepository.save(brand);
        return mapToBrandResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
        return mapToBrandResponse(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "slug", slug));
        return mapToBrandResponse(brand);
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<BrandResponse> getAllBrands(Pageable pageable) {
        Page<Brand> brands = brandRepository.findByIsActiveTrueOrderBySortOrder(pageable);
        return PaginatedResponse.of(
                brands.getContent().stream().map(this::mapToBrandResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), brands.getTotalElements()
        );
    }

    @Override
    @Transactional
    public TagResponse createTag(String name, String color) {
        Tag tag = Tag.builder().name(name).slug(SlugUtil.toSlug(name)).color(color).build();
        Tag saved = tagRepository.save(tag);
        return mapToTagResponse(saved);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(this::mapToTagResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CollectionResponse createCollection(String name, String description) {
        Collection collection = Collection.builder()
                .name(name)
                .slug(SlugUtil.toSlug(name))
                .description(description)
                .build();
        Collection saved = collectionRepository.save(collection);
        return mapToCollectionResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<CollectionResponse> getAllCollections(Pageable pageable) {
        Page<Collection> collections = collectionRepository.findAll(pageable);
        return PaginatedResponse.of(
                collections.getContent().stream().map(this::mapToCollectionResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), collections.getTotalElements()
        );
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .sku(product.getSku())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .taxRate(product.getTaxRate())
                .isTaxable(product.isTaxable())
                .status(product.getStatus().name())
                .isActive(product.isActive())
                .isFeatured(product.isFeatured())
                .isDigital(product.isDigital())
                .requiresShipping(product.isRequiresShipping())
                .trackInventory(product.isTrackInventory())
                .averageRating(product.getAverageRating())
                .reviewCount(product.getReviewCount())
                .totalSold(product.getTotalSold())
                .viewCount(product.getViewCount())
                .createdAt(product.getCreatedAt())
                .build();
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .parentId(category.getParentId())
                .imageUrl(category.getImageUrl())
                .iconUrl(category.getIconUrl())
                .bannerUrl(category.getBannerUrl())
                .color(category.getColor())
                .isActive(category.isActive())
                .isVisible(category.isVisible())
                .sortOrder(category.getSortOrder())
                .level(category.getLevel())
                .productCount(category.getProductCount())
                .build();
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .slug(brand.getSlug())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .bannerUrl(brand.getBannerUrl())
                .websiteUrl(brand.getWebsiteUrl())
                .isActive(brand.isActive())
                .isFeatured(brand.isFeatured())
                .sortOrder(brand.getSortOrder())
                .productCount(brand.getProductCount())
                .build();
    }

    private TagResponse mapToTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .description(tag.getDescription())
                .color(tag.getColor())
                .isActive(tag.isActive())
                .productCount(tag.getProductCount())
                .build();
    }

    private CollectionResponse mapToCollectionResponse(Collection collection) {
        return CollectionResponse.builder()
                .id(collection.getId())
                .name(collection.getName())
                .slug(collection.getSlug())
                .description(collection.getDescription())
                .imageUrl(collection.getImageUrl())
                .bannerUrl(collection.getBannerUrl())
                .isActive(collection.isActive())
                .isFeatured(collection.isFeatured())
                .sortOrder(collection.getSortOrder())
                .productCount(collection.getProductCount())
                .build();
    }
}
