package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.dto.request.payment.*;
import com.ecommerce.model.dto.response.payment.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.PaymentStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.PaymentService;
import com.ecommerce.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionResponse processPayment(CreatePaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));

        Transaction transaction = Transaction.builder()
                .order(order)
                .transactionNumber(CodeUtil.generateTransactionNumber())
                .paymentGateway("STRIPE")
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PROCESSING)
                .amount(request.getAmount())
                .currency("USD")
                .paymentToken(request.getPaymentToken())
                .build();

        try {
            transaction.setStatus(PaymentStatus.COMPLETED);
            transaction.setProcessedAt(LocalDateTime.now());
            order.setIsPaid(true);
            order.setPaidAt(LocalDateTime.now());
            order.setPaymentTransactionId(transaction.getTransactionNumber());
        } catch (Exception e) {
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setGatewayResponseMessage(e.getMessage());
            log.error("Payment failed for order {}: {}", order.getOrderNumber(), e.getMessage());
        }

        Transaction saved = transactionRepository.save(transaction);
        orderRepository.save(order);

        return mapToTransactionResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
        return mapToTransactionResponse(transaction);
    }

    @Override
    @Transactional
    public RefundResponse processRefund(RefundRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));

        Refund refund = Refund.builder()
                .refundNumber(CodeUtil.generateRefundNumber())
                .order(order)
                .status(com.ecommerce.model.enums.RefundStatus.PENDING)
                .amount(request.getAmount())
                .currency("USD")
                .reason(request.getReason())
                .note(request.getNote())
                .refundMethod(request.getRefundMethod())
                .build();

        Refund saved = refundRepository.save(refund);
        log.info("Refund created: {} for order {}", saved.getRefundNumber(), order.getOrderNumber());

        return mapToRefundResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RefundResponse getRefundById(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund", "id", id));
        return mapToRefundResponse(refund);
    }

    @Override
    public String createPaymentIntent(Long orderId, BigDecimal amount, String currency, String paymentMethod) {
        log.info("Creating payment intent for order {} - {} {}", orderId, amount, currency);
        return "pi_test_" + System.currentTimeMillis();
    }

    @Override
    public void handlePaymentWebhook(String provider, String payload, String signature) {
        log.info("Processing {} webhook", provider);
    }

    @Override
    public void handleStripeWebhook(String payload, String signature) {
        log.info("Processing Stripe webhook");
    }

    @Override
    public void handlePaypalWebhook(String payload, String signature) {
        log.info("Processing PayPal webhook");
    }

    private TransactionResponse mapToTransactionResponse(Transaction tx) {
        return TransactionResponse.builder()
                .id(tx.getId())
                .orderId(tx.getOrder().getId())
                .orderNumber(tx.getOrder().getOrderNumber())
                .transactionNumber(tx.getTransactionNumber())
                .paymentGateway(tx.getPaymentGateway())
                .paymentMethod(tx.getPaymentMethod())
                .status(tx.getStatus().name())
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .fee(tx.getFee())
                .netAmount(tx.getNetAmount())
                .gatewayTransactionId(tx.getGatewayTransactionId())
                .cardLast4(tx.getCardLast4())
                .cardType(tx.getCardType())
                .isRefunded(tx.isRefunded())
                .refundedAmount(tx.getRefundedAmount())
                .createdAt(tx.getCreatedAt())
                .processedAt(tx.getProcessedAt())
                .build();
    }

    private RefundResponse mapToRefundResponse(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .refundNumber(refund.getRefundNumber())
                .orderId(refund.getOrder().getId())
                .orderNumber(refund.getOrder().getOrderNumber())
                .status(refund.getStatus().name())
                .amount(refund.getAmount())
                .currency(refund.getCurrency())
                .reason(refund.getReason())
                .note(refund.getNote())
                .createdAt(refund.getCreatedAt())
                .processedAt(refund.getProcessedAt())
                .build();
    }
}
