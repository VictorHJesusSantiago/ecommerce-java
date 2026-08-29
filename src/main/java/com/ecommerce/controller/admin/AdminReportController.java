package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.DashboardResponse;
import com.ecommerce.model.dto.response.report.*;
import com.ecommerce.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Reports", description = "Admin reporting APIs")
public class AdminReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getDashboard()));
    }

    @GetMapping("/sales")
    @Operation(summary = "Get sales report")
    public ResponseEntity<ApiResponse<SalesReportResponse>> getSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getSalesReport(startDate, endDate)));
    }

    @GetMapping("/orders")
    @Operation(summary = "Get order report")
    public ResponseEntity<ApiResponse<OrderReportResponse>> getOrderReport() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getOrderReport()));
    }

    @GetMapping("/products")
    @Operation(summary = "Get product report")
    public ResponseEntity<ApiResponse<ProductReportResponse>> getProductReport() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getProductReport()));
    }

    @GetMapping("/customers")
    @Operation(summary = "Get customer report")
    public ResponseEntity<ApiResponse<CustomerReportResponse>> getCustomerReport() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getCustomerReport()));
    }

    @GetMapping("/financial")
    @Operation(summary = "Get financial report")
    public ResponseEntity<ApiResponse<FinancialReportResponse>> getFinancialReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getFinancialReport(startDate, endDate)));
    }

    @GetMapping("/inventory")
    @Operation(summary = "Get inventory report")
    public ResponseEntity<ApiResponse<InventoryReportResponse>> getInventoryReport() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getInventoryReport()));
    }

    @GetMapping("/charts/sales")
    @Operation(summary = "Get sales chart data")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSalesChartData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getSalesChartData(startDate, endDate)));
    }

    @GetMapping("/charts/revenue")
    @Operation(summary = "Get revenue chart data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRevenueChartData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getRevenueChartData(startDate, endDate)));
    }

    @GetMapping("/charts/top-products")
    @Operation(summary = "Get top products chart data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTopProductsChartData(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getTopSellingProductsData(limit)));
    }

    @GetMapping("/charts/categories")
    @Operation(summary = "Get category distribution chart data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCategoryChartData(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getTopCategoriesData(limit)));
    }

    @GetMapping("/charts/customer-growth")
    @Operation(summary = "Get customer growth chart data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCustomerGrowthData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getCustomerGrowthData(startDate, endDate)));
    }
}
