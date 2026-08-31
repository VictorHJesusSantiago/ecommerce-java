package com.ecommerce.service;

import com.ecommerce.model.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {
    void log(String entityType, Long entityId, String action, String details, Long userId);
    void log(String entityType, Long entityId, String action, String details, Long userId, String ipAddress);
    List<AuditLog> getAuditLogs(String entityType, Long entityId);
    List<AuditLog> getAuditLogsByUser(Long userId);
    List<AuditLog> getAuditLogsBetween(LocalDateTime start, LocalDateTime end);
}
