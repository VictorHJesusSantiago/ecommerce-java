package com.ecommerce.service.impl;

import com.ecommerce.model.dto.response.report.*;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.model.enums.PaymentStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ReviewRepository reviewRepository;
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long completedOrders = orderRepository.countByStatus(OrderStatus.DELIVERED);
        long totalProducts = productRepository.count();
        long totalCustomers = userRepository.count();
        BigDecimal totalRevenue = orderRepository.sumTotalByStatus(OrderStatus.DELIVERED);
        BigDecimal monthlyRevenue = orderRepository.sumTotalAfterDate(LocalDateTime.now().withDayOfMonth(1));

        List<Object[]> topProducts = orderItemRepository.findTopSellingProducts();
        List<DashboardResponse.TopProduct> topProductList = topProducts.stream()
                .limit(5)
                .map(row -> DashboardResponse.TopProduct.builder()
                        .productId(((Number) row[0]).longValue())
                        .name((String) row[1])
                        .totalSold(((Number) row[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        return DashboardResponse.builder()
                .totalOrders(totalOrders)
                .pendingOrders(pendingOrders)
                .completedOrders(completedOrders)
                .totalProducts(totalProducts)
                .totalCustomers(totalCustomers)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .monthlyRevenue(monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO)
                .topProducts(topProductList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SalesReportResponse getSalesReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        BigDecimal totalSales = orderRepository.sumTotalBetween(start, end);
        long orderCount = orderRepository.countBetween(start, end);
        BigDecimal averageOrderValue = orderCount > 0
                ? totalSales.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        List<Object[]> dailySales = orderRepository.getDailySales(start, end);
        List<SalesReportResponse.DailySales> dailySalesList = dailySales.stream()
                .map(row -> SalesReportResponse.DailySales.builder()
                        .date(((java.sql.Date) row[0]).toLocalDate())
                        .revenue((BigDecimal) row[1])
                        .orderCount(((Number) row[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        List<Object[]> topProducts = orderItemRepository.findTopSellingProductsBetween(start, end);
        List<SalesReportResponse.TopProduct> topProductsList = topProducts.stream()
                .limit(10)
                .map(row -> SalesReportResponse.TopProduct.builder()
                        .productId(((Number) row[0]).longValue())
                        .name((String) row[1])
                        .totalSold(((Number) row[2]).longValue())
                        .revenue((BigDecimal) row[3])
                        .build())
                .collect(Collectors.toList());

        return SalesReportResponse.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalSales(totalSales != null ? totalSales : BigDecimal.ZERO)
                .orderCount(orderCount)
                .averageOrderValue(averageOrderValue)
                .dailySales(dailySalesList)
                .topProducts(topProductsList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderReportResponse getOrderReport() {
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long processingOrders = orderRepository.countByStatus(OrderStatus.PROCESSING);
        long shippedOrders = orderRepository.countByStatus(OrderStatus.SHIPPED);
        long deliveredOrders = orderRepository.countByStatus(OrderStatus.DELIVERED);
        long cancelledOrders = orderRepository.countByStatus(OrderStatus.CANCELLED);
        BigDecimal totalRevenue = orderRepository.sumTotalByStatus(OrderStatus.DELIVERED);
        BigDecimal averageOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return OrderReportResponse.builder()
                .totalOrders(totalOrders)
                .pendingOrders(pendingOrders)
                .processingOrders(processingOrders)
                .shippedOrders(shippedOrders)
                .deliveredOrders(deliveredOrders)
                .cancelledOrders(cancelledOrders)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .averageOrderValue(averageOrderValue)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductReportResponse getProductReport() {
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.countByIsActiveAndIsDeleted(true, false);
        long outOfStock = productRepository.countByStockQuantityAndIsDeleted(0, false);
        long lowStock = productRepository.countLowStockProducts(10);
        double averageRating = reviewRepository.getAverageRating();
        long totalReviews = reviewRepository.count();

        return ProductReportResponse.builder()
                .totalProducts(totalProducts)
                .activeProducts(activeProducts)
                .outOfStock(outOfStock)
                .lowStock(lowStock)
                .averageRating(averageRating)
                .totalReviews(totalReviews)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerReportResponse getCustomerReport() {
        long totalCustomers = userRepository.count();
        long activeCustomers = userRepository.countByIsActive(true);
        long newCustomersThisMonth = userRepository.countByCreatedAtAfter(
                LocalDateTime.now().withDayOfMonth(1));
        long ordersCount = orderRepository.count();

        return CustomerReportResponse.builder()
                .totalCustomers(totalCustomers)
                .activeCustomers(activeCustomers)
                .newCustomersThisMonth(newCustomersThisMonth)
                .averageOrdersPerCustomer(totalCustomers > 0 ? (double) ordersCount / totalCustomers : 0.0)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialReportResponse getFinancialReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        BigDecimal totalRevenue = orderRepository.sumTotalBetween(start, end);
        BigDecimal totalRefunds = transactionRepository.sumByStatusAndDateBetween(PaymentStatus.REFUNDED, start, end);
        BigDecimal netRevenue = totalRevenue.subtract(totalRefunds);
        BigDecimal totalFees = transactionRepository.sumFeesBetween(start, end);

        return FinancialReportResponse.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .totalRefunds(totalRefunds != null ? totalRefunds : BigDecimal.ZERO)
                .netRevenue(netRevenue != null ? netRevenue : BigDecimal.ZERO)
                .totalFees(totalFees != null ? totalFees : BigDecimal.ZERO)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryReportResponse getInventoryReport() {
        long totalItems = inventoryItemRepository.count();
        long inStock = inventoryItemRepository.countByStatus("IN_STOCK");
        long lowStock = inventoryItemRepository.countByStatus("LOW_STOCK");
        long outOfStock = inventoryItemRepository.countByStatus("OUT_OF_STOCK");

        return InventoryReportResponse.builder()
                .totalItems(totalItems)
                .inStockItems(inStock)
                .lowStockItems(lowStock)
                .outOfStockItems(outOfStock)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSalesChartData(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getRevenueChartData(LocalDate startDate, LocalDate endDate) {
        return Map.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getTopSellingProductsData(int limit) {
        return Map.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getTopCategoriesData(int limit) {
        return Map.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getCustomerGrowthData(LocalDate startDate, LocalDate endDate) {
        return Map.of();
    }
}
