package com.ecommerce.listener;

import com.ecommerce.event.*;
import com.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Processing payment completed event for order: {}", event.getOrderNumber());
        try {
            emailService.sendEmail(null, "Payment Confirmation", "Your payment has been processed");
        } catch (Exception e) {
            log.error("Failed to send payment confirmation email", e);
        }
    }

    @Async
    @EventListener
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.error("Payment failed for order: {} - {}", event.getOrderNumber(), event.getErrorMessage());
    }
}
