package com.ecommerce.service;

import com.ecommerce.model.dto.request.payment.*;
import com.ecommerce.model.dto.response.payment.*;

import java.math.BigDecimal;

public interface PaymentService {

    TransactionResponse processPayment(CreatePaymentRequest request);

    TransactionResponse getTransactionById(Long id);

    RefundResponse processRefund(RefundRequest request);

    RefundResponse getRefundById(Long id);

    String createPaymentIntent(Long orderId, BigDecimal amount, String currency, String paymentMethod);

    void handlePaymentWebhook(String provider, String payload, String signature);

    void handleStripeWebhook(String payload, String signature);

    void handlePaypalWebhook(String payload, String signature);
}
