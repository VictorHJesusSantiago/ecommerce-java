package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.AuditLogService;
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
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Analytics", description = "Admin analytics APIs")
public class AdminAnalyticsController {

    private final AuditLogService auditLogService;
    private final SearchService searchService;

    @GetMapping("/search-queries")
    @Operation(summary = "Get top search queries")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopSearchQueries(
            @RequestParam(defaultValue = "30") int days,
            @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getTopSearchQueries(days, limit)));
    }

    @GetMapping("/search-filters")
    @Operation(summary = "Get active search filters")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getActiveSearchFilters() {
        return ResponseEntity.ok(ApiResponse.success(searchService.getActiveSearchFilters()));
    }

    @GetMapping("/page-views")
    @Operation(summary = "Get page view analytics")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPageViews(
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getPageViewAnalytics(days)));
    }

    @GetMapping("/conversion-funnel")
    @Operation(summary = "Get conversion funnel data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getConversionFunnel(
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getConversionFunnel(days)));
    }

    @GetMapping("/customer-behavior")
    @Operation(summary = "Get customer behavior analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCustomerBehavior(
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getCustomerBehaviorAnalytics(days)));
    }

    @GetMapping("/product-performance")
    @Operation(summary = "Get product performance analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductPerformance(
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getProductPerformanceAnalytics(days)));
    }

    @GetMapping("/realtime")
    @Operation(summary = "Get realtime analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRealtimeAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getRealtimeAnalytics()));
    }
}
