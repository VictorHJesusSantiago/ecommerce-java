package com.ecommerce.service;

import com.ecommerce.model.dto.request.marketing.NewsletterSubscriptionRequest;

public interface NewsletterService {

    void subscribe(NewsletterSubscriptionRequest request);

    void unsubscribe(String email);

    void confirmSubscription(String token);

    long getSubscriberCount();

    void sendNewsletter(String subject, String htmlContent);
}
