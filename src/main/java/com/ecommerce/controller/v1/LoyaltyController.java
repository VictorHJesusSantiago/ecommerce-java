package com.ecommerce.controller.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.LoyaltyService;
import com.ecommerce.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loyalty")
@RequiredArgsConstructor
@Tag(name = "Loyalty", description = "Loyalty program APIs")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;
    private final SecurityUtils securityUtils;

    @GetMapping("/account")
    @Operation(summary = "Get loyalty account")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLoyaltyAccount() {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyAccountByCustomerId(customerId)));
    }

    @GetMapping("/points")
    @Operation(summary = "Get loyalty points history")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPointsHistory() {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getPointsHistory(customerId)));
    }

    @GetMapping("/tiers")
    @Operation(summary = "Get loyalty tiers")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getLoyaltyTiers() {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLoyaltyTiers()));
    }

    @GetMapping("/rewards")
    @Operation(summary = "Get available rewards")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAvailableRewards() {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getAvailableRewards(customerId)));
    }

    @PostMapping("/rewards/{rewardId}/redeem")
    @Operation(summary = "Redeem reward")
    public ResponseEntity<ApiResponse<Map<String, Object>>> redeemReward(@PathVariable Long rewardId) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.redeemReward(customerId, rewardId)));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get loyalty leaderboard")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getLeaderboard(
            @RequestParam(defaultValue = "20") int limit) {
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.getLeaderboard(limit)));
    }

    @PostMapping("/earn-points")
    @Operation(summary = "Manually earn points")
    public ResponseEntity<ApiResponse<Map<String, Object>>> earnPoints(@RequestBody Map<String, Object> request) {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(loyaltyService.earnPoints(customerId, request)));
    }
}
