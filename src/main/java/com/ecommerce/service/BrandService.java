package com.ecommerce.service;

import com.ecommerce.model.dto.response.BrandResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandById(Long id);
    BrandResponse getBrandBySlug(String slug);
    PaginatedResponse<?> getBrandProducts(Long brandId, Pageable pageable);
    List<BrandResponse> getFeaturedBrands();
    List<BrandResponse> searchBrands(String query);
    long getBrandProductCount(Long brandId);
}
