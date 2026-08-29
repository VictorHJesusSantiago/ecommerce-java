package com.ecommerce.controller.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.AuditLogService;
import com.ecommerce.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/audit")
@RequiredArgsConstructor
@Tag(name = "User Audit", description = "User audit log APIs")
public class UserAuditController {

    private final AuditLogService auditLogService;
    private final SecurityUtils securityUtils;

    @GetMapping("/activity")
    @Operation(summary = "Get my activity logs")
    public ResponseEntity<ApiResponse<?>> getMyActivityLogs(
            @RequestParam(defaultValue = "30") int days) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getUserActivityLogs(customerId, days)));
    }

    @GetMapping("/login-history")
    @Operation(summary = "Get my login history")
    public ResponseEntity<ApiResponse<?>> getLoginHistory(
            @RequestParam(defaultValue = "20") int limit) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getLoginHistory(customerId, limit)));
    }

    @GetMapping("/sessions")
    @Operation(summary = "Get my active sessions")
    public ResponseEntity<ApiResponse<?>> getActiveSessions() {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getActiveSessions(customerId)));
    }

    @PostMapping("/sessions/{sessionId}/revoke")
    @Operation(summary = "Revoke a session")
    public ResponseEntity<ApiResponse<Void>> revokeSession(@PathVariable Long sessionId) {
        Long customerId = securityUtils.getCurrentUserId();
        auditLogService.revokeSession(customerId, sessionId);
        return ResponseEntity.ok(ApiResponse.success("Session revoked"));
    }

    @PostMapping("/sessions/revoke-all")
    @Operation(summary = "Revoke all sessions except current")
    public ResponseEntity<ApiResponse<Void>> revokeAllSessions() {
        Long customerId = securityUtils.getCurrentUserId();
        auditLogService.revokeAllSessions(customerId);
        return ResponseEntity.ok(ApiResponse.success("All sessions revoked"));
    }
}
