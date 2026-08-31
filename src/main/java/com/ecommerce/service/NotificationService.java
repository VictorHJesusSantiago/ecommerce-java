package com.ecommerce.service;

import com.ecommerce.model.entity.Notification;
import com.ecommerce.model.enums.NotificationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    void sendNotification(Long userId, String title, String message, NotificationCategory category);

    void sendOrderNotification(Long userId, String orderNumber, String status);

    void sendPaymentNotification(Long userId, String orderNumber, String status);

    void sendPromotionNotification(Long userId, String title, String message);

    Page<Notification> getUserNotifications(Long userId, Pageable pageable);

    Page<Notification> getUnreadNotifications(Long userId, Pageable pageable);

    long countUnread(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    void deleteNotification(Long notificationId);

    void deleteAllRead(Long userId);
}
