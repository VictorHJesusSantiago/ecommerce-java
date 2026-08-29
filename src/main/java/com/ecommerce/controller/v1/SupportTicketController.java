package com.ecommerce.controller.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.SupportTicketService;
import com.ecommerce.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/support-tickets")
@RequiredArgsConstructor
@Tag(name = "Support Tickets", description = "Customer support ticket APIs")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @Operation(summary = "Get my support tickets")
    public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> getMyTickets(Pageable pageable,
            @RequestParam(required = false) String status) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getCustomerTickets(customerId, status, pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get support ticket by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTicket(@PathVariable Long id) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getCustomerTicketById(customerId, id)));
    }

    @PostMapping
    @Operation(summary = "Create support ticket")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createTicket(@RequestBody Map<String, Object> request) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.createTicket(customerId, request)));
    }

    @PostMapping("/{id}/reply")
    @Operation(summary = "Reply to support ticket")
    public ResponseEntity<ApiResponse<Map<String, Object>>> replyToTicket(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.replyToTicketAsCustomer(customerId, id, request)));
    }

    @PostMapping("/{id}/close")
    @Operation(summary = "Close support ticket")
    public ResponseEntity<ApiResponse<Void>> closeTicket(@PathVariable Long id) {
        Long customerId = securityUtils.getCurrentUserId();
        supportTicketService.closeTicketAsCustomer(customerId, id);
        return ResponseEntity.ok(ApiResponse.success("Ticket closed"));
    }

    @PostMapping("/{id}/reopen")
    @Operation(summary = "Reopen support ticket")
    public ResponseEntity<ApiResponse<Void>> reopenTicket(@PathVariable Long id) {
        Long customerId = securityUtils.getCurrentUserId();
        supportTicketService.reopenTicket(customerId, id);
        return ResponseEntity.ok(ApiResponse.success("Ticket reopened"));
    }

    @GetMapping("/categories")
    @Operation(summary = "Get available ticket categories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(supportTicketService.getTicketCategories()));
    }
}
