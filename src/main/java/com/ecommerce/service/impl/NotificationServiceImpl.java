package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Notification;
import com.ecommerce.model.enums.NotificationCategory;
import com.ecommerce.repository.NotificationRepository;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final com.ecommerce.repository.UserRepository userRepository;

    @Override
    @Transactional
    public void sendNotification(Long userId, String title, String message, NotificationCategory category) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .category(category.name())
                .notificationType(com.ecommerce.model.enums.NotificationType.IN_APP)
                .build();

        notificationRepository.save(notification);
        log.info("Notification sent to user {}: {}", userId, title);
    }

    @Override
    @Transactional
    public void sendOrderNotification(Long userId, String orderNumber, String status) {
        sendNotification(userId, "Order " + status, "Your order " + orderNumber + " has been " + status.toLowerCase(), NotificationCategory.ORDER_CONFIRMATION);
    }

    @Override
    @Transactional
    public void sendPaymentNotification(Long userId, String orderNumber, String status) {
        sendNotification(userId, "Payment " + status, "Payment for order " + orderNumber + " has been " + status.toLowerCase(), NotificationCategory.PAYMENT_SUCCESS);
    }

    @Override
    @Transactional
    public void sendPromotionNotification(Long userId, String title, String message) {
        sendNotification(userId, title, message, NotificationCategory.PROMOTION);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> getUserNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> getUnreadNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findUnreadPageByUserId(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        var notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        var unread = notificationRepository.findUnreadByUserId(userId);
        for (var notification : unread) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
        }
        notificationRepository.saveAll(unread);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    @Transactional
    public void deleteAllRead(Long userId) {
        notificationRepository.deleteByUserIdAndIsReadTrue(userId);
    }
}
