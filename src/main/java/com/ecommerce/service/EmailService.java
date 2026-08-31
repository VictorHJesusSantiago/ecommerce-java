package com.ecommerce.service;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subject, String htmlContent);

    void sendEmailWithTemplate(String to, String templateCode, Map<String, Object> variables);

    void sendOrderConfirmation(String to, Map<String, Object> orderData);

    void sendShippingConfirmation(String to, Map<String, Object> shippingData);

    void sendPasswordReset(String to, String resetToken);

    void sendEmailVerification(String to, String verificationToken);

    void sendWelcomeEmail(String to, String firstName);

    void sendPromotionEmail(String to, String subject, String htmlContent);
}
