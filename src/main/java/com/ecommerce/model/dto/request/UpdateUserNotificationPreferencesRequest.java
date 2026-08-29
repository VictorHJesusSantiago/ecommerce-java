package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserNotificationPreferencesRequest {

    private boolean emailNotifications;
    private boolean smsNotifications;
    private boolean pushNotifications;

    private boolean orderUpdates;
    private boolean promotionalEmails;
    private boolean newsletterSubscription;
    private boolean productAlerts;
    private boolean priceDrops;
    private boolean backInStock;
    private boolean reviewReminders;
    private boolean accountSecurity;

    @Size(max = 50)
    private String timezone;

    @Size(max = 20)
    private String language;
}
