package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.ExchangeRateService;
import com.ecommerce.service.EmailService;
import com.ecommerce.service.NotificationService;
import com.ecommerce.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl {

    private final com.ecommerce.repository.WebhookRepository webhookRepository;
    private final com.ecommerce.repository.WebhookLogRepository webhookLogRepository;

    @Transactional
    public void triggerWebhooks(String event, Object payload) {
        List<Webhook> webhooks = webhookRepository.findByIsActiveTrue();
        for (Webhook webhook : webhooks) {
            if (webhook.getEvents() != null && webhook.getEvents().contains(event)) {
                triggerWebhook(webhook, event, payload);
            }
        }
    }

    private void triggerWebhook(Webhook webhook, String event, Object payload) {
        try {
            WebhookLog logEntry = WebhookLog.builder()
                    .webhook(webhook)
                    .event(event)
                    .status("PENDING")
                    .build();
            webhookLogRepository.save(logEntry);

            webhook.setLastTriggeredAt(LocalDateTime.now());
            webhookRepository.save(webhook);

            log.info("Webhook triggered: {} for event: {}", webhook.getName(), event);
        } catch (Exception e) {
            log.error("Failed to trigger webhook {}: {}", webhook.getName(), e.getMessage());
        }
    }
}
