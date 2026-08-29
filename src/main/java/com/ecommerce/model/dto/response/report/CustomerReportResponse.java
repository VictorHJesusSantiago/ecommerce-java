package com.ecommerce.model.dto.response.report;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportResponse {
    private long totalCustomers;
    private long activeCustomers;
    private long inactiveCustomers;
    private long newCustomersThisMonth;
    private long newCustomersThisWeek;
    private long totalOrders;
    private BigDecimal averageOrderValue;
    private double averageOrdersPerCustomer;
    private double customerRetentionRate;
    private BigDecimal averageLifetimeValue;
}
