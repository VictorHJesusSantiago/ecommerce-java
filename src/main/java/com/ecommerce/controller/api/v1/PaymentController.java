package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.request.payment.*;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.payment.*;
import com.ecommerce.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment processing endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process a payment")
    public ResponseEntity<ApiResponse<TransactionResponse>> processPayment(
            @Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Payment processed",
                paymentService.processPayment(request)));
    }

    @GetMapping("/transaction/{id}")
    @Operation(summary = "Get transaction by ID")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getTransactionById(id)));
    }

    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Process a refund")
    public ResponseEntity<ApiResponse<RefundResponse>> processRefund(
            @Valid @RequestBody RefundRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Refund processed",
                paymentService.processRefund(request)));
    }

    @GetMapping("/refund/{id}")
    @Operation(summary = "Get refund by ID")
    public ResponseEntity<ApiResponse<RefundResponse>> getRefund(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getRefundById(id)));
    }

    @PostMapping("/webhook/stripe")
    @Operation(summary = "Stripe webhook handler")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {
        paymentService.handleStripeWebhook(payload, signature);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/webhook/paypal")
    @Operation(summary = "PayPal webhook handler")
    public ResponseEntity<String> handlePaypalWebhook(
            @RequestBody String payload,
            @RequestHeader("PayPal-Transmission-Sig") String signature) {
        paymentService.handlePaypalWebhook(payload, signature);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/intent")
    @Operation(summary = "Create payment intent")
    public ResponseEntity<ApiResponse<String>> createPaymentIntent(
            @RequestParam Long orderId,
            @RequestParam java.math.BigDecimal amount,
            @RequestParam(defaultValue = "USD") String currency,
            @RequestParam String paymentMethod) {
        return ResponseEntity.ok(ApiResponse.success(
                paymentService.createPaymentIntent(orderId, amount, currency, paymentMethod)));
    }
}
