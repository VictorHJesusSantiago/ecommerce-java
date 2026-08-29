package com.ecommerce.aspect;

import com.ecommerce.model.entity.AuditLog;
import com.ecommerce.repository.AuditLogRepository;
import com.ecommerce.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;
    private final SecurityUtils securityUtils;

    @Pointcut("@annotation(com.ecommerce.aspect.Auditable)")
    public void auditableMethod() {}

    @Around("auditableMethod()")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        boolean success = true;
        String errorMessage = null;
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            success = false;
            errorMessage = e.getMessage();
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            try {
                AuditLog auditLog = AuditLog.builder()
                        .action(joinPoint.getSignature().getName())
                        .entityType(joinPoint.getTarget().getClass().getSimpleName())
                        .isSuccess(success)
                        .errorMessage(errorMessage)
                        .module(getModule(joinPoint.getTarget().getClass().getSimpleName()))
                        .description(String.format("Method executed in %dms", executionTime))
                        .build();

                securityUtils.getCurrentUser().ifPresent(user -> {
                    auditLog.setUserId(user.getId());
                    auditLog.setUserName(user.getFullName());
                    auditLog.setUserEmail(user.getEmail());
                });

                getCurrentRequest().ifPresent(request -> {
                    auditLog.setIpAddress(getClientIp(request));
                    auditLog.setUserAgent(request.getHeader("User-Agent"));
                    auditLog.setSessionId(request.getRequestedSessionId());
                });

                auditLogRepository.save(auditLog);
            } catch (Exception e) {
                log.error("Failed to save audit log", e);
            }
        }
        return result;
    }

    private String getModule(String className) {
        if (className.contains("Product") || className.contains("Category") || className.contains("Brand")) return "CATALOG";
        if (className.contains("Order")) return "ORDER";
        if (className.contains("Payment") || className.contains("Transaction")) return "PAYMENT";
        if (className.contains("User")) return "USER";
        if (className.contains("Cart")) return "CART";
        if (className.contains("Review")) return "REVIEW";
        return "SYSTEM";
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private java.util.Optional<HttpServletRequest> getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return java.util.Optional.ofNullable(attributes).map(ServletRequestAttributes::getRequest);
    }
}
