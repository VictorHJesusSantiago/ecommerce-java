package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.BrandResponse;
import com.ecommerce.model.dto.request.BrandRequest;
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
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Brands", description = "Brand APIs")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "Get all brands")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getAllBrands()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandById(id)));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get brand by slug")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandBySlug(slug)));
    }

    @GetMapping("/{id}/products")
    @Operation(summary = "Get brand products")
    public ResponseEntity<ApiResponse<PaginatedResponse<?>>> getBrandProducts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandProducts(id, PageRequest.of(page, size))));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured brands")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getFeaturedBrands() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getFeaturedBrands()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search brands")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> searchBrands(@RequestParam String query) {
        return ResponseEntity.ok(ApiResponse.success(brandService.searchBrands(query)));
    }

    @GetMapping("/count/{id}")
    @Operation(summary = "Get brand product count")
    public ResponseEntity<ApiResponse<Long>> getBrandProductCount(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandProductCount(id)));
    }
}
