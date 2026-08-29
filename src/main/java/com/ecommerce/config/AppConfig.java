package com.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String name = "E-Commerce Platform";
    private String version = "1.0.0";
    private String baseUrl = "http://localhost:8080";
    private String frontendUrl = "http://localhost:3000";
    private Upload upload = new Upload();
    private Email email = new Email();
    private Payment payment = new Payment();
    private Social social = new Social();

    public static class Upload {
        private String location = "./uploads";
        private long maxFileSize = 10 * 1024 * 1024;
        private String[] allowedTypes = {"image/jpeg", "image/png", "image/gif", "image/webp"};
        private int thumbnailWidth = 300;
        private int thumbnailHeight = 300;

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public long getMaxFileSize() { return maxFileSize; }
        public void setMaxFileSize(long maxFileSize) { this.maxFileSize = maxFileSize; }
        public String[] getAllowedTypes() { return allowedTypes; }
        public void setAllowedTypes(String[] allowedTypes) { this.allowedTypes = allowedTypes; }
        public int getThumbnailWidth() { return thumbnailWidth; }
        public int getThumbnailHeight() { return thumbnailHeight; }
    }

    public static class Email {
        private String from = "noreply@ecommerce.com";
        private String fromName = "E-Commerce";
        private String replyTo = "support@ecommerce.com";

        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getFromName() { return fromName; }
        public void setFromName(String fromName) { this.fromName = fromName; }
        public String getReplyTo() { return replyTo; }
        public void setReplyTo(String replyTo) { this.replyTo = replyTo; }
    }

    public static class Payment {
        private String stripeSecretKey;
        private String stripePublishableKey;
        private String stripeWebhookSecret;
        private String paypalClientId;
        private String paypalClientSecret;
        private String paypalMode = "sandbox";

        public String getStripeSecretKey() { return stripeSecretKey; }
        public void setStripeSecretKey(String stripeSecretKey) { this.stripeSecretKey = stripeSecretKey; }
        public String getStripePublishableKey() { return stripePublishableKey; }
        public void setStripePublishableKey(String stripePublishableKey) { this.stripePublishableKey = stripePublishableKey; }
        public String getStripeWebhookSecret() { return stripeWebhookSecret; }
        public void setStripeWebhookSecret(String stripeWebhookSecret) { this.stripeWebhookSecret = stripeWebhookSecret; }
        public String getPaypalClientId() { return paypalClientId; }
        public void setPaypalClientId(String paypalClientId) { this.paypalClientId = paypalClientId; }
        public String getPaypalClientSecret() { return paypalClientSecret; }
        public void setPaypalClientSecret(String paypalClientSecret) { this.paypalClientSecret = paypalClientSecret; }
        public String getPaypalMode() { return paypalMode; }
        public void setPaypalMode(String paypalMode) { this.paypalMode = paypalMode; }
    }

    public static class Social {
        private String googleClientId;
        private String googleClientSecret;
        private String facebookClientId;
        private String facebookClientSecret;

        public String getGoogleClientId() { return googleClientId; }
        public void setGoogleClientId(String googleClientId) { this.googleClientId = googleClientId; }
        public String getGoogleClientSecret() { return googleClientSecret; }
        public void setGoogleClientSecret(String googleClientSecret) { this.googleClientSecret = googleClientSecret; }
        public String getFacebookClientId() { return facebookClientId; }
        public void setFacebookClientId(String facebookClientId) { this.facebookClientId = facebookClientId; }
        public String getFacebookClientSecret() { return facebookClientSecret; }
        public void setFacebookClientSecret(String facebookClientSecret) { this.facebookClientSecret = facebookClientSecret; }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getFrontendUrl() { return frontendUrl; }
    public void setFrontendUrl(String frontendUrl) { this.frontendUrl = frontendUrl; }
    public Upload getUpload() { return upload; }
    public void setUpload(Upload upload) { this.upload = upload; }
    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public Social getSocial() { return social; }
    public void setSocial(Social social) { this.social = social; }
}
