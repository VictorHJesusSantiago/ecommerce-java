package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/search")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Search Management", description = "Admin search management APIs")
public class AdminSearchController {

    private final SearchService searchService;

    @PostMapping("/reindex")
    @Operation(summary = "Reindex all products")
    public ResponseEntity<ApiResponse<Void>> reindexAll() {
        searchService.reindexAll();
        return ResponseEntity.ok(ApiResponse.success("Reindex started"));
    }

    @PostMapping("/reindex/{productId}")
    @Operation(summary = "Reindex single product")
    public ResponseEntity<ApiResponse<Void>> reindexProduct(@PathVariable Long productId) {
        searchService.reindexProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("Product reindexed"));
    }

    @GetMapping("/suggestions")
    @Operation(summary = "Get search suggestions")
    public ResponseEntity<ApiResponse<List<String>>> getSuggestions(@RequestParam String query) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getSearchSuggestions(query)));
    }

    @GetMapping("/popular")
    @Operation(summary = "Get popular searches")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPopularSearches(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getPopularSearches(limit)));
    }

    @GetMapping("/no-results")
    @Operation(summary = "Get searches with no results")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getNoResultSearches(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(searchService.getNoResultSearches(limit)));
    }

    @DeleteMapping("/cache")
    @Operation(summary = "Clear search cache")
    public ResponseEntity<ApiResponse<Void>> clearSearchCache() {
        searchService.clearSearchCache();
        return ResponseEntity.ok(ApiResponse.success("Search cache cleared"));
    }

    @GetMapping("/synonyms")
    @Operation(summary = "Get search synonyms")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSynonyms() {
        return ResponseEntity.ok(ApiResponse.success(searchService.getSearchSynonyms()));
    }

    @PostMapping("/synonyms")
    @Operation(summary = "Create search synonym")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createSynonym(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(searchService.createSearchSynonym(request)));
    }

    @DeleteMapping("/synonyms/{id}")
    @Operation(summary = "Delete search synonym")
    public ResponseEntity<ApiResponse<Void>> deleteSynonym(@PathVariable Long id) {
        searchService.deleteSearchSynonym(id);
        return ResponseEntity.ok(ApiResponse.success("Synonym deleted"));
    }
}
