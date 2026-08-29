package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/audit-logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Audit Logs", description = "Admin audit log management APIs")
public class AdminAuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    @Operation(summary = "Get audit logs")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getAuditLogs(
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) Long entityId,
            @RequestParam(required = false) String action,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getAuditLogs(entityType, entityId, action, pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get audit log by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAuditLog(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getAuditLogById(id)));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    @Operation(summary = "Get audit logs for entity")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getEntityAuditLogs(
            @PathVariable String entityType, @PathVariable Long entityId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getAuditLogsByEntity(entityType, entityId, pageable)));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get audit log statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(auditLogService.getAuditLogStats()));
    }

    @PostMapping("/cleanup")
    @Operation(summary = "Cleanup old audit logs")
    public ResponseEntity<ApiResponse<Void>> cleanupLogs(@RequestParam(defaultValue = "90") int days) {
        auditLogService.cleanupOldLogs(days);
        return ResponseEntity.ok(ApiResponse.success("Audit logs cleaned up"));
    }
}
