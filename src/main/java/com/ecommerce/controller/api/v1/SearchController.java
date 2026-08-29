package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Search APIs")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "Search products")
    public ResponseEntity<ApiResponse<Page<?>>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var results = searchService.search(query, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/autocomplete")
    @Operation(summary = "Get autocomplete suggestions")
    public ResponseEntity<ApiResponse<List<String>>> autocomplete(@RequestParam String prefix) {
        List<String> suggestions = searchService.getAutocompleteSuggestions(prefix);
        return ResponseEntity.ok(ApiResponse.success(suggestions));
    }

    @GetMapping("/facets")
    @Operation(summary = "Get search facets")
    public ResponseEntity<ApiResponse<Map<String, Object>>> facets(@RequestParam String query) {
        Map<String, Object> facets = searchService.getSearchFacets(query);
        return ResponseEntity.ok(ApiResponse.success(facets));
    }

    @GetMapping("/related/{productId}")
    @Operation(summary = "Get related products")
    public ResponseEntity<ApiResponse<List<?>>> related(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "8") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getRelatedProducts(productId, limit)));
    }

    @GetMapping("/popular")
    @Operation(summary = "Get popular products")
    public ResponseEntity<ApiResponse<List<?>>> popular(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getPopularProducts(limit)));
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending products")
    public ResponseEntity<ApiResponse<List<?>>> trending(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getTrendingProducts(limit)));
    }

    @GetMapping("/new-arrivals")
    @Operation(summary = "Get new arrivals")
    public ResponseEntity<ApiResponse<List<?>>> newArrivals(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getNewArrivals(limit)));
    }

    @GetMapping("/best-sellers")
    @Operation(summary = "Get best sellers")
    public ResponseEntity<ApiResponse<List<?>>> bestSellers(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getBestSellers(limit)));
    }

    @GetMapping("/on-sale")
    @Operation(summary = "Get on sale products")
    public ResponseEntity<ApiResponse<List<?>>> onSale(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getOnSaleProducts(limit)));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured products")
    public ResponseEntity<ApiResponse<List<?>>> featured(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getFeaturedProducts(limit)));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get search stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> stats(@RequestParam String query) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getSearchStats(query)));
    }
}
