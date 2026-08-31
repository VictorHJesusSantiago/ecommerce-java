package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.dto.response.BrandResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.entity.Brand;
import com.ecommerce.model.entity.Product;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.BrandService;
import com.ecommerce.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final EntityMapper entityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findByIsActiveTrueOrderByNameAsc().stream()
                .map(entityMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
        return entityMapper.toBrandResponse(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "slug", slug));
        return entityMapper::toBrandResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<?> getBrandProducts(Long brandId, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandIdAndIsDeleted(brandId, false, pageable);
        return PaginatedResponse.of(
                products.getContent(), pageable.getPageNumber(), pageable.getPageSize(), products.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getFeaturedBrands() {
        return brandRepository.findByIsFeaturedTrueAndIsActiveTrue().stream()
                .map(entityMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> searchBrands(String query) {
        return brandRepository.findByNameContainingIgnoreCase(query).stream()
                .map(entityMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getBrandProductCount(Long brandId) {
        return productRepository.countByBrandIdAndIsDeleted(brandId, false);
    }
}
