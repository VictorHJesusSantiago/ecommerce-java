package com.ecommerce.service.impl;

import com.ecommerce.model.entity.AuditLog;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.AuditLogRepository;
import com.ecommerce.repository.UserRepository;
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
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void log(String entityType, Long entityId, String action, String details, Long userId) {
        log(entityType, entityId, action, details, userId, null);
    }

    @Override
    @Transactional
    public void log(String entityType, Long entityId, String action, String details, Long userId, String ipAddress) {
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;

        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .details(details)
                .user(user)
                .ipAddress(ipAddress)
                .build();

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogs(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsByUser(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsBetween(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByCreatedAtBetween(start, end);
    }
}
