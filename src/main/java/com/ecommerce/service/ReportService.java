package com.ecommerce.service;

import com.ecommerce.model.dto.response.report.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportService {
    DashboardResponse getDashboard();
    SalesReportResponse getSalesReport(LocalDate startDate, LocalDate endDate);
    OrderReportResponse getOrderReport();
    ProductReportResponse getProductReport();
    CustomerReportResponse getCustomerReport();
    FinancialReportResponse getFinancialReport(LocalDate startDate, LocalDate endDate);
    InventoryReportResponse getInventoryReport();
    List<Map<String, Object>> getSalesChartData(LocalDate startDate, LocalDate endDate);
    Map<String, Object> getRevenueChartData(LocalDate startDate, LocalDate endDate);
    Map<String, Object> getTopSellingProductsData(int limit);
    Map<String, Object> getTopCategoriesData(int limit);
    Map<String, Object> getCustomerGrowthData(LocalDate startDate, LocalDate endDate);
}
