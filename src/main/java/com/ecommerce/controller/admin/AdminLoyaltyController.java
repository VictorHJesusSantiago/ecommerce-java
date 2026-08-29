package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.LoyaltyService;
import com.ecommerce.service.CustomerGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/loyalty")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Loyalty", description = "Admin loyalty program APIs")
public class AdminLoyaltyController {

    private final LoyaltyService loyaltyService;
    private final CustomerGroupService customerGroupService;

    @GetMapping("/accounts")
    @Operation(summary = "Get all loyalty accounts")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllAccounts() {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getAllLoyaltyAccounts()));
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Get loyalty account by ID")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyAccountById(id)));
    }

    @GetMapping("/accounts/customer/{customerId}")
    @Operation(summary = "Get loyalty account by customer")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAccountByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyAccountByCustomerId(customerId)));
    }

    @PostMapping("/accounts/{id}/adjust")
    @Operation(summary = "Adjust loyalty points")
    public ResponseEntity<ApiResponse<Void>> adjustPoints(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        loyaltyService.adjustPoints(id, request);
        return ResponseEntity.ok(ApiResponse.success("Points adjusted"));
    }

    @GetMapping("/tiers")
    @Operation(summary = "Get loyalty tiers")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTiers() {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyTiers()));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get loyalty statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyStats()));
    }

    @PostMapping("/tiers")
    @Operation(summary = "Create loyalty tier")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createTier(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.createLoyaltyTier(request)));
    }

    @PutMapping("/tiers/{id}")
    @Operation(summary = "Update loyalty tier")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateTier(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.updateLoyaltyTier(id, request)));
    }

    @DeleteMapping("/tiers/{id}")
    @Operation(summary = "Delete loyalty tier")
    public ResponseEntity<ApiResponse<Void>> deleteTier(@PathVariable Long id) {
        loyaltyService.deleteLoyaltyTier(id);
        return ResponseEntity.ok(ApiResponse.success("Loyalty tier deleted"));
    }
}
