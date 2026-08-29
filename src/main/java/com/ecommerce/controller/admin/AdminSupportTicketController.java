package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.SupportTicketService;
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
@RequestMapping("/api/admin/support-tickets")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Support Tickets", description = "Admin support ticket management APIs")
public class AdminSupportTicketController {

    private final SupportTicketService supportTicketService;

    @GetMapping
    @Operation(summary = "Get all support tickets")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getAllTickets(Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getAllTickets(status, priority, pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get support ticket by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getTicketById(id)));
    }

    @PutMapping("/{id}/assign")
    @Operation(summary = "Assign support ticket")
    public ResponseEntity<ApiResponse<Void>> assignTicket(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        supportTicketService.assignTicket(id, request);
        return ResponseEntity.ok(ApiResponse.success("Ticket assigned"));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update ticket status")
    public ResponseEntity<ApiResponse<Void>> updateTicketStatus(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        supportTicketService.updateTicketStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Ticket status updated"));
    }

    @PostMapping("/{id}/reply")
    @Operation(summary = "Reply to support ticket")
    public ResponseEntity<ApiResponse<Map<String, Object>>> replyToTicket(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.replyToTicket(id, request)));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get support ticket statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getTicketStats()));
    }

    @GetMapping("/priorities")
    @Operation(summary = "Get ticket priorities")
    public ResponseEntity<ApiResponse<List<String>>> getPriorities() {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getTicketPriorities()));
    }

    @GetMapping("/categories")
    @Operation(summary = "Get ticket categories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getTicketCategories()));
    }
}
