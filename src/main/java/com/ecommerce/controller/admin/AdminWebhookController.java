package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/webhooks")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Webhooks", description = "Admin webhook management APIs")
public class AdminWebhookController {

    private final WebhookService webhookService;

    @GetMapping
    @Operation(summary = "Get all webhooks")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllWebhooks() {
        return ResponseEntity.ok(ApiResponse.success(webhookService.getAllWebhooks()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get webhook by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getWebhook(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(webhookService.getWebhookById(id)));
    }

    @PostMapping
    @Operation(summary = "Create webhook")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createWebhook(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(webhookService.createWebhook(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update webhook")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateWebhook(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(webhookService.updateWebhook(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete webhook")
    public ResponseEntity<ApiResponse<Void>> deleteWebhook(@PathVariable Long id) {
        webhookService.deleteWebhook(id);
        return ResponseEntity.ok(ApiResponse.success("Webhook deleted"));
    }

    @PostMapping("/{id}/test")
    @Operation(summary = "Test webhook")
    public ResponseEntity<ApiResponse<Map<String, Object>>> testWebhook(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(webhookService.testWebhook(id)));
    }

    @PostMapping("/{id}/toggle")
    @Operation(summary = "Toggle webhook active status")
    public ResponseEntity<ApiResponse<Void>> toggleWebhook(@PathVariable Long id) {
        webhookService.toggleWebhook(id);
        return ResponseEntity.ok(ApiResponse.success("Webhook toggled"));
    }

    @GetMapping("/{id}/logs")
    @Operation(summary = "Get webhook logs")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getWebhookLogs(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(webhookService.getWebhookLogs(id, pageable)));
    }

    @GetMapping("/events")
    @Operation(summary = "Get available webhook events")
    public ResponseEntity<ApiResponse<List<String>>> getAvailableEvents() {
        return ResponseEntity.ok(ApiResponse.success(webhookService.getAvailableEvents()));
    }
}
