package com.ecommerce.controller.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.GiftCardService;
import com.ecommerce.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/gift-cards")
@RequiredArgsConstructor
@Tag(name = "Gift Cards", description = "Gift card APIs")
public class GiftCardController {

    private final GiftCardService giftCardService;
    private final SecurityUtils securityUtils;

    @PostMapping("/validate")
    @Operation(summary = "Validate gift card code")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateGiftCard(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.validateGiftCard(request.get("code"))));
    }

    @PostMapping("/apply")
    @Operation(summary = "Apply gift card to cart")
    public ResponseEntity<ApiResponse<Void>> applyGiftCard(@RequestBody Map<String, String> request) {
        Long customerId = securityUtils.getCurrentUserId();
        giftCardService.applyGiftCard(customerId, request.get("code"));
        return ResponseEntity.ok(ApiResponse.success("Gift card applied"));
    }

    @PostMapping("/remove")
    @Operation(summary = "Remove gift card from cart")
    public ResponseEntity<ApiResponse<Void>> removeGiftCard() {
        Long customerId = securityUtils.getCurrentUserId();
        giftCardService.removeGiftCard(customerId);
        return ResponseEntity.ok(ApiResponse.success("Gift card removed"));
    }

    @GetMapping("/balance/{code}")
    @Operation(summary = "Check gift card balance")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkBalance(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(giftCardService.getGiftCardBalance(code)));
    }

    @GetMapping("/history")
    @Operation(summary = "Get gift card transaction history")
    public ResponseEntity<ApiResponse<?>> getGiftCardHistory() {
        Long customerId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(giftCardService.getGiftCardHistory(customerId)));
    }
}
