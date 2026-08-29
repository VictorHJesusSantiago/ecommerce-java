package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.ReturnService;
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
@RequestMapping("/api/admin/returns")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Returns", description = "Admin return management APIs")
public class AdminReturnController {

    private final ReturnService returnService;

    @GetMapping
    @Operation(summary = "Get all returns")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getAllReturns(Pageable pageable,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(ApiResponse.success(returnService.getAllReturns(status, pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get return by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getReturn(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(returnService.getReturnById(id)));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve return")
    public ResponseEntity<ApiResponse<Void>> approveReturn(@PathVariable Long id) {
        returnService.approveReturn(id);
        return ResponseEntity.ok(ApiResponse.success("Return approved"));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject return")
    public ResponseEntity<ApiResponse<Void>> rejectReturn(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        returnService.rejectReturn(id, request);
        return ResponseEntity.ok(ApiResponse.success("Return rejected"));
    }

    @PutMapping("/{id}/process")
    @Operation(summary = "Process return")
    public ResponseEntity<ApiResponse<Void>> processReturn(@PathVariable Long id) {
        returnService.processReturn(id);
        return ResponseEntity.ok(ApiResponse.success("Return processed"));
    }

    @PutMapping("/{id}/refund")
    @Operation(summary = "Refund return")
    public ResponseEntity<ApiResponse<Void>> refundReturn(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        returnService.refundReturn(id, request);
        return ResponseEntity.ok(ApiResponse.success("Return refunded"));
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Complete return")
    public ResponseEntity<ApiResponse<Void>> completeReturn(@PathVariable Long id) {
        returnService.completeReturn(id);
        return ResponseEntity.ok(ApiResponse.success("Return completed"));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get return statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(returnService.getReturnStats()));
    }
}
