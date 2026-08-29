package com.ecommerce.model.dto.response.report;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReportResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalRevenue;
    private BigDecimal totalRefunds;
    private BigDecimal netRevenue;
    private BigDecimal totalFees;
    private BigDecimal netProfit;
    private BigDecimal totalTax;
    private BigDecimal totalShipping;
    private BigDecimal totalDiscounts;
    private long totalTransactions;
    private long successfulTransactions;
    private long failedTransactions;
    private BigDecimal averageTransactionValue;
}
