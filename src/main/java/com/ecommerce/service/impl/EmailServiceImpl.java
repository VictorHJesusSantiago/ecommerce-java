package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.EmailTemplate;
import com.ecommerce.repository.EmailTemplateRepository;
import com.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    @Async
    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("noreply@ecommerce.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Email sent to {}: {}", to, subject);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    @Async
    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process(templateName, context);
        sendEmail(to, subject, htmlContent);
    }

    @Override
    @Async
    public void sendWelcomeEmail(String to, String firstName) {
        sendTemplateEmail(to, "Welcome to Our Store!", "email/welcome",
                Map.of("firstName", firstName));
    }

    @Override
    @Async
    public void sendOrderConfirmationEmail(String to, String orderNumber) {
        sendTemplateEmail(to, "Order Confirmation - " + orderNumber, "email/order-confirmation",
                Map.of("orderNumber", orderNumber));
    }

    @Override
    @Async
    public void sendOrderShippedEmail(String to, String orderNumber, String trackingNumber) {
        sendTemplateEmail(to, "Order Shipped - " + orderNumber, "email/order-shipped",
                Map.of("orderNumber", orderNumber, "trackingNumber", trackingNumber));
    }

    @Override
    @Async
    public void sendPaymentConfirmationEmail(String to, String orderNumber, String amount) {
        sendTemplateEmail(to, "Payment Confirmed - " + orderNumber, "email/payment-confirmation",
                Map.of("orderNumber", orderNumber, "amount", amount));
    }

    @Override
    @Async
    public void sendRefundEmail(String to, String refundNumber, String amount) {
        sendTemplateEmail(to, "Refund Processed - " + refundNumber, "email/refund",
                Map.of("refundNumber", refundNumber, "amount", amount));
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String to, String resetToken) {
        sendTemplateEmail(to, "Password Reset Request", "email/password-reset",
                Map.of("resetToken", resetToken));
    }

    @Override
    @Async
    public void sendEmailVerification(String to, String verificationToken) {
        sendTemplateEmail(to, "Verify Your Email", "email/verify-email",
                Map.of("verificationToken", verificationToken));
    }

    @Override
    @Async
    public void sendNewsletter(String to, String subject, String content) {
        sendEmail(to, subject, content);
    }

    @Override
    @Transactional(readOnly = true)
    public String getTemplateContent(String templateName) {
        EmailTemplate template = emailTemplateRepository.findByName(templateName)
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate", "name", templateName));
        return template.getContent();
    }

    @Override
    @Transactional
    public void saveTemplate(String name, String subject, String content) {
        var existing = emailTemplateRepository.findByName(name);
        if (existing.isPresent()) {
            EmailTemplate template = existing.get();
            template.setSubject(subject);
            template.setContent(content);
            emailTemplateRepository.save(template);
        } else {
            EmailTemplate template = EmailTemplate.builder()
                    .name(name).subject(subject).content(content).build();
            emailTemplateRepository.save(template);
        }
    }
}
