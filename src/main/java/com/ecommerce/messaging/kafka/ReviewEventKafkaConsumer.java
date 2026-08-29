package com.ecommerce.messaging.kafka;

import com.ecommerce.event.ReviewCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventKafkaConsumer {

    @KafkaListener(topics = "review.created", groupId = "ecommerce-review")
    public void handleReviewCreated(ReviewCreatedEvent event) {
        log.info("Received review created event for product {} by user {}", event.getProductId(), event.getUserId());
    }
}
